package com.minsu.dnews.news.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.minsu.dnews.theme.domain.Theme;

public interface NewsService {
    void fetchAndSaveNews(Theme theme) throws JsonProcessingException;
}
