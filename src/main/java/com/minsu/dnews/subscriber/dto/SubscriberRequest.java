package com.minsu.dnews.subscriber.dto;

import com.minsu.dnews.subscriber.domain.Subscriber;

import java.time.LocalDateTime;

public record SubscriberRequest(
        String email,
        LocalDateTime sendTime
) {

    public Subscriber toEntity() {
        return new Subscriber(
                email,
                sendTime
        );
    }
}
