package com.example.study_mate.memberpreference.repository;

import com.example.study_mate.member.domain.Member;
import com.example.study_mate.memberpreference.domain.MemberPreference;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberPreferenceRepository
        extends JpaRepository<MemberPreference, Long> {

    @EntityGraph(attributePaths = {"activityTimes", "activityDays"})
    Optional<MemberPreference> findByMember(Member member);

}