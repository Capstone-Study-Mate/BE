package com.example.study_mate.studyapplication.repository;

import com.example.study_mate.studyapplication.domain.StudyApplication;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudyApplicationRepository extends JpaRepository<StudyApplication, Long> {
}
