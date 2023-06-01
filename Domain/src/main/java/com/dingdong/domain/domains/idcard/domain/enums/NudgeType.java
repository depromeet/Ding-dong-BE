package com.dingdong.domain.domains.idcard.domain.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NudgeType {
    FRIENDLY("친해지고 싶어요 🥰"),
    SIMILARITY("저와 비슷한 점이 많아요 😎"),
    TALKING("만나면 같이 이야기해요 😀");

    final String value;
}
