package com.minsu.dnews.news.dto;

public record NewsItemDto(
        String theme,
        String title,
        String originallink,
        String link,
        String description,
        String pubDate
) {
}
