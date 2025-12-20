package com.example.study_mate.memberpreference.service;

import com.example.study_mate.global.exception.code.BusinessException;
import com.example.study_mate.global.exception.code.GeneralErrorCode;
import com.example.study_mate.member.domain.Member;
import com.example.study_mate.member.repository.MemberRepository;
import com.example.study_mate.memberpreference.converter.MemberPreferenceConverter;
import com.example.study_mate.memberpreference.domain.MemberPreference;
import com.example.study_mate.memberpreference.dto.req.MemberPreferenceUpdateRequest;
import com.example.study_mate.memberpreference.dto.res.MemberPreferenceResponse;
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
    private final MemberPreferenceConverter converter;

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
    @Transactional(readOnly = true) //읽기 전용 트랜잭션 명시적 표시
    public MemberPreferenceResponse getMyPreference(Long memberId) {
        MemberPreference preference =
                memberPreferenceRepository.findByMemberId(memberId)
                        .orElse(null);

        if (preference == null) {
            return null;
        }
        preference.getActivityTimes().size();
        preference.getActivityDays().size();

        return converter.toResponse(preference);
    }
}
