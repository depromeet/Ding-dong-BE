package com.dingdong.api.idcard.controller.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentCountResponse {
    @Schema(description = "댓글 갯수")
    private int count;

    public static CommentCountResponse from(int count) {
        return CommentCountResponse.builder().count(count).build();
    }
}
