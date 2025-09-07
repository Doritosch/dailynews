package com.minsu.dnews.subscriber.dto;

import com.minsu.dnews.subscriber.domain.Subscriber;
import com.minsu.dnews.theme.domain.Theme;

import java.time.LocalDateTime;
import java.util.List;

public record SubscriberRequest(
        String email,
        List<String> themeList
) {

    public Subscriber toEntity() {
        return new Subscriber(
                email
        );
    }
}
