package com.example.login.exception;

import com.example.login.utils.Constants;
import org.springframework.http.HttpStatus;

public class AuthenticationFailureException extends IqnessException {
  private static final long serialVersionUID = 30763009752460581L;

  public AuthenticationFailureException() {
    super(
        "Authentication failed due to invalid credentials",
        HttpStatus.UNAUTHORIZED,
        Constants.INVALID_USER_CREDENTIALS,
        "Invalid credentials",
        "Username or password is not correct");
  }
}
