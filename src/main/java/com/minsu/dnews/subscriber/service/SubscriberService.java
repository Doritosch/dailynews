package com.minsu.dnews.subscriber.service;

import com.minsu.dnews.subscriber.domain.Subscriber;
import com.minsu.dnews.subscriber.dto.SubscriberEmailThemes;
import com.minsu.dnews.subscriber.dto.SubscriberRequest;
import com.minsu.dnews.subscriber.dto.SubscriberResponse;
import com.minsu.dnews.subscriber.dto.SubscriberSearchRequest;

import java.util.List;
import java.util.Map;

public interface SubscriberService {
    SubscriberResponse subscribe(SubscriberRequest subscriber);
    SubscriberResponse searchEmail(SubscriberSearchRequest searchRequest);
    void unsubscribe(String email);

    Map<String, List<String>> getEmailAndSubThemesFromSubscriber();
}
