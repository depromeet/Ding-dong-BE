package com.dingdong.api.community.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CommunityListDto {
    @Schema(description = "커뮤니티 Id")
    private Long communityId;

    @Schema(description = "썸네일 이미지")
    private String thumbnailImageUrl;

    @Schema(description = "커버 이미지")
    private String coverImageUrl;

    @Schema(description = "커뮤니티 이름")
    private String title;
}
