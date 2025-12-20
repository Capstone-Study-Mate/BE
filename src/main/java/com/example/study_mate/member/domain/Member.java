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

    private String name;

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
    private String providerId;


    public void updateMyInfo(
            String email,
            String nickname,
            String major,
            String phoneNumber
    ) {
        this.email = email;
        this.nickname = nickname;
        this.major = major;
        this.phoneNumber = phoneNumber;
    }

    @PrePersist
    public void prePersist() {
        if (this.role == null) {
            this.role = Role.ROLE_USER;
        }
    }

    public void updateUniversity(University university) {
        this.university = university;
    }

    public void updateEmail(String email) {
        this.email = email;
    }
}

