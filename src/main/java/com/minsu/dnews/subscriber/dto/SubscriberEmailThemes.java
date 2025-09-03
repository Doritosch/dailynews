package com.minsu.dnews.subscriber.dto;

import com.minsu.dnews.theme.domain.SubscribeTheme;

import java.util.List;

public record SubscriberEmailThemes(
        String email,
        List<String> subThemes
) {
}
