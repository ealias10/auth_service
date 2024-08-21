package com.example.login.controller;

import com.example.login.exception.*;
import com.example.login.request.LoginRequest;
import com.example.login.request.RefreshTokenRequest;
import com.example.login.request.UserCreateRequest;
import com.example.login.request.UserPasswordResetRequest;
import com.example.login.service.UserService;
import com.example.login.vo.LoginVO;
import com.example.login.vo.ResponseVO;
import com.example.login.vo.UsersVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;




@RestController
@Slf4j
@Validated
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ResponseVO<Object>> createUser(
            @RequestBody(required = true) UserCreateRequest request) throws RoleNotFoundException, UsersExistsException {
        ResponseVO<Object> response = new ResponseVO<>();
        UsersVO usersVO = userService.createUser(request);
        response.addData(usersVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseVO<String>> forgotPasswordByUser(
            @RequestParam String userNameOrEmail) throws InvalidUserNameOrEmailException, EmailServiceException {
        log.info("Invoked forgot password by user API with username or email: {}", userNameOrEmail);
        ResponseVO<String> response = new ResponseVO<>();
        response.addData(userService.forgotPasswordByUser(userNameOrEmail));
        log.info(
                "Successfully called forgot password by user API with username or email: {}",
                userNameOrEmail);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ResponseVO<Object>> resetPasswordByUser(
            @RequestBody(required = true) UserPasswordResetRequest userResetRequest) throws UserNotFoundException {
        ResponseVO<Object> response = new ResponseVO<>();
        UsersVO usersVO = userService.resetPasswordByUser(userResetRequest);
        response.addData(usersVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseVO<Object>> loginUser(
            @RequestBody(required = true) LoginRequest loginRequest) throws AuthenticationFailureException {
        ResponseVO<Object> response = new ResponseVO<>();
        LoginVO loginVO = userService.loginUser(loginRequest);
        response.addData(loginVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseVO<Object>> refreshToken(
            @RequestBody(required = true) RefreshTokenRequest refreshTokenRequest) throws UserNotFoundException, AuthenticationFailureException {
        ResponseVO<Object> response = new ResponseVO<>();
        LoginVO loginVO = userService.refreshToken(refreshTokenRequest);
        response.addData(loginVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
