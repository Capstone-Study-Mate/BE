package com.example.study_mate.memberpreference.controller;

import com.example.study_mate.global.common.CommonResponse;
import com.example.study_mate.member.security.MemberDetails;
import com.example.study_mate.memberpreference.dto.req.MemberPreferenceUpdateRequest;
import com.example.study_mate.memberpreference.dto.res.MemberPreferenceResponse;
import com.example.study_mate.memberpreference.service.MemberPreferenceService;
import lombok.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberPreferenceController {

    private final MemberPreferenceService memberPreferenceService;


    // 내 성향 수정
    @PatchMapping("/me/preferences")
    public CommonResponse<Void> updateMyPreference(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody MemberPreferenceUpdateRequest request
    ) {
        memberPreferenceService.updateMyPreference(memberDetails.getMember(), request);

        return CommonResponse.onSuccess(null);
    }


    // 내 성향 조회
    @GetMapping("/me/preferences")
    public CommonResponse<MemberPreferenceResponse> getMyPreference(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        return CommonResponse.onSuccess(
                memberPreferenceService.getMyPreference(
                        memberDetails.getMember()
                )
        );
    }

}
