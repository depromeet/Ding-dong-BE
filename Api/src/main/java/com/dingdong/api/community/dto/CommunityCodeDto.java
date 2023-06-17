package com.dingdong.api.community.dto;


import com.dingdong.domain.domains.community.domain.Community;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityCodeDto {

    @Schema(description = "행성 ID", example = "1")
    private Long id;

    @Schema(description = "행성 초대 코드", example = "555555")
    private String invitationCode;

    public static CommunityCodeDto from(Community community) {
        return CommunityCodeDto.builder()
                .id(community.getId())
                .invitationCode(community.getInvitationCode())
                .build();
    }
}
