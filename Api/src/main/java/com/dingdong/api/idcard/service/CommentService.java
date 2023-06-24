package com.dingdong.api.idcard.service;


import com.dingdong.api.global.helper.UserHelper;
import com.dingdong.api.idcard.controller.request.CreateCommentRequest;
import com.dingdong.api.idcard.dto.CommentDto;
import com.dingdong.domain.common.util.SliceUtil;
import com.dingdong.domain.domains.idcard.adaptor.CommentAdaptor;
import com.dingdong.domain.domains.idcard.adaptor.IdCardAdaptor;
import com.dingdong.domain.domains.idcard.domain.entity.Comment;
import com.dingdong.domain.domains.idcard.domain.entity.CommentLike;
import com.dingdong.domain.domains.idcard.domain.entity.CommentReply;
import com.dingdong.domain.domains.idcard.domain.entity.CommentReplyLike;
import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import com.dingdong.domain.domains.idcard.domain.vo.CommentVo;
import com.dingdong.domain.domains.idcard.validator.CommentValidator;
import com.dingdong.domain.domains.idcard.validator.IdCardValidator;
import com.dingdong.domain.domains.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentAdaptor commentAdaptor;

    private final UserHelper userHelper;

    private final IdCardAdaptor idCardAdaptor;

    private final IdCardValidator idCardValidator;

    private final CommentValidator commentValidator;

    /** 댓글 생성 */
    @Transactional
    public Long createComment(Long idCardId, CreateCommentRequest request) {
        User currentUser = userHelper.getCurrentUser();

        IdCard idCard = idCardAdaptor.findById(idCardId);

        Comment comment =
                Comment.toEntity(idCard.getId(), currentUser.getId(), request.getContents());

        return commentAdaptor.save(comment).getId();
    }

    /** 대댓글 생성 */
    @Transactional
    public void createCommentReply(Long idCardId, Long commentId, CreateCommentRequest request) {
        User currentUser = userHelper.getCurrentUser();

        Comment comment = getComment(idCardId, commentId);

        CommentReply commentReply =
                CommentReply.toEntity(
                        idCardId, comment.getId(), currentUser.getId(), request.getContents());

        comment.updateReplies(commentReply);
    }

    /** 댓글 조회 */
    public Slice<CommentDto> getComments(Long idCardId, Pageable pageable) {
        User currentUser = userHelper.getCurrentUser();

        IdCard idCard = idCardAdaptor.findById(idCardId);

        Slice<CommentVo> comments = commentAdaptor.findCommentsByIdCard(idCard.getId(), pageable);

        return SliceUtil.valueOf(
                comments.stream()
                        .map(
                                commentVo ->
                                        CommentDto.of(
                                                commentVo.getComment(),
                                                commentVo.getUserInfo(),
                                                currentUser.getId()))
                        .toList(),
                pageable);
    }

    /** 댓글 좋아요 생성 */
    @Transactional
    public void createCommentLike(Long idCardId, Long commentId) {
        User currentUser = userHelper.getCurrentUser();

        Comment comment = getComment(idCardId, commentId);

        commentValidator.isExistCommentLike(comment, currentUser.getId());

        comment.updateLikes(CommentLike.toEntity(comment.getId(), currentUser.getId()));
    }

    /** 대댓글 좋아요 생성 */
    @Transactional
    public void createCommentReplyLike(Long idCardId, Long commentId, Long commentReplyId) {
        User currentUser = userHelper.getCurrentUser();

        CommentReply commentReply = getCommentReply(idCardId, commentId, commentReplyId);

        commentValidator.isExistCommentReplyLike(commentReply, currentUser.getId());

        commentReply.updateReplyLikes(
                CommentReplyLike.toEntity(commentReplyId, currentUser.getId()));
    }

    /** 댓글 삭제 */
    @Transactional
    public void deleteComment(Long idCardId, Long commentId) {
        User currentUser = userHelper.getCurrentUser();

        Comment comment = getComment(idCardId, commentId);

        commentValidator.isValidCommentUser(comment, currentUser.getId());

        comment.delete();
    }

    /** 대댓글 삭제 */
    @Transactional
    public void deleteCommentReply(Long idCardId, Long commentId, Long commentReplyId) {
        User currentUser = userHelper.getCurrentUser();

        Comment comment = getComment(idCardId, commentId);

        CommentReply commentReply = commentAdaptor.findCommentReply(comment, commentReplyId);

        commentValidator.isValidCommentReplyUser(commentReply, currentUser.getId());

        comment.deleteReply(commentReply);
    }

    /** 댓글 좋아요 취소 */
    @Transactional
    public void deleteCommentLike(Long idCardId, Long commentId, Long commentLikeId) {
        User currentUser = userHelper.getCurrentUser();

        Comment comment = getComment(idCardId, commentId);

        CommentLike commentLike = commentAdaptor.findCommentLike(comment, commentLikeId);

        commentValidator.isValidCommentLikeUser(commentLike, currentUser.getId());

        comment.deleteLike(commentLike);
    }

    /** 대댓글 좋아요 취소 */
    @Transactional
    public void deleteCommentReplyLike(
            Long idCardId, Long commentId, Long commentReplyId, Long commentReplyLikeId) {
        User currentUser = userHelper.getCurrentUser();

        CommentReply commentReply = getCommentReply(idCardId, commentId, commentReplyId);

        CommentReplyLike commentReplyLike =
                commentAdaptor.findCommentReplyLike(commentReply, commentReplyLikeId);

        commentValidator.isValidCommentReplyLikeUser(commentReplyLike, currentUser.getId());

        commentReply.deleteLike(commentReplyLike);
    }

    /** comment 가져오는 공통 메서드 */
    private Comment getComment(Long idCardId, Long commentId) {
        IdCard idCard = idCardAdaptor.findById(idCardId);

        Comment comment = commentAdaptor.findById(commentId);

        idCardValidator.isValidIdCardComment(idCard, comment);

        return comment;
    }

    /** commentReply 가져오는 공통 메서드 */
    private CommentReply getCommentReply(Long idCardId, Long commentId, Long commentReplyId) {
        Comment comment = getComment(idCardId, commentId);

        CommentReply commentReply = commentAdaptor.findCommentReply(comment, commentReplyId);

        commentValidator.isValidCommentReply(commentReply, comment.getId());

        return commentReply;
    }
}
