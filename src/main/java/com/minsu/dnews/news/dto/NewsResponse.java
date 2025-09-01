package com.minsu.dnews.news.dto;

import com.minsu.dnews.news.domain.News;

public record NewsResponse(
        String theme,
        String title,
        String description,
        String originallink
) {
    public static NewsResponse from(News news) {
        return new NewsResponse(
                news.getTheme().getName(),
                news.getTitle(),
                news.getDescription(),
                news.getOriginallink()
        );
    }
}
