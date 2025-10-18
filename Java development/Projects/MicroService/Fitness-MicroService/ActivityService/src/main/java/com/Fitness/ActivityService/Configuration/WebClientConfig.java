package com.Fitness.ActivityService.Configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


//Problem :
//ActivityService ko UserService se user ka naam ya info chahiye.
//Hum kaise request bhejenge UserService ko aur data wapas laenge?

//Solution:

//WebClient → ek tool (client) jo ActivityService me rahega aur UserService ko HTTP request bhejega.
//UserService ka response wapas lega aur use process karega.
@Configuration
public class WebClientConfig {

    //@LoadBalanced → ye important hai!

    //Iska matlab: agar aap service name use karte ho jaise "http://USERSERVICE", to Spring automatically Eureka se service ka real instance IP:port resolve karega.
    //Multiple instances hone par ye load balancing bhi karta hai.

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }



    @Bean
    public WebClient userServiceWebClient(WebClient.Builder webClientBuilder){
        return webClientBuilder.baseUrl("http://USERSERVICE").build();
    }
}
