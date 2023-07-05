package com.dingdong.api.community.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CheckInvitationCodeDto {
    @Schema(description = "행성 ID")
    private Long communityId;

    @Schema(description = "행성 이름")
    private String name;

    public static CheckInvitationCodeDto of(Long communityId, String name) {
        return CheckInvitationCodeDto.builder().communityId(communityId).name(name).build();
    }
}
