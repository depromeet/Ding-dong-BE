package com.dingdong.api.notification.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommunityDto {
    @Schema(description = "행성 고유값")
    private Long communityId;

    @Schema(description = "행성 이름")
    private String communityName;
}
