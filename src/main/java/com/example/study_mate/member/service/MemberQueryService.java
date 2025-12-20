package com.example.study_mate.member.service;

import com.example.study_mate.member.dto.res.JwtTokenPair;


public interface MemberQueryService {
    Object login(JwtTokenPair dto);
}
