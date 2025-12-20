package com.example.study_mate.study.service;

import com.example.study_mate.global.exception.code.BusinessException;
import com.example.study_mate.member.domain.Member;
import com.example.study_mate.member.repository.MemberRepository;
import com.example.study_mate.study.domain.Study;
import com.example.study_mate.study.dto.req.StudyCreateRequest;
import com.example.study_mate.study.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.study_mate.global.exception.code.GeneralErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyService {
    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;

    public Long createStudy(Long memberId, StudyCreateRequest request) {
        Member leader = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

        Study study = Study.builder()
                .title(request.title())
                .description(request.description())
                .leader(leader)
                .university(leader.getUniversity())
                .maxMembers(request.maxMembers())
                .currentMembers(1)
                .activityTimes(request.activityTimes())
                .activityDays(request.activityDays())
                .build();

        return studyRepository.save(study).getId();
    }
}
