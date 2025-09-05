package com.minsu.dnews.subscriber.infra;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SubscriberJpaRepositoryTest {
    @Autowired
    private SubscriberJpaRepository subscriberJpaRepository;

    @Test
    void test() {
        Map<String, List<String>> collect = subscriberJpaRepository.findSubscribersEmailAndTheme().stream()
                .collect(Collectors.groupingBy(
                        row -> (String) row[0],
                        Collectors.mapping(row -> (String) row[1], Collectors.toList())
                ));

        for(String key : collect.keySet()) {
            System.out.println(key);
            for (String theme : collect.get(key)) {
                System.out.println(theme);
            }
        }
    }

}