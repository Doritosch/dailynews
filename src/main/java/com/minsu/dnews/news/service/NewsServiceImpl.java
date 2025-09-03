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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
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
        List<News> newsList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String responseBody = naverNewsApi.getNews(theme.getName());

            objectMapper.registerModule(new JavaTimeModule());
            NaverNewsResposne naverNewsResposne =
                    objectMapper.readValue(responseBody, NaverNewsResposne.class);

            naverNewsResposne.items().forEach(item -> {
                News news = News.builder()
                        .theme(theme)
                        .title(item.title())
                        .originallink(item.originallink())
                        .description(item.description())
                        .pubDate(convertPubDate(item.pubDate()))
                        .build();
                newsJpaRepository.save(news);
                newsList.add(news);
            });
        } catch (Exception e) {
            throw new IllegalStateException("뉴스 데이터를 불러오고 저장하는 데 실패했습니다.");
        } finally {
            objectMapper.clearCaches();
            return newsList;
        }
    }
    private LocalDateTime convertPubDate(String pubDate) {
        // 예: "Mon, 01 Sep 2025"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy", Locale.ENGLISH);
        return ZonedDateTime.parse(pubDate, formatter).toLocalDateTime();
    }
    @Scheduled(cron = "0 0 7 * *")
    private void sendMailToSubscribers() throws JsonProcessingException {
        Map<String, List<NewsItemDto>> themeNewsMap = updateNews();// 뉴스 갱신 및 db 저장

        // 모든 구독자 정보에서 email, subThemeList 추출
        List<SubscriberEmailThemes> subInfo = subscriberService.getEmailAndSubThemes();

        // 당일 기준 모든 뉴스 저장. data format -> map(theme, mop<News>)
        //그리고 각 유저가 선택한 테마들 가져와서 list에 뉴스 3개씩 저장
        for(SubscriberEmailThemes sub : subInfo) {
            Map<String, List<NewsItemDto>> personalNews = new HashMap<>();
            for(String theme : sub.subThemes()) {
                List<NewsItemDto> newsItemDtos = themeNewsMap.get(theme);
                Collections.shuffle(newsItemDtos);
                List<NewsItemDto> sendNewsDtoList = newsItemDtos.subList(0, 3);
                personalNews.put(theme, sendNewsDtoList);
            }

            mailSendService.sendHtmlEmail(sub.email(), personalNews);
        }
    }

}
