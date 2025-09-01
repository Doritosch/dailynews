package com.minsu.dnews.theme.domain;

import com.minsu.dnews.news.domain.News;
import com.minsu.dnews.subscriber.domain.Subscriber;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "THEME_ID")
    private Long id;

    private String name;

    public Theme(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "theme")
    private List<News> newsList = new ArrayList<>();
}
