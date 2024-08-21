package com.example.login.config;


import java.util.Optional;


import org.springframework.data.domain.AuditorAware;

import static com.example.login.utils.Utility.getUserName;


public class AuditAware implements AuditorAware<String> {

    public Optional<String> getCurrentAuditor() {
        return Optional.of(getUserName());
    }


}
