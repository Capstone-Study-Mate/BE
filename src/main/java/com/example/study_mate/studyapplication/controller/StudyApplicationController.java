package com.example.study_mate.studyapplication.controller;

import com.example.study_mate.global.common.CommonResponse;
import com.example.study_mate.global.common.PageResponse;
import com.example.study_mate.member.security.MemberDetails;
import com.example.study_mate.studyapplication.dto.res.MyStudyApplicationResponse;
import com.example.study_mate.studyapplication.dto.res.StudyApplicationListResponse;
import com.example.study_mate.studyapplication.dto.res.StudyApplicationResponse;
import com.example.study_mate.studyapplication.service.StudyApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class StudyApplicationController {

    private final StudyApplicationService applicationService;

    @PostMapping("/{studyId}/apply")
    public CommonResponse<StudyApplicationResponse> apply(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable Long studyId
    ) {
        applicationService.apply(
                memberDetails.getMemberId(),
                studyId
        );
        return CommonResponse.onSuccess(null);
    }

    @PatchMapping("/{applicationId}/approve")
    public CommonResponse<StudyApplicationResponse> approve(
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
    public CommonResponse<StudyApplicationResponse> reject(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable Long applicationId
    ) {
        applicationService.rejectApplication(
                memberDetails.getMemberId(),
                applicationId
        );
        return CommonResponse.onSuccess(null);
    }

    @GetMapping("/me")
    public CommonResponse<PageResponse<MyStudyApplicationResponse>> getMyApplications(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return CommonResponse.onSuccess(
                applicationService.getMyApplications(memberDetails.getMemberId(), page, size)
        );
    }

    @GetMapping("/studies/{studyId}/applications")
    public CommonResponse<PageResponse<StudyApplicationListResponse>> getStudyApplications(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable Long studyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return CommonResponse.onSuccess(
                applicationService.getStudyApplications(
                        studyId,
                        memberDetails.getMemberId(),
                        pageable
                )
        );
    }


}
