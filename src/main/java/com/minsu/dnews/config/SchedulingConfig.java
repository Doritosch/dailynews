package com.minsu.dnews.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.minsu.dnews.news.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Slf4j
@Configuration
public class SchedulingConfig {
    private NewsService newsService;
    public SchedulingConfig(NewsService newsService) {
        this.newsService = newsService;
    }
    @Scheduled(cron = "0 0 7 * * *", zone = "Asia/Seoul")
    public void sendNewsToAllSubscriber() {
        try {
            newsService.sendMailToSubscribers();
        } catch (JsonProcessingException e) {
            log.info("뉴스 발송 실패", e);
        }
    }
}
