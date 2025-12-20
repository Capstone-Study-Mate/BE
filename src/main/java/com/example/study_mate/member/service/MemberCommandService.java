package com.example.study_mate.member.service;

import com.example.study_mate.member.dto.res.JwtTokenPair;


public interface MemberCommandService {
    Object signup(JwtTokenPair dto);
}
