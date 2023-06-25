package com.dingdong.domain.domains.idcard.domain.vo;


import com.dingdong.domain.domains.idcard.domain.enums.CharacterType;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Character {

    @Enumerated(EnumType.STRING)
    private CharacterType characterType;

    private Character(CharacterType characterType) {
        this.characterType = characterType;
    }

    public static Character toEntity(CharacterType characterType) {
        return new Character(characterType);
    }
}
