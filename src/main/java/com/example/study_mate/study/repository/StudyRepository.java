package com.example.study_mate.study.repository;

import com.example.study_mate.study.domain.Study;
import com.example.study_mate.studyapplication.domain.StudyApplication;
import com.example.study_mate.studyapplication.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudyRepository extends JpaRepository<Study, Long> {
    Page<Study> findByLeader_Id(Long id, Pageable pageable);

    @Query("""
        select s
        from Study s
        where s.leader.id = :memberId
        order by s.updatedAt desc
    """)
    Page<Study> findMyLeaderStudies(
            @Param("memberId") Long memberId,
            Pageable pageable
    );

}
