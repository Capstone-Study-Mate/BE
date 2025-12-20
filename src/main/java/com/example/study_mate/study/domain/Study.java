package com.example.study_mate.study.domain;

import com.example.study_mate.global.common.BaseEntity;
import com.example.study_mate.member.domain.Member;
import com.example.study_mate.memberpreference.enums.ActivityDay;
import com.example.study_mate.memberpreference.enums.ActivityTime;
import com.example.study_mate.university.domain.University;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "studies")
public class Study extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int currentMembers;

    private int maxMembers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id")
    private Member leader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<ActivityTime> activityTimes;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<ActivityDay> activityDays;
}
