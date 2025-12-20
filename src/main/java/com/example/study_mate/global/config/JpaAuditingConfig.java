package com.example.study_mate.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// JPA Auditing 설정을 위한 Configuration 클래스
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
