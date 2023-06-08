package com.dingdong.domain.domains.idcard.domain.enums;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CharacterType {
    PIPI("FIRST", "피피", "Pipi"),
    LUNA("FIRST", "루나", "Luna");

    final String group;
    final String korean;
    final String english;

    public static List<CharacterType> getCharacterByGroupNumber(String groupNumber) {
        return Arrays.stream(values())
                .filter(characterType -> characterType.getGroup().equals(groupNumber))
                .collect(Collectors.toList());
    }
}
