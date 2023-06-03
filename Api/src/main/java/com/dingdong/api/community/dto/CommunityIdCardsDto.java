package com.dingdong.api.community.dto;


import com.dingdong.domain.domains.idcard.domain.enums.CharacterType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityIdCardsDto {

    @Schema(description = "주민증 고유 값")
    private Long idCardId;

    @Schema(description = "이름")
    private String nickname;

    @Schema(description = "자기소개")
    private String aboutMe;

    @Schema(description = "캐릭터 타입")
    private CharacterType characterType;

    @Schema(description = "키워드 타이틀 목록")
    private final List<String> keywordTitles = new ArrayList<>();
}
