package com.example.study_mate.auth.service;

import com.example.study_mate.auth.domain.EmailVerification;
import com.example.study_mate.auth.dto.UniversityEmailVerifyRequest;
import com.example.study_mate.auth.repository.EmailVerificationRepository;
import com.example.study_mate.global.exception.code.BusinessException;
import com.example.study_mate.member.domain.Member;
import com.example.study_mate.member.repository.MemberRepository;
import com.example.study_mate.university.domain.University;
import com.example.study_mate.university.dto.UniversityEmailRequest;
import com.example.study_mate.university.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

import static com.example.study_mate.global.exception.code.GeneralErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UniversityAuthService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final UniversityRepository universityRepository;
    private final MemberRepository memberRepository;

    public void sendUniversityEmailCode(
            Long memberId,
            UniversityEmailRequest request
    ) {
        University university = universityRepository.findById(request.universityId())
                .orElseThrow(() -> new BusinessException(NOT_FOUND));

        if (!request.email().endsWith(university.getEmailDomain())) {
            throw new BusinessException(EMAIL_MISMATCH);
        }

        String code = String.valueOf(
                new Random().nextInt(900000) + 100000
        );

        emailVerificationRepository.save(
                EmailVerification.builder()
                        .email(request.email())
                        .code(code)
                        .expiredAt(LocalDateTime.now().plusMinutes(5))
                        .build()
        );

        // 캡스톤용: 콘솔 출력
        log.info("[학교 이메일 인증 코드] {}", code);
    }

    @Transactional
    public void verifyUniversityEmail(
            Long memberId,
            UniversityEmailVerifyRequest request
    ) {
        //인증 코드 조회
        EmailVerification verification =
                emailVerificationRepository
                        .findByEmailAndCode(request.email(), request.code())
                        .orElseThrow(() ->
                                new BusinessException(INVALID_VERIFICATION_CODE)
                        );

        // 만료 체크
        if (verification.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new BusinessException(VERIFICATION_CODE_EXPIRED);
        }

        // 회원 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new BusinessException(USER_NOT_FOUND)
                );

        // 이메일 도메인으로 대학 조회
        String domain = request.email().split("@")[1];

        University university =
                universityRepository.findByEmailDomain(domain)
                        .orElseThrow(() ->
                                new BusinessException(NOT_FOUND)
                        );

        // 회원에 대학 + 이메일 설정
        member.updateUniversity(university);
        member.updateEmail(request.email());

        // 인증 코드 삭제
        emailVerificationRepository.delete(verification);
    }

}
