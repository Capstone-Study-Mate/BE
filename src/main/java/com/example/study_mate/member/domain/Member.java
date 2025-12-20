package com.example.study_mate.member.domain;

import com.example.study_mate.global.common.BaseEntity;
import com.example.study_mate.member.enums.Gender;
import com.example.study_mate.member.enums.Role;
import com.example.study_mate.university.domain.University;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;    //소셜 로그인시 사용 x

    @Email
    private String email;

    private String name;       // 실명

    private String nickname;

    private String phoneNumber;

    private String major;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @Column
    private String provider;

    @Column
    private String providerId;    // 네이버 id

}

