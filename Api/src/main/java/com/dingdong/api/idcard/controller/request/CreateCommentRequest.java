package com.dingdong.api.idcard.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentRequest {

    @Schema(description = "작성 내용", example = "졸리다")
    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private String contents;
}
