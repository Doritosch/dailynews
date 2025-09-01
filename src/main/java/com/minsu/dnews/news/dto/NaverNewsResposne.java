package com.minsu.dnews.news.dto;

import java.util.List;

public record NaverNewsResposne(
        String lastBuildDate,
        int total,
        int start,
        int display,
        List<NewsItem> items
) {
}
