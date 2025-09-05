package com.minsu.dnews.news.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.minsu.dnews.api.NaverNewsApi;
import com.minsu.dnews.mail.MailSendService;
import com.minsu.dnews.news.infra.NewsJpaRepository;
import com.minsu.dnews.subscriber.service.SubscriberService;
import com.minsu.dnews.theme.infra.ThemeJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NewsServiceImplTest {
    @Autowired
    private NewsServiceImpl newsService;
    @Test
    void sendMailToSubscribersTest() throws JsonProcessingException {
        newsService.sendMailToSubscribers();
    }
}