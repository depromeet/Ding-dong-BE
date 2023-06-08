package com.dingdong.api.s3.controller.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadImageResponse {
    @Schema(description = "업로드 한 이미지 url")
    private String imageUrl;
}
