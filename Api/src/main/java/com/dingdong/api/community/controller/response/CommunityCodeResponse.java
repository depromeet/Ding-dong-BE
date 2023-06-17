package com.dingdong.api.community.controller.response;


import com.dingdong.api.community.dto.CommunityCodeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityCodeResponse {

    @Schema(description = "행성 코드 정보")
    private CommunityCodeDto communityCodeDto;

    public static CommunityCodeResponse from(CommunityCodeDto dto) {
        return CommunityCodeResponse.builder().communityCodeDto(dto).build();
    }
}
