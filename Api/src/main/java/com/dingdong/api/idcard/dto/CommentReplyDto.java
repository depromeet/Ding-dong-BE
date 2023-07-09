package com.dingdong.api.idcard.dto;


import com.dingdong.domain.domains.idcard.domain.entity.CommentReply;
import com.dingdong.domain.domains.idcard.domain.model.UserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentReplyDto {

    @Schema(description = "대댓글 id", example = "1")
    private final Long commentReplyId;

    @Schema(description = "대댓글 내용", example = "대댓글 귀찮다")
    private final String content;

    @Schema(description = "대댓글 생성일")
    private final Timestamp createdAt;

    @Schema(description = "댓글 작성자 정보")
    private final UserInfoDto writerInfo;

    @Schema(description = "대댓글 좋아요 정보 dto")
    private final LikeDto commentReplyLikeInfo;

    public static CommentReplyDto of(
            CommentReply commentReply, UserInfo userInfo, Long currentUserId) {
        return CommentReplyDto.builder()
                .commentReplyId(commentReply.getId())
                .content(commentReply.getContent())
                .createdAt(commentReply.getCreatedAt())
                .writerInfo(UserInfoDto.from(userInfo))
                .commentReplyLikeInfo(
                        LikeDto.ofCommentReplyLike(commentReply.getReplyLikes(), currentUserId))
                .build();
    }
}
