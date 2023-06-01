package com.dingdong.domain.domains.idcard.domain;


import com.dingdong.domain.domains.idcard.domain.enums.CharacterType;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Embeddable
@Getter
public class UserInfo {
    @NotNull private Long userId;

    @NotNull private String nickname;

    @NotNull private String aboutMe;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CharacterType characterType;
}
