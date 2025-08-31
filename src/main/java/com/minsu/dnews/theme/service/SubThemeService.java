package com.minsu.dnews.theme.service;

import com.minsu.dnews.subscriber.domain.Subscriber;
import com.minsu.dnews.theme.domain.SubscribeTheme;
import com.minsu.dnews.theme.domain.Theme;

import java.util.List;

public interface SubThemeService {
    List<SubscribeTheme> makeSubTheme(List<String> themeList, Subscriber subscriber);
    void removeSubTheme(List<String> themeList, Subscriber subscriber);
}
