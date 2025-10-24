package com.Fitness.AiService.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;


@Service
public class GeminiService {

    // Agar tumhara Spring Boot app ko kisi dusre microservice
    // API se data lena ya bhejna hai —
    //to WebClient use hota hai.
    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String getGeminiApiKey;

    public GeminiService(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.build();
    }

    //postman body request
    // {
    //    "contents": [
    //      {
    //        "parts": [
    //          {
    //            "text": "Explain how AI works in a few words"
    //          }
    //        ]
    //      }
    //    ]
    //  }

    public String getRecommendations(String contains){
        Map<String , Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts",new Object[] {
                                Map.of("text",contains)
                                })
                }
        );

        String response = webClient.post()
                .uri(geminiApiUrl)
                .header("Content-Type","application/json")
                .header("X-goog-api-key",getGeminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }

}
