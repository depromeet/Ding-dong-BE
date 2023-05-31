package com.dingdong.domain.domains.idcard.domain;


import com.dingdong.domain.domains.idcard.domain.enums.CharacterType;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;

@Embeddable
@Getter
public class Character {

    @Enumerated(EnumType.STRING)
    private CharacterType characterType;
}
