package com.dingdong.domain.domains.idcard.domain.vo;


import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class UserInfo {
    @NotNull private Long userId;

    @NotNull private String nickname;

    @NotNull private String aboutMe;

    @Embedded @NotNull private Character character;

    private UserInfo(Long userId, String nickname, String aboutMe, Character character) {
        this.userId = userId;
        this.nickname = nickname;
        this.aboutMe = aboutMe;
        this.character = character;
    }

    public static UserInfo create(
            Long userId, String nickname, String aboutMe, Character character) {
        return new UserInfo(userId, nickname, aboutMe, character);
    }
}
