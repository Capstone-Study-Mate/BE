package com.example.study_mate.member.service;


import com.example.study_mate.global.exception.code.BusinessException;
import com.example.study_mate.global.exception.code.GeneralErrorCode;
import com.example.study_mate.global.security.CustomUserDetails;
import com.example.study_mate.global.utill.JwtUtil;
import com.example.study_mate.member.converter.MemberConverter;
import com.example.study_mate.member.domain.Member;
import com.example.study_mate.member.dto.req.MemberLoginReqDTO;
import com.example.study_mate.member.dto.res.MemberLoginResDTO;
import com.example.study_mate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    // 회원가입
    @Override
    public MemberLoginResDTO login(
            MemberLoginReqDTO dto
    ) {

        // Member 조회
        Member member = memberRepository.findByUsername(dto.username())
                .orElseThrow(() -> new BusinessException(GeneralErrorCode.USER_NOT_FOUND));

        // 비밀번호 검증
        if (!encoder.matches(dto.password(), member.getPassword())) {
            throw new BusinessException(GeneralErrorCode.USER_NOT_FOUND);
        }

        // JWT 토큰 발급용 UserDetails
        CustomUserDetails userDetails = new CustomUserDetails(member);

        // 엑세스 토큰 발급
        String accessToken = jwtUtil.createAccessToken(userDetails);

        // DTO 조립
        return memberConverter.toDto(member, accessToken);

    }
}
