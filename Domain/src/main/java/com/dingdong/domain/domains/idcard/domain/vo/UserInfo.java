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

    private String profileImageUrl;

    @NotNull private String nickname;

    @NotNull private String aboutMe;

    @Embedded @NotNull private Character character;

    private UserInfo(
            Long userId,
            String profileImageUrl,
            String nickname,
            String aboutMe,
            Character character) {
        this.userId = userId;
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
        this.aboutMe = aboutMe;
        this.character = character;
    }

    public static UserInfo create(
            Long userId,
            String profileImageUrl,
            String nickname,
            String aboutMe,
            Character character) {
        return new UserInfo(userId, profileImageUrl, nickname, aboutMe, character);
    }
}
