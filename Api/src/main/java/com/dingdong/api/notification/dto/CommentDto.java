package com.dingdong.api.notification.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDto {
    @Schema(description = "댓글 고유값")
    private Long commentId;

    @Schema(description = "댓글 or 답글")
    private String comment;
}
