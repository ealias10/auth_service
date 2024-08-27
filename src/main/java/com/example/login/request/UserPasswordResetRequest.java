package com.example.login.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserPasswordResetRequest {

  @NotNull()
  @JsonProperty(value = "user_name")
  private String username;

  @NotNull()
  @JsonProperty("current_password")
  private String currentPassword;

  @NotNull()
  @JsonProperty("new_password")
  private String newPassword;
}
