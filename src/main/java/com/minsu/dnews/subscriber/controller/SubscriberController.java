package com.minsu.dnews.subscriber.controller;

import com.minsu.dnews.subscriber.dto.SubscriberRequest;
import com.minsu.dnews.subscriber.dto.SubscriberResponse;
import com.minsu.dnews.subscriber.dto.SubscriberSearchRequest;
import com.minsu.dnews.subscriber.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/sub")
public class SubscriberController {

    private final SubscriberService subscriberService;

    @PostMapping
    public String subscribe(
            @ModelAttribute SubscriberRequest request
    ) {
        SubscriberResponse response = subscriberService.subscribe(request);
        log.info("Subscribed: " + response.email());
        return "subscribe-success";
    }
    @DeleteMapping("/{subEmail}")
    public void unsubscribe(@PathVariable String subEmail) {
        subscriberService.unsubscribe(subEmail);
    }
}
