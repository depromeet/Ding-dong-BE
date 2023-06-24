package com.dingdong.domain.domains.idcard.domain.enums;

import static com.dingdong.domain.domains.user.exception.UserErrorCode.NOT_FOUND_CHARACTER_TYPE;

import com.dingdong.core.exception.BaseException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum CharacterType {
    BUDDY,
    TOBY,
    PIPI,
    TRUE;

    public static CharacterType findCharacterType(String type) {
        return Arrays.stream(values())
                .filter(c -> c.name().equals(type))
                .findAny()
                .orElseThrow(() -> new BaseException(NOT_FOUND_CHARACTER_TYPE));
    }
}
