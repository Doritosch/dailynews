package com.minsu.dnews.subscriber.controller;

import com.minsu.dnews.subscriber.dto.SubscriberRequest;
import com.minsu.dnews.subscriber.dto.SubscriberResponse;
import com.minsu.dnews.subscriber.dto.SubscriberSearchRequest;
import com.minsu.dnews.subscriber.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sub")
public class SubscriberController {

    private final SubscriberService subscriberService;

    @PostMapping()
    public ResponseEntity<SubscriberResponse> subscribe(
            @RequestBody SubscriberRequest request
    ) {
        SubscriberResponse response = subscriberService.subscribe(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<SubscriberResponse> findSubscriberByEmail(
            @RequestBody SubscriberSearchRequest request
            ) {
        SubscriberResponse response = subscriberService.searchEmail(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping("/{subEmail}")
    public void unsubscribe(@PathVariable String subEmail) {
        subscriberService.unsubscribe(subEmail);
    }
}
