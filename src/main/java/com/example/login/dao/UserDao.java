package com.example.login.dao;
import com.example.login.model.Users;
import com.example.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserDao {

    @Autowired
    UserRepository userRepository;

    public Users getUserByName(String name) {
        return userRepository.getUserByName(name);
    }

    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    public Users getActiveUserByNameOrEmail(String usernameOrEmail) {
        return userRepository.findUserByNameOrEmailAndActiveTrue(usernameOrEmail).orElse(null);
    }

    public Users getUserById(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }



}
