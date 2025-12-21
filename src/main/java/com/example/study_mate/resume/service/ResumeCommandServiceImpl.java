package com.example.study_mate.resume.service;

import com.example.study_mate.global.exception.code.BusinessException;
import com.example.study_mate.member.domain.Member;
import com.example.study_mate.resume.domain.Resume;
import com.example.study_mate.resume.dto.req.ResumeCreateRequest;
import com.example.study_mate.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.study_mate.global.exception.code.GeneralErrorCode.MISSING_PARAMETER;

@Service
@RequiredArgsConstructor
public class ResumeCommandServiceImpl implements ResumeCommandService {

    private final ResumeRepository resumeRepository;

    @Transactional
    @Override
    public Boolean createResume(Member member, ResumeCreateRequest request) {

        if(request.introduction().isEmpty() || request.title().isEmpty())
            throw new BusinessException(MISSING_PARAMETER);

        Resume resume = Resume.builder()
                .member(member)
                .title(request.title())
                .introduction(request.introduction())
                .build();

        return Boolean.TRUE;
    }
}
