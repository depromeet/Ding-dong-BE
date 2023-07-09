package com.dingdong.api.idcard.controller.response;


import com.dingdong.api.idcard.dto.CommentReplyDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentRepliesResponse {

    @Schema(description = "대댓글이 달린 댓글의 id")
    private final Long commentId;

    @Schema(description = "대댓글 정보")
    private final List<CommentReplyDto> repliesInfo;

    public static CommentRepliesResponse of(Long commentId, List<CommentReplyDto> repliesInfo) {
        return CommentRepliesResponse.builder()
                .commentId(commentId)
                .repliesInfo(repliesInfo)
                .build();
    }
}
