package com.dingdong.api.community.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ValidateInvitationCodeDto {
    @Schema(description = "행성 ID")
    private Long communityId;

    @Schema(description = "행성 이름")
    private String name;

    public static ValidateInvitationCodeDto of(Long communityId, String name) {
        return ValidateInvitationCodeDto.builder().communityId(communityId).name(name).build();
    }
}
