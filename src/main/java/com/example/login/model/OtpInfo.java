package com.example.login.model;

import java.io.Serializable;
import lombok.*;

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
