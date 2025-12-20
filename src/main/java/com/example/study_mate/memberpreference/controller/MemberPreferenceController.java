package com.example.study_mate.memberpreference.controller;

import com.example.study_mate.global.common.CommonResponse;
import com.example.study_mate.member.security.MemberDetails;
import com.example.study_mate.memberpreference.dto.MemberPreferenceUpdateRequest;
import com.example.study_mate.memberpreference.service.MemberPreferenceService;
import lombok.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberPreferenceController {

    private final MemberPreferenceService memberPreferenceService;

    @PatchMapping("/me/preferences")
    public CommonResponse<Void> updateMyPreference(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody MemberPreferenceUpdateRequest request
    ) {
        memberPreferenceService.updateMyPreference(
                memberDetails.getMemberId(),
                request
        );

        return CommonResponse.onSuccess(null);
    }
}
