package com.minsu.dnews.subscriber.service;

import com.minsu.dnews.subscriber.domain.Subscriber;
import com.minsu.dnews.subscriber.domain.SubscriberStatus;
import com.minsu.dnews.subscriber.dto.SubscriberRequest;
import com.minsu.dnews.subscriber.dto.SubscriberResponse;
import com.minsu.dnews.subscriber.dto.SubscriberSearchRequest;
import com.minsu.dnews.subscriber.infra.SubscriberJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriberServiceImpl implements SubscriberService {
    private final SubscriberJpaRepository subscriberJpaRepository;

    @Autowired
    public SubscriberServiceImpl(SubscriberJpaRepository subscriberJpaRepository) {
        this.subscriberJpaRepository = subscriberJpaRepository;
    }


    @Transactional
    @Override
    public SubscriberResponse subscribe(SubscriberRequest subscriber) {
        if (subscriberJpaRepository.findByEmail(subscriber.email()) != null) {
            throw new IllegalStateException("이미 구독한 이메일입니다.");
        }

        Subscriber subscriberEntity = subscriber.toEntity();
        Subscriber saveEntity = subscriberJpaRepository.save(subscriberEntity);
        return SubscriberResponse.from(saveEntity);
    }

    @Override
    public SubscriberResponse searchEmail(SubscriberSearchRequest searchRequest) {
        Subscriber subscriber = subscriberJpaRepository.findByEmail(searchRequest.email());

        if (subscriber.getStatus() == SubscriberStatus.UNSUBSCRIBE) {
            throw new IllegalStateException("구독을 해지한 회원입니다.");
        }

        return SubscriberResponse.from(subscriber);
    }
    @Transactional
    @Override
    public void unsubscribe(String email) {
        Subscriber subscriber = subscriberJpaRepository.findByEmail(email);

        subscriber.unsubService();
    }
}
