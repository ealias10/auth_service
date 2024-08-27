package com.example.login.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GenerateJWTToken {
  @Value("${spring.security.jwt.secret}")
  private String jwtSecret;

  @Value("${access.token.expiry.minutes}")
  private Integer expiry;

  public String createJWTToken(long expiryInMinutes, Map<String, Object> claims) {
    Algorithm algorithm;
    final Map<String, Object> jwtHeader;
    jwtHeader = new HashMap<>();
    jwtHeader.put("alg", "HS256");
    jwtHeader.put("typ", "JWT");
    Date expiryDate = new Date(System.currentTimeMillis() + (expiryInMinutes * expiry * 1000));
    try {
      algorithm = Algorithm.HMAC256(jwtSecret);
      return JWT.create()
          .withHeader(jwtHeader)
          .withPayload(claims)
          .withExpiresAt(expiryDate)
          .sign(algorithm);
    } catch (Exception e) {
      log.error("failed to generate token ", e);
      throw e;
    }
  }
}
