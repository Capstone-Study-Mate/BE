package com.example.study_mate.auth.controller;

import com.example.study_mate.auth.dto.UniversityEmailVerifyRequest;
import com.example.study_mate.auth.service.UniversityAuthService;
import com.example.study_mate.global.common.CommonResponse;
import com.example.study_mate.member.security.MemberDetails;
import com.example.study_mate.university.dto.UniversityEmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/university")
public class UniversityAuthController {

    private final UniversityAuthService universityAuthService;

    @PostMapping("/email")
    public CommonResponse<Void> sendCode(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody UniversityEmailRequest request
    ) {
        universityAuthService.sendUniversityEmailCode(
                memberDetails.getMemberId(),
                request
        );
        return CommonResponse.onSuccess(null);
    }

    @PostMapping("/verify")
    public CommonResponse<Void> verifyUniversityEmail(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody UniversityEmailVerifyRequest request
    ) {
        universityAuthService.verifyUniversityEmail(
                memberDetails.getMemberId(),
                request
        );
        return CommonResponse.onSuccess(null);
    }

}
