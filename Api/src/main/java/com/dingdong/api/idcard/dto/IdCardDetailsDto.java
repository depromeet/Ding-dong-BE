package com.dingdong.api.idcard.dto;


import com.dingdong.domain.domains.idcard.domain.enums.CharacterType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class IdCardDetailsDto {
    @Schema(description = "주민증 Id")
    private Long idCardId;

    @Schema(description = "이름")
    private String nickname;

    @Schema(description = "유저 프로필 이미지")
    private String profileImageUrl;

    @Schema(description = "자기소개")
    private String aboutMe;

    @Schema(description = "키워드 정보")
    private List<KeywordDto> keywords = new ArrayList<>();

    @Schema(description = "캐릭터 타입")
    private CharacterType characterType;
}
