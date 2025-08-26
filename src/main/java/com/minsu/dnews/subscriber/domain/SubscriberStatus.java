package com.minsu.dnews.subscriber.domain;

public enum SubscriberStatus {
    SUBSCRIBE, UNSUBSCRIBE;

    public static SubscriberStatus subscribe() {
        return SUBSCRIBE;
    }
    public static SubscriberStatus unsubscribe() {
        return UNSUBSCRIBE;
    }
}
