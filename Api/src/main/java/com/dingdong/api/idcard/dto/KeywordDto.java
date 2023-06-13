package com.dingdong.api.idcard.dto;


import com.dingdong.domain.domains.idcard.domain.entity.Keyword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class KeywordDto {
    @Schema(description = "키워드 Id")
    private Long keywordId;

    @Schema(description = "키워드 제목")
    private String title;

    @Schema(description = "키워드 이미지")
    private String imageUrl;

    @Schema(description = "키워드 내용")
    private String content;

    public static KeywordDto of(Keyword keyword) {
        KeywordDto dto = new KeywordDto();
        dto.keywordId = keyword.getId();
        dto.title = keyword.getTitle();
        dto.imageUrl = keyword.getImagerUrl();
        dto.content = keyword.getContent();

        return dto;
    }
}
