package com.dingdong.domain.domains.user.domain.enums;

import static java.util.Arrays.*;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderType {
    MALE("남"),
    FEMALE("여"),
    ETC("기타");

    final String value;

    public static GenderType findGenderType(String gender) {
        for (GenderType genderType : values()) {
            if (genderType.name().toLowerCase().equals(gender)) return genderType;
        }
        return ETC;
    }
}
