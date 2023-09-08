package com.dingdong.api.nudge.controller.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NudgeDetailResponse {

    @Schema(description = "상대 유저가 나에게 보낸 콕 찌르기 타입")
    private String toNudgeType;

    public static NudgeDetailResponse from(String toNudgeType) {
        return NudgeDetailResponse.builder().toNudgeType(toNudgeType).build();
    }
}
