package com.dingdong.api.idcard.dto;


import com.dingdong.domain.domains.idcard.domain.entity.Comment;
import com.dingdong.domain.domains.idcard.domain.vo.UserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDto {

    @Schema(description = "댓글 id", example = "1")
    private final Long commentId;

    @Schema(description = "댓글 내용", example = "댓글이 제일 어렵네...")
    private final String content;

    @Schema(description = "댓글 생성일")
    private final Timestamp createdAt;

    @Schema(description = "댓글 작성자 정보")
    private final UserInfoDto writerInfo;

    @Schema(description = "댓글 좋아요 정보 dto")
    private final LikeDto commentLikeInfo;

    @Schema(description = "댓글 대댓글 정보 dto list")
    private final List<CommentReplyDto> commentReplyInfos;

    public static CommentDto of(Comment comment, UserInfo userInfo, Long currentUserId) {
        return CommentDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .writerInfo(UserInfoDto.from(userInfo))
                .commentLikeInfo(LikeDto.ofCommentLike(comment.getLikes(), currentUserId))
                .commentReplyInfos(
                        comment.getReplies().stream()
                                .map(
                                        commentReply ->
                                                CommentReplyDto.of(commentReply, currentUserId))
                                .toList())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
