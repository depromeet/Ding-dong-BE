package com.dingdong.api.community.controller.response;


import com.dingdong.api.community.dto.CommunityListDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CommunityListResponse {
    @Schema(description = "내가 속한 행성 목록")
    List<CommunityListDto> communityListDtos;

    public static CommunityListResponse from(List<CommunityListDto> communityListDto) {
        return CommunityListResponse.builder().communityListDtos(communityListDto).build();
    }
}
