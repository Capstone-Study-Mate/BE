package com.example.study_mate.studyapplication.repository;

import com.example.study_mate.studyapplication.domain.StudyApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudyApplicationRepository extends JpaRepository<StudyApplication, Long> {
    boolean existsByStudyIdAndMemberId(Long studyId, Long memberId);
    Page<StudyApplication> findByMember_Id(Long memberId, Pageable pageable);
    Page<StudyApplication> findByStudy_Id(Long studyId, Pageable pageable);
}
