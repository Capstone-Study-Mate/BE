package com.example.study_mate.memberpreference.domain;

import com.example.study_mate.member.domain.Member;
import com.example.study_mate.memberpreference.dto.req.MemberPreferenceUpdateRequest;
import com.example.study_mate.memberpreference.enums.ActivityDay;
import com.example.study_mate.memberpreference.enums.ActivityTime;
import com.example.study_mate.memberpreference.enums.StudyPurpose;
import com.example.study_mate.memberpreference.enums.Tendency;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "memberpreferences")
public class MemberPreference {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private StudyPurpose studyPurpose;

    @Enumerated(EnumType.STRING)
    private Tendency tendency;

    private String interest;

    // enum + Set (중복 선택 가능)
    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "member_activity_time",
            joinColumns = @JoinColumn(name = "member_preference_id")
    )
    @Enumerated(EnumType.STRING)
    private Set<ActivityTime> activityTimes = new HashSet<>();

    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "member_activity_day",
            joinColumns = @JoinColumn(name = "member_preference_id")
    )
    @Enumerated(EnumType.STRING)
    private Set<ActivityDay> activityDays = new HashSet<>();


    public void update(MemberPreferenceUpdateRequest request) {
        this.studyPurpose = request.studyPurpose();
        this.interest = request.interest();
        this.tendency = request.tendency();
        this.activityTimes = request.activityTimes();
        this.activityDays = request.activityDays();
    }
}

