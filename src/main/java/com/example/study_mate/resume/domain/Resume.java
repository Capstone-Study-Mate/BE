package com.example.study_mate.resume.domain;

import com.example.study_mate.member.domain.Member;
import com.example.study_mate.resume.dto.req.ResumeRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 1000)
    private String introduction;

    private String title;

    public void updateResume(ResumeRequest request) {
        this.title = request.title();
        this.introduction = request.introduction();
    }
}

