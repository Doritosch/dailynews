package com.minsu.dnews.subscriber.infra;

import com.minsu.dnews.subscriber.domain.Subscriber;
import com.minsu.dnews.subscriber.dto.SubscriberEmailThemes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriberJpaRepository extends JpaRepository<Subscriber, Long> {
    Subscriber findByEmail(String email);

    @Query("select s.email, t.name from Subscriber s " +
            "join s.subscribeThemeList st " +
            "join st.theme t " +
            "where s.status = 'subscribe'")
    List<Object[]> findSubscribersEmailAndTheme();
}
