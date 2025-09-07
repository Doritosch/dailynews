package com.minsu.dnews.theme.service;

import com.minsu.dnews.subscriber.domain.Subscriber;
import com.minsu.dnews.theme.domain.SubscribeTheme;
import com.minsu.dnews.theme.domain.Theme;
import com.minsu.dnews.theme.infra.SubscribeThemeJpaRepository;
import com.minsu.dnews.theme.infra.ThemeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubThemeServiceImpl implements SubThemeService {

    private final SubscribeThemeJpaRepository subscribeThemeJpaRepository;
    private final ThemeJpaRepository themeJpaRepository;

    @Override
    public List<SubscribeTheme> makeSubTheme(List<String> themeList, Subscriber subscriber) {
        List<SubscribeTheme> subscribeThemes = new ArrayList<>();
        List<Theme> themes = themeJpaRepository.findByNameIn(themeList);

        for(Theme theme : themes) {
            SubscribeTheme subscribeTheme = SubscribeTheme.builder()
                    .theme(theme)
                    .subscriber(subscriber)
                    .build();

            SubscribeTheme savedSubTheme = subscribeThemeJpaRepository.save(subscribeTheme);
            subscribeThemes.add(savedSubTheme);
        }

        return subscribeThemes;
    }

    @Override
    public void removeSubTheme(List<String> themeList, Subscriber subscriber) {
        for(String name : themeList) {
            SubscribeTheme findSubscribeTheme =
                    subscribeThemeJpaRepository.findByThemeNameAndSubscriber(name, subscriber);

            subscribeThemeJpaRepository.delete(findSubscribeTheme);
        }
    }
}
