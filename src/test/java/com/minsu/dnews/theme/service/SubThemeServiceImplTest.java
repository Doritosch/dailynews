package com.minsu.dnews.theme.service;

import com.minsu.dnews.subscriber.domain.Subscriber;
import com.minsu.dnews.theme.domain.SubscribeTheme;
import com.minsu.dnews.theme.domain.Theme;
import com.minsu.dnews.theme.infra.SubscribeThemeJpaRepository;
import com.minsu.dnews.theme.infra.ThemeJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubThemeServiceImplTest {
    @Mock
    private ThemeJpaRepository themeJpaRepository;
    @Mock
    private SubscribeThemeJpaRepository subscribeThemeJpaRepository;
    @InjectMocks
    private SubThemeServiceImpl subThemeService;


    @Test
    void makeSubThemeTest() {
        // given
        List<String> themeList = List.of("주식", "게임", "정치");

        Subscriber subscriber = new Subscriber("test@naver.com");

        // 가짜 Theme 리스트 (DB에서 가져왔다고 가정)
        List<Theme> fakeThemes = themeList.stream()
                .map(name -> Theme.builder().name(name).build())
                .toList();

        // stub 설정
        when(themeJpaRepository.findByNameIn(themeList))
                .thenReturn(fakeThemes);

        // save() 동작: DB에 저장된 것처럼 "그냥 그대로 반환"
        when(subscribeThemeJpaRepository.save(any(SubscribeTheme.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        List<SubscribeTheme> subscribeThemes = subThemeService.makeSubTheme(themeList, subscriber);

        // then
        assertEquals(3, subscribeThemes.size()); // 3개가 저장됐어야 함
        assertEquals("주식", subscribeThemes.get(0).getTheme().getName());
        assertEquals(subscriber, subscribeThemes.get(0).getSubscriber());
    }
}