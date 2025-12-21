package com.example.study_mate.resume.service;

import com.example.study_mate.global.exception.code.BusinessException;
import com.example.study_mate.member.domain.Member;
import com.example.study_mate.resume.domain.Resume;
import com.example.study_mate.resume.dto.req.ResumeRequest;
import com.example.study_mate.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.example.study_mate.global.exception.code.GeneralErrorCode.*;

@Service
@RequiredArgsConstructor
public class ResumeCommandServiceImpl implements ResumeCommandService {

    private final ResumeRepository resumeRepository;

    // 이력서 생성 로직
    @Transactional
    @Override
    public Boolean createResume(Member member, ResumeRequest request) {

        if(request.introduction().isEmpty() || request.title().isEmpty())
            throw new BusinessException(MISSING_PARAMETER);

        Resume resume = Resume.builder()
                .member(member)
                .title(request.title())
                .introduction(request.introduction())
                .build();

        resumeRepository.save(resume);

        return Boolean.TRUE;
    }


    @Transactional
    @Override
    public Object updateResume(Member member, Long id, ResumeRequest request) {

        if(request.introduction().isEmpty() || request.title().isEmpty())
            throw new BusinessException(MISSING_PARAMETER);

        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND));


        if(!Objects.equals(resume.getMember().getId(), member.getId()))
            throw new BusinessException(FORBIDDEN);

        resume.updateResume(request);

        return Boolean.TRUE;
    }

    @Transactional
    @Override
    public Object deleteResume(Member member, Long id) {

        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND));

        if(!Objects.equals(resume.getMember().getId(), member.getId()))
            throw new BusinessException(FORBIDDEN);

        resumeRepository.delete(resume);

        return Boolean.TRUE;
    }
}
