package com.dingdong.domain.domains.alarm.domain.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmType {
    COMMENT("댓글"),
    NUDGE("콕찌르기");

    final String value;
}
