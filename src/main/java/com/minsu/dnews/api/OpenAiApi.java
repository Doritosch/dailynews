package com.minsu.dnews.api;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

//@Component
//public class OpenAiApi {
//
//    private final String prompts = "Please summary this text into Korean in two lines.";
//    @Bean
//    public CommandLineRunner runner(ChatClient.Builder builder) {
//        return args -> {
//            ChatClient chatClient = builder.build();
//            String response = chatClient.prompt().call().content();
//            System.out.println(response);
//        };
//    }
//
//
//}
