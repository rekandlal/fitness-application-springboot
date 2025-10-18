package com.Fitness.UserService.Controller;

import com.Fitness.UserService.DTO.RegisterRequest;
import com.Fitness.UserService.DTO.UserResponse;
import com.Fitness.UserService.Models.User;
import com.Fitness.UserService.Repository.UserRepository;
import com.Fitness.UserService.Services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    //Client POST request bhejta hai /register pe with JSON data.
    //Spring JSON ko RegisterRequest me convert karta hai.
    //userService.register user ko save karta hai aur UserResponse return karta hai.
    //Controller 200 OK ke sath user info wapas bhej deta hai.
    @PostMapping("register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.ok(userService.register(request));
        //ResponseEntity Spring ka HTTP response wrapper hai.
        //Isse hum status code, headers, aur body ko customize kar sakte hain.
        //Matlab, ye sirf data nahi, pura HTTP response manage karta hai.
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> ValidateUser(@PathVariable Long userId){
        return ResponseEntity.ok(userService.existByUserId(userId));
    }


}
