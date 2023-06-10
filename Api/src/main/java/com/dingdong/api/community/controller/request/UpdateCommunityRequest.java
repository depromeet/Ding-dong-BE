package com.dingdong.api.community.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateCommunityRequest {

    @Schema(description = "행성 이름", example = "미세 가스먼지 지대134")
    @NotBlank(message = "행성 이름을 입력해주세요. 공백과 중복은 허용하지 않습니다.")
    @Size(min = 1, max = 16, message = "행성 이름은 최대 16글자 입니다.")
    private String name;

    @Schema(description = "행성 프로필 이미지", example = "http://profile-image.com")
    private String profileImageUrl;

    @Schema(description = "행성 배경 이미지", example = "http://cover-image.com")
    private String coverImageUrl;

    @Schema(description = "행성 소개 한마디", example = "이 행성은 우주 세계 최강이다!!")
    @Size(max = 50, message = "행성 소개는 최대 50글자 입니다.")
    private String aboutCommunity;
}
