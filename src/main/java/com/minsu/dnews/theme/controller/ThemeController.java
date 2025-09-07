package com.minsu.dnews.theme.controller;

import com.minsu.dnews.theme.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ThemeController {
    private final ThemeService themeService;

    @GetMapping("/themes")
    public ResponseEntity<List<String>> getAllTheme() {
        List<String> allTheme = themeService.findAllTheme();

        return ResponseEntity.status(HttpStatus.OK).body(allTheme);
    }
}
