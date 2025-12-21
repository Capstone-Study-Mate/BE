package com.example.study_mate.resume.controller;

import com.example.study_mate.global.common.CommonResponse;
import com.example.study_mate.member.domain.Member;
import com.example.study_mate.member.security.MemberDetails;
import com.example.study_mate.resume.dto.req.ResumeCreateRequest;
import com.example.study_mate.resume.service.ResumeCommandService;
import com.example.study_mate.resume.service.ResumeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resumes")
public class ResumeController {

    private final ResumeQueryService resumeQueryService;
    private final ResumeCommandService resumeCommandService;


    // 생성한 이력서 모두 조회
    @GetMapping("/api")
    public CommonResponse<?> getAllResume(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        return CommonResponse.onSuccess(
                resumeQueryService.getAllResume(memberDetails.getMember())
        );
    }


    // 이력서 생성
    @PostMapping("/api/create")
    public CommonResponse<?> createResume(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody ResumeCreateRequest request
            ) {


        return CommonResponse.onSuccess(
                resumeCommandService.createResume(memberDetails.getMember(),request)
        );
    }


}
