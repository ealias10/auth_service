package com.example.login.exception;


import com.example.login.utils.Constants;
import org.springframework.http.HttpStatus;

public class EmailServiceException extends IqnessException {

    private static final long serialVersionUID = 30763009752460581L;

    public EmailServiceException(String email) {
        super(
                "Failed to send email to :" + email,
                HttpStatus.INTERNAL_SERVER_ERROR,
                Constants.INTERNAL_SERVER_ERROR_CODE);
    }
}
