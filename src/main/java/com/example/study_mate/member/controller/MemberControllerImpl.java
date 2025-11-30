package com.example.study_mate.member.controller;

import com.example.study_mate.global.common.CommonResponse;
import com.example.study_mate.member.dto.res.MemberLoginResDTO;
import com.example.study_mate.member.dto.req.MemberLoginReqDTO;
import com.example.study_mate.member.service.MemberCommandService;
import com.example.study_mate.member.service.MemberQueryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberControllerImpl {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

//    @PostMapping("/sign-up")
//    public CommonResponse<MemberLoginResDTO> signUp(
//            @RequestBody @Valid MemberLoginReqDTO dto
//    ){
//        return CommonResponse.onSuccess(memberCommandService.signup(dto));
//    }
    // 회원가입

    // 로그인
    @PostMapping("/login")
    public CommonResponse<MemberLoginResDTO> login(
            @RequestBody @Valid MemberLoginReqDTO dto
    ){
        return CommonResponse.onSuccess(memberQueryService.login(dto));
    }

}
