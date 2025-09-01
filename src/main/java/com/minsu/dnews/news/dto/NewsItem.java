package com.minsu.dnews.news.dto;

import java.time.LocalDateTime;

public record NewsItem(
        String theme,
        String title,
        String originallink,
        String link,
        String description,
        String pubDate
) {
}
