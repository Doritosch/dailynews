package com.minsu.dnews.theme.infra;

import com.minsu.dnews.subscriber.domain.Subscriber;
import com.minsu.dnews.subscriber.infra.SubscriberJpaRepository;
import com.minsu.dnews.theme.domain.SubscribeTheme;
import com.minsu.dnews.theme.domain.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
class SubscribeThemeJpaRepositoryTest {

    @Autowired
    private SubscriberJpaRepository subscriberJpaRepository;
    @Autowired
    private ThemeJpaRepository themeJpaRepository;
    @Autowired
    private SubscribeThemeJpaRepository subscribeThemeJpaRepository;
    @BeforeEach
    void setUp() {
        themeJpaRepository.save(new Theme("주식"));
        themeJpaRepository.save(new Theme("게임"));
        themeJpaRepository.save(new Theme("정치"));
    }
    @Test
    void saveTest() {
        //given
        Subscriber subscriber = new Subscriber("test@naver.com", LocalDateTime.now());
        List<Theme> themes = new ArrayList<>();

        Subscriber savedSubscriber = subscriberJpaRepository.save(subscriber);

        themes.add(themeJpaRepository.save(new Theme("주식")));
        themes.add(themeJpaRepository.save(new Theme("게임")));
        themes.add(themeJpaRepository.save(new Theme("정치")));
        //when
        for(Theme theme : themes) {
            subscribeThemeJpaRepository.save(SubscribeTheme.builder()
                            .theme(theme)
                            .subscriber(savedSubscriber)
                            .build());
        }

        //then
        assertEquals(subscribeThemeJpaRepository.findAll().size(), 3);
    }
}