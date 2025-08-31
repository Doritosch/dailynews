package com.minsu.dnews.news.domain;

import com.minsu.dnews.subscriber.domain.Subscriber;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class SendLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "COLUMN_ID")
    private News news;

    @ManyToOne
    @JoinColumn(name = "SUBSCRIBER_ID")
    private Subscriber subscriber;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime sendAt;

    public void addNews(News news) {
        this.news = news;
        if (!news.getSendLogs().contains(this)) {
            news.getSendLogs().add(this);
        }
    }
    public void addSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
        if (!subscriber.getSendLogs().contains(this)) {
            subscriber.getSendLogs().add(this);
        }
    }
}
