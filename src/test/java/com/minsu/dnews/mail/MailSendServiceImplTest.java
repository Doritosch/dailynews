package com.minsu.dnews.mail;

import com.minsu.dnews.news.dto.NewsItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailSendServiceImplTest {
    @Autowired
    MailSendServiceImpl mailSendService;
    @Test
    void sendEmailToMe() {
        Map<String, List<NewsItemDto>> newMap = new HashMap<>();
        NewsItemDto newsItemDto = new NewsItemDto(
                "art", "title", "naver.com", "naver.com",
                "description", "00:30"
        );
        newMap.put("art", List.of(newsItemDto));
        mailSendService.sendHtmlEmail("als00825@naver.com", newMap);
    }
}