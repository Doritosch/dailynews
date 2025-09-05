package com.minsu.dnews.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NaverNewsApiTest {
    @Autowired
    private NaverNewsApi naverNewsApi;

    @Test
    void searchNewsTest() {
        String it = naverNewsApi.getNews("IT");
        System.out.println(it);
    }
}