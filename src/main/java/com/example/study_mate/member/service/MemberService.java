package com.example.study_mate.member.service;

import com.example.study_mate.global.exception.code.BusinessException;
import com.example.study_mate.member.converter.MemberConverter;
import com.example.study_mate.member.domain.Member;
import com.example.study_mate.member.dto.req.MemberUpdateRequest;
import com.example.study_mate.member.dto.res.MemberResponse;
import com.example.study_mate.member.repository.MemberRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.study_mate.global.exception.code.GeneralErrorCode;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;

    // 내 정보 수정 로직
    public MemberResponse updateMyInfo(
            Long memberId,
            MemberUpdateRequest request
    ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new BusinessException(GeneralErrorCode.USER_NOT_FOUND)
                );

        // JPA의 더티 체킹을 활용하여 자동 업데이트
        memberConverter.updateMemberFromRequest(member, request);

        return memberConverter.toResponse(member);
    }


    // 내 정보 조회 로직
    @Transactional(readOnly = true)
    public MemberResponse getMyInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new BusinessException(GeneralErrorCode.USER_NOT_FOUND)
                );

        return MemberResponse.from(member);
    }
}

