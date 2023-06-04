package com.dingdong.domain.domains.user.domain.enums;


import java.util.stream.Stream;
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
        return Stream.of(GenderType.values())
                .filter(genderType -> genderType.name().toLowerCase().equals(gender))
                .findFirst()
                .orElse(ETC);
    }
}
