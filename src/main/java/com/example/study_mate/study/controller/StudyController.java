package com.example.study_mate.study.controller;

import com.example.study_mate.global.common.CommonResponse;
import com.example.study_mate.global.common.PageResponse;
import com.example.study_mate.member.security.MemberDetails;
import com.example.study_mate.study.dto.req.StudyCreateRequest;
import com.example.study_mate.study.dto.res.MyStudyResponse;
import com.example.study_mate.study.dto.res.StudyCreateResponse;
import com.example.study_mate.study.dto.res.StudyDetailResponse;
import com.example.study_mate.study.dto.res.StudyListResponse;
import com.example.study_mate.study.service.StudyService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studies")
public class StudyController {

    private final StudyService studyService;

    @PostMapping
    public CommonResponse<StudyCreateResponse> createStudy(
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

    @GetMapping
    public CommonResponse<PageResponse<StudyListResponse>> getStudies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return CommonResponse.onSuccess(
                studyService.getStudies(page, size)
        );
    }


    @GetMapping("/{studyId}")
    public CommonResponse<StudyDetailResponse> getStudyDetail(
            @PathVariable Long studyId
    ) {
        return CommonResponse.onSuccess(
                studyService.getStudyDetail(studyId)
        );
    }

    @GetMapping("/me")
    public CommonResponse<PageResponse<MyStudyResponse>> getMyStudies(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PageableDefault(size = 10)@Parameter(hidden = true) Pageable pageable
    ) {
        return CommonResponse.onSuccess(
                studyService.getMyStudies(memberDetails.getMemberId(), pageable)
        );
    }

    @GetMapping("/recommend")
    public CommonResponse<PageResponse<StudyListResponse>> getRecommendStudies(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return CommonResponse.onSuccess(
                studyService.getRecommendStudies(memberDetails.getMember(), page, size)
        );
    }

}
