package com.minsu.dnews.news.domain;

import jakarta.persistence.*;
import lombok.*;

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
    private String content;
    @Column(nullable = false)
    private String newsUrl;

    @OneToMany(mappedBy = "news")
    private List<SendLog> sendLogs = new ArrayList<>();

}
