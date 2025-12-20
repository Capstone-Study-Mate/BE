package com.example.study_mate.member.security;

import com.example.study_mate.member.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MemberDetails implements UserDetails {

    private final Member member;

    public MemberDetails(Member member) {
        this.member = member;
    }

    // 🔑 Spring Security가 "username"으로 쓰는 값
    @Override
    public String getUsername() {
        return member.getId().toString();
    }

    // 🔒 JWT 방식에서는 비밀번호 사용 안 함
    @Override
    public String getPassword() {
        return null;
    }

    public Long getMemberId(){return member.getId();}

    // 🛡 권한 (ROLE_USER, ROLE_ADMIN 등)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(member.getRole().name())
        );
    }

    // ⬇️ 아래는 보통 true 고정
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // 🔍 필요하면 Member 접근용 getter
    public Member getMember() {
        return member;
    }
}
