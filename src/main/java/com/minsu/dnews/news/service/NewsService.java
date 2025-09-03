package com.minsu.dnews.news.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.minsu.dnews.news.domain.News;
import com.minsu.dnews.theme.domain.Theme;

import java.util.List;

public interface NewsService {
    List<News> fetchAndSaveNews(Theme theme) throws JsonProcessingException;
}
