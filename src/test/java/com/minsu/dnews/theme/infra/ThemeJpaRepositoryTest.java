package com.minsu.dnews.theme.infra;

import com.minsu.dnews.theme.domain.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.minsu.dnews.theme.infra.ThemeJpaRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ThemeJpaRepositoryTest {

    @Autowired
    private ThemeJpaRepository themeJpaRepository;
    private Theme theme;

    @Test
    @DisplayName("Theme 저장 테스트")
    void createTheme() {
        //given
        theme = Theme.builder()
                .name("레이싱")
                .build();
        //when
        Theme savedTheme = themeJpaRepository.save(theme);

        //then
        assertTrue(savedTheme.getId() != null);
        assertEquals(savedTheme.getName(), theme.getName());
    }
}