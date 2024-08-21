package com.example.login.exception;
import com.example.login.utils.Constants;
import org.springframework.http.HttpStatus;

public class InvalidUserNameOrEmailException    extends IqnessException  {
    public InvalidUserNameOrEmailException(String userNameOrEmail) {
        super(
                "Entered User Name Or Email is invalid :" + userNameOrEmail,
                HttpStatus.BAD_REQUEST,
                Constants.INVALID_USERNAME_OR_EMAIL_ERROR_CODE);
    }
}


