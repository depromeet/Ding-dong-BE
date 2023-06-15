package com.dingdong.api.idcard.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateIdCardRequest {

    @Schema(description = "행성 id", example = "1")
    @NotNull(message = "행성 식별 값을 입력해주세요.")
    private Long communityId;

    @Schema(description = "유저 프로필 이미지 url. null 값이면 기본 이미지", example = "test.com")
    private String profileImageUrl;

    @Schema(description = "행성에서 사용할 유저 닉네임", example = "김민준")
    @NotBlank(message = "행성에서 사용할 유저 닉네임을 입력해주세요. 공백은 허용하지 않습니다.")
    private String nickname;

    @Schema(description = "유저 간단 자기소개", example = "안녕하세요. 김민준입니다.")
    @NotNull(message = "유저 자기소개를 입력해주세요.")
    @Size(max = 500, message = "자기소개는 500글자 제한입니다.")
    private String aboutMe;
}
