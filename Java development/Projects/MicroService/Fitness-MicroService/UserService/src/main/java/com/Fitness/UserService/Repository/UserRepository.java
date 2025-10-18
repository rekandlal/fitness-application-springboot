package com.Fitness.UserService.Repository;

import com.Fitness.UserService.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
    //save(), findAll(), findById() jaise functions automatically milte hain.
}
