package com.example.login.controller;

import com.example.login.request.LoginRequest;
import com.example.login.request.UserCreateRequest;
import com.example.login.request.UserPasswordResetRequest;
import com.example.login.service.UserService;
import com.example.login.vo.LoginVO;
import com.example.login.vo.ResponseVO;
import com.example.login.vo.UsersVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final UUID ID = UUID.randomUUID();
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void createUserTest() throws Exception {
        UserCreateRequest userRequest = UserCreateRequest.builder()
                .username("user1")
                .password("password")
                .userRole("")
                .build();
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(getResponse());
        ResultActions resultActions = mockMvc.perform(
                        post("/user/create").content(objectMapper.writeValueAsString(userRequest)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        ResponseVO response =
                objectMapper.readValue(
                        resultActions.andReturn().getResponse().getContentAsString(), ResponseVO.class);
        assertEquals(200, response.getStatus());
        assertEquals("success", response.getMessage());
    }

    @Test
    void resetPasswordByUserTest() throws Exception {
        var userResetRequest = UserPasswordResetRequest.builder().username("user1").currentPassword("password").newPassword("newPassword").build();
        Mockito.when(userService.resetPasswordByUser(Mockito.any())).thenReturn(getResponse());
        ResultActions resultActions = mockMvc.perform(
                        put("/user/reset-password").content(objectMapper.writeValueAsString(userResetRequest)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        ResponseVO response =
                objectMapper.readValue(
                        resultActions.andReturn().getResponse().getContentAsString(), ResponseVO.class);
        assertEquals(200, response.getStatus());
        assertEquals("success", response.getMessage());
    }

    private static UsersVO getResponse() {
        return UsersVO.builder().userId(ID).userName("e").userRole("user").build();
    }

    @Test
    void loginUser() throws Exception {
        var loginRequest = LoginRequest.builder().username("user1").password("password").build();
        Mockito.when(userService.loginUser(Mockito.any())).thenReturn(getLoginResponse());
        ResultActions resultActions = mockMvc.perform(
                        post("/user/login").content(objectMapper.writeValueAsString(loginRequest)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        ResponseVO response =
                objectMapper.readValue(
                        resultActions.andReturn().getResponse().getContentAsString(), ResponseVO.class);
        assertEquals(200, response.getStatus());
        assertEquals("success", response.getMessage());
    }

    private static LoginVO getLoginResponse() {
        return LoginVO.builder().accessToken("eye").build();
    }



}
