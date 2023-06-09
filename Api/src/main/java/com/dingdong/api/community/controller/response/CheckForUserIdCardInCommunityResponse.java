package com.dingdong.api.community.controller.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckForUserIdCardInCommunityResponse {

    @Schema(description = "커뮤니티 Id")
    private final Long communityId;

    @Schema(description = "유저가 행성에 주민증이 있는지 여부")
    private final boolean isUserMakeIdCard;

    public static CheckForUserIdCardInCommunityResponse of(
            Long communityId, boolean isUserMakeIdCard) {
        return CheckForUserIdCardInCommunityResponse.builder()
                .communityId(communityId)
                .isUserMakeIdCard(isUserMakeIdCard)
                .build();
    }
}
