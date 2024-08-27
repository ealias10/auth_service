package com.example.login.service;

import com.example.login.dao.RoleDao;
import com.example.login.dao.UserDao;
import com.example.login.exception.*;
import com.example.login.mapper.UserMapper;
import com.example.login.model.OtpInfo;
import com.example.login.model.Role;
import com.example.login.model.Users;
import com.example.login.request.LoginRequest;
import com.example.login.request.RefreshTokenRequest;
import com.example.login.request.UserCreateRequest;
import com.example.login.request.UserPasswordResetRequest;
import com.example.login.utils.GenerateJWTToken;
import com.example.login.utils.Utility;
import com.example.login.vo.LoginVO;
import com.example.login.vo.UsersVO;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

@org.springframework.stereotype.Service
@Transactional(rollbackOn = Exception.class)
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final UserDao userDao;

  private final RoleDao roleDao;

  private final EmailService emailService;

  private final PasswordEncoder passwordEncoder;
  private final GenerateJWTToken generateJWTToken;

  @Value("${access.token.expiry.minutes}")
  private long accessTokenExpiry;

  @Value("${user.forgot.password.otp.expiry.minutes}")
  private long otpExpiryTime;

  @Value("${spring.security.jwt.secret}")
  private String jwtSecret;

  @Value("${refresh.token.expiry.minutes}")
  private String refreshTokenExpiry;

  public UsersVO createUser(UserCreateRequest request)
      throws RoleNotFoundException, UsersExistsException {
    try {
      Users existingUser = userDao.getUserByName(request.getUsername());
      if (existingUser != null) {
        throw new UsersExistsException(request.getUsername());
      }
      Role role = roleDao.getRoleByName(request.getUserRole());
      if (role == null) {
        throw new RoleNotFoundException(request.getUserRole());
      }
      Users users = UserMapper.createUser(request, role);
      users.setPassword(passwordEncoder.encode(users.getPassword()));
      Users savedUser = userDao.saveUser(users);
      log.info("Create user successfully, Request: {}", request);
      return UserMapper.createUserVO(savedUser);
    } catch (Exception e) {
      log.error("Error while creating user,  Request: {}", request);
      throw e;
    }
  }

  public UsersVO resetPasswordByUser(UserPasswordResetRequest userResetRequest)
      throws UserNotFoundException {
    try {
      Users existingUser = userDao.getUserByName(userResetRequest.getUsername());
      if (existingUser == null
          || !passwordEncoder.matches(
              userResetRequest.getCurrentPassword(), existingUser.getPassword())) {
        throw new UserNotFoundException(userResetRequest.getUsername());
      }
      existingUser.setPassword(passwordEncoder.encode(userResetRequest.getNewPassword()));
      Users updateUser = userDao.saveUser(existingUser);
      log.info("Updated password successfully, User: {}", userResetRequest.getNewPassword());
      return UserMapper.createUserVO(updateUser);
    } catch (Exception e) {
      log.error("Error while reset password, userId: {}", userResetRequest.getNewPassword());
      throw e;
    }
  }

  private String generatePasswordOtpForUser(Users userInfo) {
    String otp = Utility.getRandomOTPCode();
    Date expiryDate = new Date(System.currentTimeMillis() + (otpExpiryTime * 60 * 1000));
    userInfo.setPasswordOTP(OtpInfo.builder().otp(otp).expiry(expiryDate.getTime()).build());
    userDao.saveUser(userInfo);
    return otp;
  }

  public String forgotPasswordByUser(String userNameOrEmail)
      throws InvalidUserNameOrEmailException, EmailServiceException {

    try {
      Users userInfo = userDao.getActiveUserByNameOrEmail(userNameOrEmail);
      if (userInfo != null) {
        String otp = generatePasswordOtpForUser(userInfo);
        emailService.sendForgotPasswordEmail(userInfo.getEmail(), otp, otpExpiryTime);
        log.info("Successfully send OTP to user email,Email id : {}", userInfo.getEmail());
        return String.format("OTP successfully send to this email :%s", userInfo.getEmail());
      }
      throw new InvalidUserNameOrEmailException(userNameOrEmail);
    } catch (Exception e) {
      log.error(
          "Error while forgot user password from user service, Username or email: {}",
          userNameOrEmail,
          e);
      throw e;
    }
  }

  public LoginVO loginUser(LoginRequest loginRequest) throws AuthenticationFailureException {
    try {
      Users users = userDao.getUserByName(loginRequest.getUsername());
      if (users == null
          || !passwordEncoder.matches(loginRequest.getPassword(), users.getPassword())) {
        throw new AuthenticationFailureException();
      }
      String accessToken = getAccessToken(users);
      String refreshToken = getRefreshToken();
      users.setRefreshToken(refreshToken);
      userDao.saveUser(users);
      log.info("User login successfully, Username: {}", loginRequest.getUsername());
      return UserMapper.createLoginVO(accessToken, refreshToken);
    } catch (Exception e) {
      log.error("Error while login, userName: {}", loginRequest.getUsername());
      throw e;
    }
  }

  private String getAccessToken(Users user) {
    Map<String, Object> claims = new HashMap<>();
    String role = user.getRole().getName();
    claims.put("sub", user.getId().toString());
    claims.put("name", user.getUsername());
    claims.put("role", role);
    return generateJWTToken.createJWTToken(accessTokenExpiry, claims);
  }

  public LoginVO refreshToken(RefreshTokenRequest refreshTokenRequest)
      throws UserNotFoundException, AuthenticationFailureException {
    String userIdFromToken = null;
    try {
      userIdFromToken = Utility.getUserId();
      UUID userId = UUID.fromString(userIdFromToken);
      Users existingUser = userDao.getUserById(userId);
      if (existingUser == null) {
        throw new UserNotFoundException(UUID.fromString(userIdFromToken));
      }
      if (existingUser.getRefreshToken() == null) {
        throw new AuthenticationFailureException();
      } else if (Boolean.FALSE.equals(
              Utility.validateToken(refreshTokenRequest.getRefreshToken(), jwtSecret))
          || !existingUser.getRefreshToken().equals(refreshTokenRequest.getRefreshToken())) {
        existingUser.setRefreshToken(null);
        userDao.saveUser(existingUser);
        throw new AuthenticationFailureException();
      }
      String accessToken = getAccessToken(existingUser);
      String refreshToken = getRefreshToken();
      log.info("User token successfully refreshed, userId: {}", userId);
      existingUser.setRefreshToken(refreshToken);
      userDao.saveUser(existingUser);
      return UserMapper.createLoginVO(accessToken, refreshToken);
    } catch (Exception e) {
      log.error("Error while refresh token, userId: {}", userIdFromToken);
      throw e;
    }
  }

  private String getRefreshToken() {
    Map<String, Object> claims = new HashMap<>();
    return generateJWTToken.createJWTToken(Long.parseLong(refreshTokenExpiry), claims);
  }
}
