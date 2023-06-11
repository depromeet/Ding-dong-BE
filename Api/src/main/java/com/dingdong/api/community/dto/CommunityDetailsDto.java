package com.dingdong.api.community.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CommunityDetailsDto {
    @Schema(description = "행성 Id")
    private Long communityId;

    @Schema(description = "로고 이미지")
    private String logoImageUrl;

    @Schema(description = "커버 이미지")
    private String coverImageUrl;

    @Schema(description = "커뮤니티 이름")
    private String title;

    @Schema(description = "주민 수")
    private int idCardCount;

    @Schema(description = "소개 글")
    private String description;
}
