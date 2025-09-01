package com.minsu.dnews.news.domain;

import com.minsu.dnews.theme.domain.Theme;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class News {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COLUMN_ID")
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String originallink;
    @Column(nullable = false)
    private String description;

    private LocalDateTime pubDate;

    @OneToMany(mappedBy = "news")
    private List<SendLog> sendLogs = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "THEME_ID")
    private Theme theme;
    public News(String title, String originallink, String description, LocalDateTime pubDate) {
        this.title = title;
        this.originallink = originallink;
        this.description = description;
        this.pubDate = pubDate;
    }
}
