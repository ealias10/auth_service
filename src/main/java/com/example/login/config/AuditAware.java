package com.example.login.config;

import static com.example.login.utils.Utility.getUserName;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class AuditAware implements AuditorAware<String> {

  public Optional<String> getCurrentAuditor() {
    return Optional.of(getUserName());
  }
}
