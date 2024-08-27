package com.example.login.exception;

import com.example.login.utils.Constants;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends IqnessException {
  private static final long serialVersionUID = 30763009752460581L;

  public InvalidTokenException() {
    super("Token is invalid", HttpStatus.UNAUTHORIZED, Constants.INVALID_TOKEN_ERROR_CODE);
  }
}
