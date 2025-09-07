package com.minsu.dnews.subscriber.service;

import com.minsu.dnews.subscriber.domain.Subscriber;
import com.minsu.dnews.subscriber.domain.SubscriberStatus;
import com.minsu.dnews.subscriber.dto.SubscriberEmailThemes;
import com.minsu.dnews.subscriber.dto.SubscriberRequest;
import com.minsu.dnews.subscriber.dto.SubscriberResponse;
import com.minsu.dnews.subscriber.dto.SubscriberSearchRequest;
import com.minsu.dnews.subscriber.infra.SubscriberJpaRepository;
import com.minsu.dnews.theme.domain.SubscribeTheme;
import com.minsu.dnews.theme.domain.Theme;
import com.minsu.dnews.theme.infra.ThemeJpaRepository;
import com.minsu.dnews.theme.service.SubThemeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SubscriberServiceImpl implements SubscriberService {
    private final SubscriberJpaRepository subscriberJpaRepository;
    private final ThemeJpaRepository themeJpaRepository;
    private final SubThemeService subThemeService;
    @Autowired
    public SubscriberServiceImpl(
            SubscriberJpaRepository subscriberJpaRepository,
            ThemeJpaRepository themeJpaRepository,
            SubThemeService subThemeService
    ) {
        this.subscriberJpaRepository = subscriberJpaRepository;
        this.themeJpaRepository = themeJpaRepository;
        this.subThemeService = subThemeService;
    }


    @Transactional
    @Override
    public SubscriberResponse subscribe(SubscriberRequest request) {
        if (subscriberJpaRepository.findByEmail(request.email()) != null) {
            throw new IllegalStateException("이미 구독한 이메일입니다.");
        }

        Subscriber subscriberEntity = request.toEntity();
        Subscriber saveEntity = subscriberJpaRepository.save(subscriberEntity);

        subThemeService.makeSubTheme(request.themeList(), saveEntity);

        return SubscriberResponse.from(saveEntity);
    }

    @Override
    public SubscriberResponse searchEmail(SubscriberSearchRequest searchRequest) {
        Subscriber subscriber = subscriberJpaRepository.findByEmail(searchRequest.email());

        if (subscriber.getStatus() == SubscriberStatus.UNSUBSCRIBE) {
            throw new IllegalStateException("구독을 해지한 회원입니다.");
        }

        return SubscriberResponse.from(subscriber);
    }
    public Map<String, List<String>> getEmailAndSubThemesFromSubscriber() {
        return subscriberJpaRepository.findSubscribersEmailAndTheme().stream()
                .collect(Collectors.groupingBy(
                        row -> (String) row[0],
                        Collectors.mapping(row -> (String) row[1], Collectors.toList())
                ));
    }
    @Transactional
    @Override
    public void unsubscribe(String email) {
        Subscriber subscriber = subscriberJpaRepository.findByEmail(email);
        subscriber.unsubService();
    }
}
