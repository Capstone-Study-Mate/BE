package com.example.study_mate.auth.repository;

import com.example.study_mate.auth.domain.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationRepository
        extends JpaRepository<EmailVerification, Long> {

    Optional<EmailVerification> findByEmailAndCode(
            String email,
            String code
    );
}
