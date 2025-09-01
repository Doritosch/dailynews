package com.minsu.dnews.news.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.minsu.dnews.api.NaverNewsApi;
import com.minsu.dnews.news.domain.News;
import com.minsu.dnews.news.dto.NaverNewsResposne;
import com.minsu.dnews.news.infra.NewsJpaRepository;
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
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {

    private final NaverNewsApi naverNewsApi;
//    private final OpenAiApi openAiApi;
    private final NewsJpaRepository newsJpaRepository;
    private final ThemeJpaRepository themeJpaRepository;

    @Scheduled(cron = "0 0 7 * * *")
    public void updateNews() throws JsonProcessingException {
        List<Theme> themes = themeJpaRepository.findAll();

        for(Theme theme : themes) {
            fetchAndSaveNews(theme);
        }

        sendMailToSubscribers();
    }
    @Transactional
    public void fetchAndSaveNews(Theme theme) {
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
            });
        } catch (Exception e) {
            throw new IllegalStateException("뉴스 데이터를 불러오고 저장하는 데 실패했습니다.");
        }


    }
    private LocalDateTime convertPubDate(String pubDate) {
        // 예: "Mon, 01 Sep 2025"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy", Locale.ENGLISH);
        return ZonedDateTime.parse(pubDate, formatter).toLocalDateTime();
    }
    private void sendMailToSubscribers() {

    }
}
