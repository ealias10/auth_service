package com.example.login.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OtpInfo implements Serializable {

    private String otp;
    private Long expiry;
}
