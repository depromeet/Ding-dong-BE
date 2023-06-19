package com.dingdong.api.idcard.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDto {

    @Schema(description = "댓글 id", example = "1")
    private Long commentId;

    @Schema(description = "댓글 내용", example = "댓글이 제일 어렵네...")
    private String content;

    @Schema(description = "댓글 생성일")
    private Timestamp createdAt;

    @Schema(description = "댓글 좋아요 정보 dto")
    private LikeDto commentLikeInfo;

    @Schema(description = "댓글 대댓글 정보 dto list")
    private List<CommentReplyDto> commentReplyInfos;

    //    public static CommentDto from(Comment comment, Long currentUserId) {
    //        return CommentDto.builder().commentId(comment.getId()).content(comment.getContent())
    //            .commentLikeInfo(LikeDto.ofCommentLike(comment.getLikes(), currentUserId))
    //            .commentReplyInfos(comment.getReplies().stream()
    //                .map(commentReply -> CommentReplyDto.of(commentReply,
    // currentUserId)).toList())
    //            .createdAt(comment.getCreatedAt()).build();
    //    }
}
