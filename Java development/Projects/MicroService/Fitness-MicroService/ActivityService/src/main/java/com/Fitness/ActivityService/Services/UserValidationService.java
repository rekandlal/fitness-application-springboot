package com.Fitness.ActivityService.Services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;


// this service consume user Service api take user data and then use it
@Service
@AllArgsConstructor
@Slf4j
public class UserValidationService {

    private final WebClient userServiceWebClient;

    // this service consume user Service api take user data and then use it
    // here we consume userId and validate that and then return boolean
    public boolean validateUser(Long userId){
        log.info("Calling User Service For {}" , userId );


        try{
            return Boolean.TRUE.equals(userServiceWebClient.get()
                    .uri("/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block());
        }catch (WebClientResponseException e){
            e.printStackTrace();
        }

        return false;
    }



}
