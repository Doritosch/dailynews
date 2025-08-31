package com.minsu.dnews.subscriber.service;

import com.minsu.dnews.subscriber.domain.Subscriber;
import com.minsu.dnews.subscriber.dto.SubscriberRequest;
import com.minsu.dnews.subscriber.dto.SubscriberResponse;
import com.minsu.dnews.subscriber.dto.SubscriberSearchRequest;
import com.minsu.dnews.subscriber.infra.SubscriberJpaRepository;
import com.minsu.dnews.theme.infra.ThemeJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@Rollback(value = false)
@ExtendWith(MockitoExtension.class)
class SubscriberServiceImplTest {

    @Mock
    SubscriberJpaRepository subscriberJpaRepository;
    @Mock
    ThemeJpaRepository themeJpaRepository;
    @InjectMocks
    SubscriberServiceImpl subscriberService;

    @Test
    @DisplayName("구독자 생성")
    void subscribeTest() {
        //given
        SubscriberRequest subscriberRequest = new SubscriberRequest("test@naver.com", LocalDateTime.now(), new ArrayList<>());
        Subscriber subscriber = new Subscriber("test@naver.com", subscriberRequest.sendTime());

        //when
        when(subscriberJpaRepository.save(any(Subscriber.class)))
                .thenReturn(subscriber);

        SubscriberResponse subscribe = subscriberService.subscribe(subscriberRequest);

        //then
        Assertions.assertEquals(subscriberRequest.email(), subscribe.email());
        verify(subscriberJpaRepository, times(1)).save(any(Subscriber.class));
    }
    @Test
    @DisplayName("이메일로 구독자 정보찾기")
    void searchSubscriberTest() {
        //given
        SubscriberSearchRequest subscriberSearchRequest =
                new SubscriberSearchRequest("test01@naver.com");
        Subscriber subscriber = new Subscriber(subscriberSearchRequest.email(), LocalDateTime.now());

        //when
        when(subscriberJpaRepository.findByEmail(subscriberSearchRequest.email()))
                .thenReturn(Optional.of(subscriber).orElse(null));

        SubscriberResponse subscriberResponse = subscriberService.searchEmail(subscriberSearchRequest);
        //then
        assertEquals(subscriberSearchRequest.email(), subscriberResponse.email());
        verify(subscriberJpaRepository, times(1)).findByEmail(subscriberSearchRequest.email());
    }
}