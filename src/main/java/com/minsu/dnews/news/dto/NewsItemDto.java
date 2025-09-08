package com.minsu.dnews.news.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;

public record NewsItemDto(
        String theme,
        String title,
        String originallink,
        String link,
        String description,
        String pubDate
) {
}
