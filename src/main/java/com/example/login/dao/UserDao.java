package com.example.login.dao;

import com.example.login.model.Users;
import com.example.login.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDao {

  private final UserRepository userRepository;

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
