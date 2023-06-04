package com.dingdong.api.idcard.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateKeywordDto {

    @Schema(description = "키워드 제목", example = "안산러")
    @NotNull(message = "키워드 이름을 입력해주세요")
    private String title;

    @Schema(description = "키워드 이미지", example = "test.com")
    private String imageUrl;

    @Schema(description = "키워드 내용", example = "안산에서 통학하는 불쌍한 영혼을 구제해주세요... 내일도 학교가야함")
    @NotNull(message = "키워드 설명을 적어주세요")
    private String content;
}
