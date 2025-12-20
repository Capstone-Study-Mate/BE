package com.example.study_mate.memberpreference.repository;

import com.example.study_mate.memberpreference.domain.MemberPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberPreferenceRepository
        extends JpaRepository<MemberPreference, Long> {

    Optional<MemberPreference> findByMemberId(Long memberId);
}