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
public class MemberPreferenceService {

    private final MemberPreferenceRepository memberPreferenceRepository;
    private final MemberPreferenceConverter converter;


    // 내 성향 수정 로직
    @Transactional
    public void updateMyPreference(
            Member member,
            MemberPreferenceUpdateRequest request
    ) {

        MemberPreference pref = memberPreferenceRepository.findByMember(member)
                .orElseGet(()-> {
                    MemberPreference newPref = MemberPreference.builder()
                            .member(member)
                            .studyPurpose(request.studyPurpose())
                            .tendency(request.tendency())
                            .interest(request.interest())
                            .activityTimes(request.activityTimes())
                            .activityDays(request.activityDays())
                            .build();

                    return memberPreferenceRepository.save(newPref);  // 없으면 생성
                });


        // 존재하는 경우 수정
        if(pref.getId() != null)
            pref.update(request);

    }


    // 내 성향 조회 로직
    @Transactional(readOnly = true) //읽기 전용 트랜잭션 명시적 표시
    public MemberPreferenceResponse getMyPreference(Member member) {

        MemberPreference preference =
                memberPreferenceRepository.findByMember(member)
                        .orElseThrow(() -> new BusinessException(GeneralErrorCode.INVALID_INPUT_VALUE));

        return converter.toResponse(preference);
    }
}
