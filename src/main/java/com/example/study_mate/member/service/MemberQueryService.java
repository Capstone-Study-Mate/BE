package com.example.study_mate.member.service;

import com.example.study_mate.member.dto.req.MemberLoginReqDTO;
import com.example.study_mate.member.dto.res.MemberLoginResDTO;

public interface MemberQueryService {
    MemberLoginResDTO login(MemberLoginReqDTO dto);
}
