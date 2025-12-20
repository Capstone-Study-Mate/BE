package com.example.study_mate.memberpreference.service;

import com.example.study_mate.global.exception.code.BusinessException;
import com.example.study_mate.global.exception.code.GeneralErrorCode;
import com.example.study_mate.member.domain.Member;
import com.example.study_mate.member.repository.MemberRepository;
import com.example.study_mate.memberpreference.domain.MemberPreference;
import com.example.study_mate.memberpreference.dto.MemberPreferenceUpdateRequest;
import com.example.study_mate.memberpreference.repository.MemberPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberPreferenceService {

    private final MemberRepository memberRepository;
    private final MemberPreferenceRepository memberPreferenceRepository;

    public void updateMyPreference(
            Long memberId,
            MemberPreferenceUpdateRequest request
    ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new BusinessException(GeneralErrorCode.USER_NOT_FOUND)
                );
        MemberPreference preference =
                memberPreferenceRepository.findByMemberId(memberId)
                        .orElseGet(() ->
                                memberPreferenceRepository.save(
                                        MemberPreference.builder()
                                                .member(member)
                                                .build()
                                )
                        );

        preference.update(
                request.studyPurpose(),
                request.interest(),
                request.tendency(),
                request.activityTimes(),
                request.activityDays()
        );
    }
}
