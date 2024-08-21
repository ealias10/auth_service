package com.example.login.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RefreshTokenRequest {

    @NotEmpty
    @JsonProperty("refresh_token")
    private String refreshToken;
}
