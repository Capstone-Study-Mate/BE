package com.example.study_mate.resume.service;

import com.example.study_mate.member.domain.Member;
import com.example.study_mate.resume.dto.req.ResumeRequest;

public interface ResumeCommandService {
    Boolean createResume(Member member, ResumeRequest request);

    Object updateResume(Member member, Long id, ResumeRequest request);

    Object deleteResume(Member member, Long id);
}
