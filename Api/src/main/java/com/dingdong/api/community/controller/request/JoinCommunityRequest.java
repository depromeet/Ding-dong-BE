package com.dingdong.api.community.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class JoinCommunityRequest {
    @Schema(description = "가입할 행성 ID", example = "1")
    private Long communityId;
}
