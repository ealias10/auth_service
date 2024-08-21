package com.example.login.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LoginRequest {

    @NotNull()
    @JsonProperty("username")
    private String username;

    @NotNull()
    @JsonProperty("password")
    private String password;

}
