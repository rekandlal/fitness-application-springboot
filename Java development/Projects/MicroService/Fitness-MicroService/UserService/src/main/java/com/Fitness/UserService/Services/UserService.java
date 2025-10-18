package com.Fitness.UserService.Services;

import com.Fitness.UserService.DTO.RegisterRequest;
import com.Fitness.UserService.DTO.UserResponse;
import com.Fitness.UserService.Models.User;
import com.Fitness.UserService.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email Already Exist");
        }

        // "Client se jo data aaya hai, usko database object me copy
        // kar rahe hain taaki save kar sakein."
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());

        // save in database and also
        //savedUser = database se jo user object return hua, usme id
        // , createdAt jaise fields automatically fill ho gaye.
        User savedUser = userRepository.save(user);


        // client register karne ke baad server send response Isliye hum UserResponse
        // DTO bana rahe hain aur sirf client ko bhejne wale fields copy kar rahe hain:
        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setFirstName(savedUser.getFirstName());
        userResponse.setLastName(savedUser.getLastName());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setUpdateAt(savedUser.getUpdateAt());

        return userResponse;

        //Client Form (DTO) → Service → Entity (DB compatible) → save → DB
        //DB returns Entity → Service → Response DTO → Client JSON

    }

    public UserResponse getUserProfile(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdateAt(user.getUpdateAt());

        return userResponse;
    }

    public Boolean existByUserId(Long userId){
        log.info("Calling User Service For {}" , userId );

        return userRepository.existsById(userId);
    }
}
