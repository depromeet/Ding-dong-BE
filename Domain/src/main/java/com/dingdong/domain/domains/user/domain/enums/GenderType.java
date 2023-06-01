package com.dingdong.domain.domains.user.domain.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderType {
    MALE("남"),
    FEMALE("여"),
    ETC("기타");

    final String value;
}
