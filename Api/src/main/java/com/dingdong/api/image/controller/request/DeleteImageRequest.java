package com.dingdong.api.image.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class DeleteImageRequest {
    @Schema(description = "삭제할 이미지 Url", example = "https://image.jpg")
    private String imageUrl;
}
