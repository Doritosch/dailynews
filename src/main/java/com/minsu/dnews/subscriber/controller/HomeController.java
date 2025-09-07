package com.minsu.dnews.subscriber.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String getHome() {
        return "index";
    }
    @GetMapping("/subscribe")
    public String getSubscribe() {
        return "subscribe";
    }
}
