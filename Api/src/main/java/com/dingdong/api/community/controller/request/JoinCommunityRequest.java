package com.dingdong.api.community.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class JoinCommunityRequest {
    @Schema(description = "가입할 행성 ID", example = "1")
    @NotNull(message = "행성 ID를 입력해주세요.")
    private Long communityId;
}
