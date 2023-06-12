package com.dingdong.api.image.controller.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadImageResponse {

    @Schema(description = "업로드 한 이미지 url")
    private String imageUrl;

    public static UploadImageResponse from(String imageUrl) {
        return new UploadImageResponse(imageUrl);
    }
}
