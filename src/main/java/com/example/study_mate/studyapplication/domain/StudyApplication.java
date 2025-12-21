package com.example.study_mate.studyapplication.domain;

import com.example.study_mate.global.common.BaseEntity;
import com.example.study_mate.member.domain.Member;
import com.example.study_mate.resume.domain.Resume;
import com.example.study_mate.study.domain.Study;
import com.example.study_mate.studyapplication.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyApplication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    public void approve() {
        this.status = ApplicationStatus.APPROVED;
        study.increaseMembers();
    }

    public void reject() {
        this.status = ApplicationStatus.REJECTED;
    }

    public boolean isPending() {
        return this.status == ApplicationStatus.PENDING;
    }
}
