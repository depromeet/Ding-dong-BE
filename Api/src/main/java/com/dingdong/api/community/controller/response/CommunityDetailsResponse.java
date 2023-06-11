package com.dingdong.api.community.controller.response;


import com.dingdong.api.community.dto.CommunityDetailsDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CommunityDetailsResponse {
    @Schema(description = "커뮤니티 상세정보")
    private CommunityDetailsDto communityDetailsDto;
}
