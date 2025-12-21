package com.example.study_mate.member.controller;

import com.example.study_mate.global.common.CommonResponse;
import com.example.study_mate.member.dto.req.MemberUpdateRequest;
import com.example.study_mate.member.dto.res.MemberResponse;
import com.example.study_mate.member.security.MemberDetails;
import com.example.study_mate.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;


    // 내 정보 수정
    @PatchMapping("/me")
    public CommonResponse<MemberResponse> updateMyInfo(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody MemberUpdateRequest request
    ) {
        return CommonResponse.onSuccess(
                memberService.updateMyInfo(
                        memberDetails.getMemberId(),
                        request
                )
        );
    }

    // 내 정보 조회
    @GetMapping("/me")
    public CommonResponse<MemberResponse> getMyInfo(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        return CommonResponse.onSuccess(
                memberService.getMyInfo(memberDetails.getMemberId())
        );
    }

}
