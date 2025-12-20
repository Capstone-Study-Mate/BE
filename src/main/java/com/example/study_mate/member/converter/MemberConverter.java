package com.example.study_mate.member.converter;

import com.example.study_mate.member.domain.Member;
import com.example.study_mate.member.dto.req.MemberUpdateRequest;
import com.example.study_mate.member.dto.res.MemberResponse;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

//    public MemberLoginResDTO toDto(Member member, String accessToken) {
//        return MemberLoginResDTO.builder()
//                .memberId(member.getId())
//                .accessToken(accessToken)
//                .build();
//   }

    public void updateMemberFromRequest(
            Member member,
            MemberUpdateRequest request
    ) {
        member.updateMyInfo(
                request.email(),
                request.nickname(),
                request.major(),
                request.phoneNumber()
        );
    }

    public MemberResponse toResponse(Member member) {
        return MemberResponse.from(member);
    }
}