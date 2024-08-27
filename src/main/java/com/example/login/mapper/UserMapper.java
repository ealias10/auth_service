package com.example.login.mapper;

import com.example.login.model.Role;
import com.example.login.model.Users;
import com.example.login.request.UserCreateRequest;
import com.example.login.vo.LoginVO;
import com.example.login.vo.UsersVO;

public class UserMapper {
    public static Users createUser(UserCreateRequest request, Role role) {
        return Users.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .role(role)
                .email(request.getEmail())
                .active(true)
                .build();
    }

    public static UsersVO createUserVO(Users user) {
        return UsersVO.builder()
                .userId(user.getId())
                .userName(user.getUsername())
                .userRole(user.getRole().getName())
                .build();
    }

    public static LoginVO createLoginVO(String accessToken, String refreshToken) {
        return LoginVO.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }

}
