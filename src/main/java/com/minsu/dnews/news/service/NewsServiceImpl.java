package com.minsu.dnews.news.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.minsu.dnews.api.NaverNewsApi;
import com.minsu.dnews.mail.MailSendService;
import com.minsu.dnews.mail.MailSendServiceImpl;
import com.minsu.dnews.news.domain.News;
import com.minsu.dnews.news.dto.NaverNewsResposne;
import com.minsu.dnews.news.dto.NewsItemDto;
import com.minsu.dnews.news.infra.NewsJpaRepository;
import com.minsu.dnews.subscriber.dto.SubscriberEmailThemes;
import com.minsu.dnews.subscriber.service.SubscriberService;
import com.minsu.dnews.theme.domain.Theme;
import com.minsu.dnews.theme.infra.ThemeJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class NewsServiceImpl implements NewsService {

    private final NaverNewsApi naverNewsApi;
    private final NewsJpaRepository newsJpaRepository;
    private final ThemeJpaRepository themeJpaRepository;
    private final SubscriberService subscriberService;
    private final MailSendService mailSendService;

    // 테마별 뉴스 저장 및 NewsItemDto 리스트 반환
    public Map<String, List<NewsItemDto>> updateNews() throws JsonProcessingException {
        List<Theme> themes = themeJpaRepository.findAll();
        log.info("테마는 " +
                themes.stream()
                        .map(Theme::getName)
                        .collect(Collectors.joining(", "))
                + "가 존재합니다.");
        Map<String, List<NewsItemDto>> themeNewsMap = new HashMap<>();
        List<NewsItemDto> newsItems = new ArrayList<>();

        for(Theme theme : themes) {
            List<News> newsList = fetchAndSaveNews(theme);
            for(News news : newsList) {
                NewsItemDto newsItem = new NewsItemDto(
                        news.getTheme().getName(),
                        news.getTitle(),
                        news.getOriginallink(),
                        news.getOriginallink(),
                        news.getDescription(),
                        news.getPubDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                );
                newsItems.add(newsItem);
            }

            themeNewsMap.put(theme.getName(), newsItems);
        }
        return themeNewsMap;
    }
    @Transactional
    public List<News> fetchAndSaveNews(Theme theme) {
//        List<News> newsList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String responseBody = naverNewsApi.getNews(theme.getName());

            objectMapper.registerModule(new JavaTimeModule());
            NaverNewsResposne naverNewsResposne =
                    objectMapper.readValue(responseBody, NaverNewsResposne.class);
            log.info("object 역직렬화");
            List<News> newsList = naverNewsResposne.items().stream()
                    .map(item -> News.builder()
                            .theme(theme)
                            .title(item.title())
                            .originallink(item.originallink())
                            .description(item.description())
                            .pubDate(convertPubDate(item.pubDate()))
                            .build())
                    .toList();
            log.info("객체 list화 완료");
            newsJpaRepository.saveAll(newsList);
            log.info("뉴스 저장 완료");
            return newsList;
//            naverNewsResposne.items().forEach(item -> {
//                News news = News.builder()
//                        .theme(theme)
//                        .title(item.title())
//                        .originallink(item.originallink())
//                        .description(item.description())
//                        .pubDate(convertPubDate(item.pubDate()))
//                        .build();
//                newsJpaRepository.save(news);
//                newsList.add(news);
//            });
        } catch (Exception e) {
            throw new IllegalStateException("뉴스 데이터를 불러오고 저장하는 데 실패했습니다.");
        }
    }
    private LocalDateTime convertPubDate(String pubDate) {
        // 예: "Mon, 01 Sep 2025"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        return ZonedDateTime.parse(pubDate, formatter).toLocalDateTime();
    }
    @Scheduled(cron = "0 0 7 * *")
    public void sendMailToSubscribers() throws JsonProcessingException {
        Map<String, List<NewsItemDto>> themeNewsMap = updateNews();// 뉴스 갱신 및 db 저장

        // 모든 구독자 정보에서 email, subThemeList 추출
        Map<String, List<String>> subInfo = subscriberService.getEmailAndSubThemesFromSubscriber();
        log.info("구독자 정보 가져오기 완료");
        // 당일 기준 모든 뉴스 저장. data format -> map(theme, List<NewsItemDto>)
        //그리고 각 유저가 선택한 테마들 가져와서 list에 뉴스 3개씩 저장
        for(String sub : subInfo.keySet()) {
            log.info(sub + "님에게 전송을 시작합니다.");
            Map<String, List<NewsItemDto>> personalNews = new HashMap<>();
            for(String theme : subInfo.get(sub)) {
                List<NewsItemDto> newsItemDtos = themeNewsMap.get(theme);

                personalNews.put(theme, getRandomNews(newsItemDtos));
            }
            log.info(personalNews.size() + " " + personalNews.keySet());
            mailSendService.sendHtmlEmail(sub, personalNews);
        }
    }
    private List<NewsItemDto> getRandomNews(List<NewsItemDto> newsList) {
        if (newsList == null || newsList.isEmpty()) {
            throw new IllegalStateException("뉴스가 없습니다.");
        }

        int size = Math.min(3, newsList.size());
        return new Random().ints(0, newsList.size())
                .distinct()
                .limit(size)
                .mapToObj(newsList::get)
                .collect(Collectors.toList());
    }
}
