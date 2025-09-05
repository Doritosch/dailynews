package com.minsu.dnews.news.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NewsItemDto(
        String theme,
        String title,
        @JsonProperty("originallink") String originallink,
        String link,
        String description,
        String pubDate
) {
}
