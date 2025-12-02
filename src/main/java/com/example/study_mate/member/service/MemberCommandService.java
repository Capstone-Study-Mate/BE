package com.example.study_mate.member.service;

import com.example.study_mate.member.dto.req.MemberLoginReqDTO;

public interface MemberCommandService {
    Object signup(MemberLoginReqDTO dto);
}
