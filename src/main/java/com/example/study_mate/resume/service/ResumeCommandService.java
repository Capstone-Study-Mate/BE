package com.example.study_mate.resume.service;

import com.example.study_mate.member.domain.Member;
import com.example.study_mate.resume.dto.req.ResumeCreateRequest;

public interface ResumeCommandService {
    Boolean createResume(Member member, ResumeCreateRequest request);
}
