package com.minsu.dnews.subscriber.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Subscriber {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime sendTime;
    @Enumerated(EnumType.STRING)
    private SubscriberStatus status;

    public Subscriber(String email, LocalDateTime sendTime) {
        this.email = email;
        this.sendTime = sendTime;
        this.status = SubscriberStatus.SUBSCRIBE;
    }

    public void unsubService() {
        this.status = SubscriberStatus.UNSUBSCRIBE;
    }
}
