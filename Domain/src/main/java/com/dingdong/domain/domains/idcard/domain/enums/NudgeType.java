package com.dingdong.domain.domains.idcard.domain.enums;

import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_NUDGE_TYPE;

import com.dingdong.core.exception.BaseException;
import java.util.Arrays;
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

    public static NudgeType findNudgeType(String value) {
        return Arrays.stream(values())
                .filter(c -> c.name().equals(value.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new BaseException(NOT_FOUND_NUDGE_TYPE));
    }
}
