package com.dingdong.api.nudge.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NudgeRequest {

    @Schema(description = "콕 찌르기 유형", example = "TALKING")
    @NotBlank(message = "콕 찌르기 유형을 입력해주세요.")
    private String nudgeType;
}
