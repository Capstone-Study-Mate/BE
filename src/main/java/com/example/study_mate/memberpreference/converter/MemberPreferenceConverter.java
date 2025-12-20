package com.example.study_mate.memberpreference.converter;

import com.example.study_mate.memberpreference.domain.MemberPreference;
import com.example.study_mate.memberpreference.dto.res.MemberPreferenceResponse;
import org.springframework.stereotype.Component;

@Component
public class MemberPreferenceConverter {

    public MemberPreferenceResponse toResponse(MemberPreference preference) {
        return new MemberPreferenceResponse(
                preference.getStudyPurpose(),
                preference.getInterest(),
                preference.getTendency(),
                preference.getActivityTimes(),
                preference.getActivityDays()
        );
    }
}
