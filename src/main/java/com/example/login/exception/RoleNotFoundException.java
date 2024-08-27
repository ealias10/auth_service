package com.example.login.exception;

import com.example.login.utils.Constants;
import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends IqnessException {
  private static final long serialVersionUID = 30763009752460581L;

  public RoleNotFoundException(String name) {
    super(
        "Role is not found with name: " + name,
        HttpStatus.NOT_FOUND,
        Constants.ROLE_NOT_FOUND_ERROR_CODE);
  }
}
