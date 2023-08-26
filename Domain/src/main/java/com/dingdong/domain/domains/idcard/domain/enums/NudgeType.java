package com.dingdong.domain.domains.idcard.domain.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NudgeType {
    FRIENDLY("친해지고 싶어요"),
    SIMILARITY("저와 비슷해요"),
    TALKING("같이 밥 한끼 해요"),
    NICE_TO_MEET_YOU("만나서 반가워요"),
    ;

    final String value;
}
