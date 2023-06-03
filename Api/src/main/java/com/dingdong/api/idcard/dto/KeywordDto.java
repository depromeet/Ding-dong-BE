package com.dingdong.api.idcard.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class KeywordDto {
    @Schema(description = "키워드 Id")
    private Long keywordId;

    @Schema(description = "키워드 제목")
    private String title;

    @Schema(description = "키워드 이미지")
    private String imageUrl;

    @Schema(description = "키워드 내용")
    private String content;
}
