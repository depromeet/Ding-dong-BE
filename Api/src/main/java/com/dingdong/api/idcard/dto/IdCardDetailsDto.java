package com.dingdong.api.idcard.dto;


import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import com.dingdong.domain.domains.idcard.domain.enums.CharacterType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IdCardDetailsDto {
    @Schema(description = "주민증 Id")
    private Long idCardId;

    @Schema(description = "주민증 작성자 Id")
    private Long userId;

    @Schema(description = "이름")
    private String nickname;

    @Schema(description = "유저 프로필 이미지")
    private String profileImageUrl;

    @Schema(description = "자기소개")
    private String aboutMe;

    @Schema(description = "키워드 정보")
    private List<KeywordDto> keywords;

    @Schema(description = "캐릭터 타입")
    private CharacterType characterType;

    @Schema(description = "댓글 개수")
    private int commentCount;

    @Schema(description = "상대 유저가 나에게 보낸 콕 찌르기 타입")
    private String toNudgeType;

    @Schema(description = "읽지 않은 딩동 개수")
    private int unreadNudges;

    public static IdCardDetailsDto of(
            IdCard idCard, List<KeywordDto> keywordDtos, int commentCount, String toNudgeType) {
        return IdCardDetailsDto.builder()
                .idCardId(idCard.getId())
                .userId(idCard.getUserInfo().getUserId())
                .nickname(idCard.getNickname())
                .profileImageUrl(idCard.getProfileImageUrl())
                .aboutMe(idCard.getAboutMe())
                .keywords(keywordDtos)
                .characterType(idCard.getCharacterType())
                .commentCount(commentCount)
                .toNudgeType(toNudgeType)
                .build();
    }
}
