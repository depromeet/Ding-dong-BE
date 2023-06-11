package com.dingdong.api.community.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CommunityListDto {
    @Schema(description = "행성 Id")
    private Long communityId;

    @Schema(description = "로고 이미지")
    private String logoImageUrl;

    @Schema(description = "행성 이름")
    private String title;

    @Schema(description = "주민 수")
    private int idCardCount;
}
