package com.dingdong.api.community.controller.response;


import com.dingdong.api.community.dto.CommunityDetailsDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityDetailsResponse {
    @Schema(description = "행성 상세정보")
    private CommunityDetailsDto communityDetailsDto;

    public static CommunityDetailsResponse from(CommunityDetailsDto dto) {
        return CommunityDetailsResponse.builder().communityDetailsDto(dto).build();
    }
}
