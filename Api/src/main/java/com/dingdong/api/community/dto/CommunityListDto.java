package com.dingdong.api.community.dto;


import com.dingdong.domain.domains.community.domain.Community;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityListDto {
    @Schema(description = "행성 Id")
    private Long communityId;

    @Schema(description = "로고 이미지")
    private String logoImageUrl;

    @Schema(description = "행성 이름")
    private String title;

    @Schema(description = "주민 수")
    private int idCardCount;

    public static CommunityListDto from(Community community, int idCardCount) {
        return CommunityListDto.builder()
                .communityId(community.getId())
                .logoImageUrl(community.getLogoImageUrl())
                .title(community.getName())
                .idCardCount(idCardCount)
                .build();
    }
}
