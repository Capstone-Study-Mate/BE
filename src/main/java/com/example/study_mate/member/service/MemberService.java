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

    public MemberResponse updateMyInfo(
            Long memberId,
            MemberUpdateRequest request
    ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new BusinessException(GeneralErrorCode.USER_NOT_FOUND)
                );

        memberConverter.updateMemberFromRequest(member, request);

        return memberConverter.toResponse(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse getMyInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new BusinessException(GeneralErrorCode.USER_NOT_FOUND)
                );

        return MemberResponse.from(member);
    }
}

