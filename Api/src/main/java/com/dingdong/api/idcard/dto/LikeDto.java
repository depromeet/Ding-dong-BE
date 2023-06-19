package com.dingdong.api.idcard.dto;


import com.dingdong.domain.domains.idcard.domain.entity.CommentLike;
import com.dingdong.domain.domains.idcard.domain.entity.CommentReplyLike;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeDto {

    @Schema(description = "해당 댓글의 좋아요 갯수", example = "1")
    private final Long likeCount;

    @Schema(description = "접속 유저가 좋아요를 눌렀는지 여부 => true면 하트 색칠", example = "true")
    private final Boolean isThisCurrentUserLike;

    public static LikeDto ofCommentLike(List<CommentLike> commentLikes, Long userId) {
        return LikeDto.builder()
                .likeCount((long) commentLikes.size())
                .isThisCurrentUserLike(
                        commentLikes.stream()
                                .anyMatch(
                                        commentLike ->
                                                Objects.equals(commentLike.getUserId(), userId)))
                .build();
    }

    public static LikeDto ofCommentReplyLike(
            List<CommentReplyLike> commentReplyLikes, Long userId) {
        return LikeDto.builder()
                .likeCount((long) commentReplyLikes.size())
                .isThisCurrentUserLike(
                        commentReplyLikes.stream()
                                .anyMatch(
                                        commentReplyLike ->
                                                Objects.equals(
                                                        commentReplyLike.getUserId(), userId)))
                .build();
    }
}
