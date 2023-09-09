package com.dingdong.api.nudge.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NudgeRequest {

    @Schema(description = "콕 찌르기를 진행한 커뮤니티 id", example = "1")
    @NotNull(message = "커뮤니티 id를 입력해주세요.")
    private Long communityId;

    @Schema(
            description =
                    "콕 찌르기 유형 (FRIENDLY : 친해지고 싶어요 / SIMILARITY : 저와 비슷해요 / TALKING : 같이 밥 한끼 해요 / MEET : 만나서 반가워요)",
            example = "TALKING")
    @NotBlank(message = "콕 찌르기 유형을 입력해주세요.")
    private String nudgeType;
}
