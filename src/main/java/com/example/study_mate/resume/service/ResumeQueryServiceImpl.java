package com.example.study_mate.resume.service;

import com.example.study_mate.member.domain.Member;
import com.example.study_mate.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResumeQueryServiceImpl implements ResumeQueryService {

    private final ResumeRepository resumeRepository;

    @Transactional(readOnly = true)
    @Override
    public Object getAllResume(Member member) {

        return null;
    }
}
