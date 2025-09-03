package com.minsu.dnews.mail;

import com.minsu.dnews.news.dto.NewsItemDto;

import java.util.List;
import java.util.Map;

public interface MailSendService {
    void sendHtmlEmail(String email, Map<String, List<NewsItemDto>> listMap);
}
