package com.example.login.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.login.model.Role;
import com.example.login.model.Users;
import com.example.login.repository.UserRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

public class UserDaoTest {
  @InjectMocks UserDao userDao;
  @Mock UserRepository userRepository;
  private static final UUID ID = UUID.randomUUID();

  @Test
  void getUserByNameTest() {
    Mockito.when(userRepository.getUserByName(Mockito.anyString())).thenReturn(getUsers());
    var response = userDao.getUserByName("ealias");
    assertEquals(ID, response.getId());
  }

  private static Users getUsers() {
    return Users.builder()
        .active(true)
        .password("124")
        .role(Role.builder().id(ID).name("ADMIN").build())
        .username("ealias")
        .id(ID)
        .build();
  }

  @Test
  void saveUserTest() {
    Mockito.when(userRepository.save(Mockito.any())).thenReturn(getUsers());
    var response = userDao.saveUser(new Users());
    assertEquals(ID, response.getId());
  }
}
