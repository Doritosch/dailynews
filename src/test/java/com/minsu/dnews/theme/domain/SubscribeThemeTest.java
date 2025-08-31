package com.minsu.dnews.theme.domain;

import com.minsu.dnews.subscriber.domain.Subscriber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubscribeThemeTest {

    @Test
    @DisplayName("subTheme 생성")
    void createSubTheme() {
        //given
        Subscriber s1 = Subscriber.builder()
                .email("test@naver.com")
                .build();

        Theme t1 = Theme.builder()
                .name("주식")
                .build();

        //when
        SubscribeTheme st1 = SubscribeTheme.builder()
                .subscriber(s1)
                .theme(t1)
                .build();

        //then
        assertEquals(st1.getSubscriber().getEmail(), s1.getEmail());
        assertEquals(st1.getTheme().getName(), t1.getName());
    }
}