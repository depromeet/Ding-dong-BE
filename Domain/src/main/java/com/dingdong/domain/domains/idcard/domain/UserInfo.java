package com.dingdong.domain.domains.idcard.domain;


import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Embeddable
@Getter
public class UserInfo {
    @NotNull private Long userId;

    @NotNull private String nickname;

    @NotNull private String aboutMe;

    @Embedded @NotNull private Character character;
}
