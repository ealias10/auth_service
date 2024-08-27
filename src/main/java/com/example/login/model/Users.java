package com.example.login.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Users extends Audit {
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  private UUID id;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "refresh_token")
  private String refreshToken;

  @Column(name = "active")
  private Boolean active;

  @Column(name = "email")
  private String email;

  @Type(type = "jsonb")
  @Column(name = "email_otp", columnDefinition = "jsonb")
  private OtpInfo emailOTP;

  @Type(type = "jsonb")
  @Column(name = "password_otp", columnDefinition = "jsonb")
  private OtpInfo passwordOTP;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", referencedColumnName = "id")
  @ToString.Exclude
  private Role role;
}
