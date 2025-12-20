package com.example.study_mate.studyapplication.controller;

import com.example.study_mate.global.common.CommonResponse;
import com.example.study_mate.member.security.MemberDetails;
import com.example.study_mate.studyapplication.service.StudyApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class StudyApplicationController {

    private final StudyApplicationService applicationService;

    @PatchMapping("/{applicationId}/approve")
    public CommonResponse<Void> approve(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable Long applicationId
    ) {
        applicationService.approveApplication(
                memberDetails.getMemberId(),
                applicationId
        );
        return CommonResponse.onSuccess(null);
    }

    @PatchMapping("/{applicationId}/reject")
    public CommonResponse<Void> reject(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable Long applicationId
    ) {
        applicationService.rejectApplication(
                memberDetails.getMemberId(),
                applicationId
        );
        return CommonResponse.onSuccess(null);
    }
}
