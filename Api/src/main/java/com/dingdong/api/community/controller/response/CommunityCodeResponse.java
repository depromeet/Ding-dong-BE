package com.dingdong.api.community.controller.response;


import com.dingdong.domain.domains.community.domain.Community;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommunityCodeResponse {

    @Schema(description = "행성 ID", example = "1")
    private Long id;

    @Schema(description = "행성 초대 코드", example = "555555")
    private String invitationCode;

    public static CommunityCodeResponse from(Community community) {
        return new CommunityCodeResponse(community.getId(), community.getInvitationCode());
    }
}
