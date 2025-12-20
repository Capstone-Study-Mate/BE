package com.example.study_mate.member.security;

import com.example.study_mate.member.domain.Member;
import com.example.study_mate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) {
        Member member = memberRepository.findById(Long.valueOf(memberId))
                .orElseThrow(() ->
                        new UsernameNotFoundException("회원이 존재하지 않습니다. id=" + memberId)
                );

        return new MemberDetails(member);
    }
}
