package com.dingdong.api.idcard.dto;


import com.dingdong.domain.domains.idcard.domain.vo.UserInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoDto {

    private final Long userId;

    private final String profileImageUrl;

    private final String nickname;

    public static UserInfoDto from(UserInfo userInfo) {
        return UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .profileImageUrl(userInfo.getProfileImageUrl())
                .nickname(userInfo.getNickname())
                .build();
    }
}
