package com.minsu.dnews.subscriber.dto;

import com.minsu.dnews.subscriber.domain.Subscriber;

public record SubscriberSearchRequest(
        String email
) {
}
