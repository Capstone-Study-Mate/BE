package com.example.study_mate.study.controller;

import com.example.study_mate.global.common.CommonResponse;
import com.example.study_mate.member.security.MemberDetails;
import com.example.study_mate.study.dto.req.StudyCreateRequest;
import com.example.study_mate.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studies")
public class StudyController {

    private final StudyService studyService;

    @PostMapping
    public CommonResponse<Long> createStudy(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody StudyCreateRequest request
    ) {
        return CommonResponse.onSuccess(
                studyService.createStudy(
                        memberDetails.getMemberId(),
                        request
                )
        );
    }
}
