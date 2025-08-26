package com.minsu.dnews.subscriber.infra;

import com.minsu.dnews.subscriber.domain.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriberJpaRepository extends JpaRepository<Subscriber, Long> {
    Subscriber findByEmail(String email);
}
