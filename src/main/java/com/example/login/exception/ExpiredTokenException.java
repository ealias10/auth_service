package com.example.login.exception;

import com.example.login.utils.Constants;
import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends IqnessException {
    private static final long serialVersionUID = 30763009752460581L;

    public ExpiredTokenException() {
        super("Token is expired", HttpStatus.UNAUTHORIZED, Constants.EXPIRED_TOKEN_ERROR_CODE);
    }
}
