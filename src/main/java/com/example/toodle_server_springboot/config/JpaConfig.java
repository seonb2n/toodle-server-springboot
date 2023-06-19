package com.example.toodle_server_springboot.config;

import com.example.toodle_server_springboot.dto.security.UserPrincipal;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    /**
     * CreatedBy 와 ModifiedBy 를 Auditing 하기 위해 필요한 Bean
     *
     * @return
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .map(UserPrincipal.class::cast)
            .map(UserPrincipal::getUsername);
    }

}
