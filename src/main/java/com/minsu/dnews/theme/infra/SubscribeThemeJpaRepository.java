package com.minsu.dnews.theme.infra;

import com.minsu.dnews.subscriber.domain.Subscriber;
import com.minsu.dnews.theme.domain.SubscribeTheme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeThemeJpaRepository extends JpaRepository<SubscribeTheme, Long> {
    SubscribeTheme findByThemeNameAndSubscriber(String name, Subscriber subscriber);
}
