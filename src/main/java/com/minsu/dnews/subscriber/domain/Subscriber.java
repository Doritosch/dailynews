package com.minsu.dnews.subscriber.domain;

import com.minsu.dnews.news.domain.SendLog;
import com.minsu.dnews.theme.domain.SubscribeTheme;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscriber {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBSCRIBER_ID")
    private Long id;
    @Column(nullable = false)
    private String email;

    private LocalDateTime sendTime;
    @Enumerated(EnumType.STRING)
    private SubscriberStatus status;

    @OneToMany(mappedBy = "subscriber")
    private List<SubscribeTheme> subscribeThemeList = new ArrayList<>();

    @OneToMany(mappedBy = "subscriber")
    private List<SendLog> sendLogs = new ArrayList<>();

    public Subscriber(String email, LocalDateTime sendTime) {
        this.email = email;
        this.sendTime = sendTime;
        this.status = SubscriberStatus.SUBSCRIBE;
    }

    public void unsubService() {
        this.status = SubscriberStatus.UNSUBSCRIBE;
    }
}
