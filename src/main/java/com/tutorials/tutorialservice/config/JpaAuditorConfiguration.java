package com.tutorials.tutorialservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "currentAuthorAuditorProvider",
        dateTimeProviderRef = "auditingCurrentDateTimeProvider")
public class JpaAuditorConfiguration {

       @Bean(name = "currentAuthorAuditorProvider")
       public AuditorAware<String> getCurrentAuthor() {
           //return ()-> Optional.ofNullable("Dhaya");
           return ()-> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
       }

       @Bean(name = "auditingCurrentDateTimeProvider")
       public DateTimeProvider getCurrentDate() {
           return ()-> Optional.of(LocalDateTime.now());
       }
}
