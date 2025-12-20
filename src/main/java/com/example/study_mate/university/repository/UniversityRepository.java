package com.example.study_mate.university.repository;

import com.example.study_mate.university.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UniversityRepository
        extends JpaRepository<University, Long> {

    Optional<University> findByEmailDomain(String emailDomain);
}
