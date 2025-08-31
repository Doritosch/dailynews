package com.minsu.dnews.theme.domain;

import com.minsu.dnews.subscriber.domain.Subscriber;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubscribeTheme {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "THEME_ID")
    private Theme theme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBSCRIBER_ID")
    private Subscriber subscriber;

    public SubscribeTheme(Theme theme, Subscriber subscriber) {
        this.theme = theme;
        this.subscriber = subscriber;
    }
}
