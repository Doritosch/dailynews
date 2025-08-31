package com.minsu.dnews.news.infra;

import com.minsu.dnews.news.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsJpaRepository extends JpaRepository<News, Long> {
}
