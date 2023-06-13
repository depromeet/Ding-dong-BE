package com.dingdong.api.community.dto;


import com.dingdong.domain.domains.community.domain.Community;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
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

    public static CommunityDetailsDto of(Community community, int idCardCount) {
        return CommunityDetailsDto.builder()
                .communityId(community.getId())
                .logoImageUrl(community.getLogoImageUrl())
                .coverImageUrl(community.getCoverImageUrl())
                .title(community.getName())
                .idCardCount(idCardCount)
                .description(community.getDescription())
                .build();
    }
}
