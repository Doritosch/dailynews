package com.minsu.dnews.subscriber.dto;

import com.minsu.dnews.subscriber.domain.Subscriber;

import java.time.LocalDateTime;

public record SubscriberResponse(
        String email
) {
    public static SubscriberResponse from(Subscriber subscriber) {
        return new SubscriberResponse(
                subscriber.getEmail()
        );
    }
}
