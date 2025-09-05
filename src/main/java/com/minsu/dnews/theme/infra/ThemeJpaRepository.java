package com.minsu.dnews.theme.infra;

import com.minsu.dnews.subscriber.dto.SubscriberEmailThemes;
import com.minsu.dnews.theme.domain.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ThemeJpaRepository extends JpaRepository<Theme, Long> {
    List<Theme> findByNameIn(List<String> themes);
}
