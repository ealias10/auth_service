package com.example.login.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.login.dao.RoleDao;
import com.example.login.dao.UserDao;
import com.example.login.exception.AuthenticationFailureException;
import com.example.login.exception.RoleNotFoundException;
import com.example.login.exception.UserNotFoundException;
import com.example.login.exception.UsersExistsException;
import com.example.login.model.Role;
import com.example.login.model.Users;
import com.example.login.request.LoginRequest;
import com.example.login.request.UserCreateRequest;
import com.example.login.request.UserPasswordResetRequest;
import com.example.login.utils.GenerateJWTToken;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock UserDao userDao;
  private static final UUID ID = UUID.randomUUID();
  @InjectMocks UserService userService;
  @Mock PasswordEncoder passwordEncoder;

  @Mock RoleDao roleDao;

  @Mock GenerateJWTToken generateJWTToken;

  @Test
  void resetPasswordByUserNotFoundExceptionTest() {
    Users users = null;
    when(userDao.getUserByName(Mockito.anyString())).thenReturn(users);
    assertThrows(
        UserNotFoundException.class,
        () -> userService.resetPasswordByUser(createUserPasswordResetRequest()));
  }

  public UserCreateRequest createUserRequest() {
    return UserCreateRequest.builder().password("password").username("ealias").build();
  }

  @Test
  void createUserByRoleNotFoundExceptionTest() {
    Role role = null;
    when(roleDao.getRoleByName(Mockito.any())).thenReturn(role);
    assertThrows(RoleNotFoundException.class, () -> userService.createUser(createUserRequest()));
  }

  @Test
  void createUserUserExistsTest() {
    when(userDao.getUserByName(Mockito.anyString())).thenReturn(createUser());
    assertThrows(UsersExistsException.class, () -> userService.createUser(createUserRequest()));
  }

  @Test
  void loginUserByAuthenticationFailureExceptionTest() {
    LoginRequest request = LoginRequest.builder().password("password").username("ealias").build();
    when(userDao.getUserByName(Mockito.anyString())).thenReturn(createUser());
    assertThrows(AuthenticationFailureException.class, () -> userService.loginUser(request));
  }

  @Test
  void createUserUserTest()
      throws UsersExistsException, com.example.login.exception.RoleNotFoundException {
    Mockito.when(roleDao.getRoleByName(Mockito.any())).thenReturn(getRole());
    Users users = createUser();
    Mockito.when(userDao.saveUser(Mockito.any())).thenReturn(users);
    var request = createUserRequest();
    var response = userService.createUser(request);
    assertEquals("ealias", response.getUserName());
  }

  public Role getRole() {
    return Role.builder().name("ADMIN").build();
  }

  public Users createUser() {
    return Users.builder().id(ID).username("ealias").password("password").role(getRole()).build();
  }

  public UserPasswordResetRequest createUserPasswordResetRequest() {
    return UserPasswordResetRequest.builder()
        .currentPassword("123")
        .username("ealias")
        .currentPassword("epg")
        .build();
  }
}
