package com.dingdong.api.community.dto;


import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyInfoInCommunityDto {

    @Schema(description = "유저 ID")
    private Long userId;

    @Schema(description = "행성 내 주민증에 등록한 유저 닉네임")
    private String nickname;

    @Schema(description = "행성 내 주민증에 등록한 유저 프로필 이미지 Url")
    private String profileImageUrl;

    @Schema(description = "관리자 인지 여부")
    private Boolean isAdmin;

    @Schema(description = "내 주민증 존재 여부")
    private Boolean isExistsIdCard;

    @Schema(description = "주민증의 댓글 개수")
    private Long commentCount;

    public static MyInfoInCommunityDto of(
            Long userId, Optional<IdCard> idCard, boolean isAdmin, Long commentCount) {
        return MyInfoInCommunityDto.builder()
                .userId(userId)
                .nickname(idCard.map(IdCard::getNickname).orElse(null))
                .profileImageUrl(idCard.map(IdCard::getProfileImageUrl).orElse(null))
                .isAdmin(isAdmin)
                .isExistsIdCard(idCard.isPresent())
                .commentCount(commentCount)
                .build();
    }
}
