package com.minsu.dnews.theme.service;

import com.minsu.dnews.theme.infra.ThemeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ThemeService {
    private final ThemeJpaRepository themeJpaRepository;

    public List<String> findAllTheme() {
        return themeJpaRepository.findAll().stream()
                .map(theme -> {
            return theme.getName();
        }).collect(Collectors.toList());
    }
}
