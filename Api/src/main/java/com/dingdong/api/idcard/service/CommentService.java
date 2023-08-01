package com.dingdong.api.idcard.service;

import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_ID_CARD;

import com.dingdong.api.global.helper.UserHelper;
import com.dingdong.api.idcard.controller.request.CreateCommentRequest;
import com.dingdong.api.idcard.dto.CommentDto;
import com.dingdong.api.idcard.dto.CommentReplyDto;
import com.dingdong.api.notification.service.NotificationService;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.common.util.SliceUtil;
import com.dingdong.domain.domains.community.validator.CommunityValidator;
import com.dingdong.domain.domains.idcard.adaptor.CommentAdaptor;
import com.dingdong.domain.domains.idcard.adaptor.IdCardAdaptor;
import com.dingdong.domain.domains.idcard.domain.entity.Comment;
import com.dingdong.domain.domains.idcard.domain.entity.CommentLike;
import com.dingdong.domain.domains.idcard.domain.entity.CommentReply;
import com.dingdong.domain.domains.idcard.domain.entity.CommentReplyLike;
import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import com.dingdong.domain.domains.idcard.domain.model.CommentVo;
import com.dingdong.domain.domains.idcard.validator.CommentValidator;
import com.dingdong.domain.domains.idcard.validator.IdCardValidator;
import com.dingdong.domain.domains.notification.domain.enums.NotificationType;
import com.dingdong.domain.domains.notification.domain.model.NotificationContent;
import com.dingdong.domain.domains.user.domain.entity.User;
import java.util.List;
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

    private final NotificationService notificationService;

    private final CommunityValidator communityValidator;

    /** 댓글 생성 */
    @Transactional
    public Long createComment(Long idCardId, CreateCommentRequest request) {
        User currentUser = userHelper.getCurrentUser();
        IdCard targetIdCard = validateAndGetIdCard(idCardId, currentUser.getId());
        Comment comment =
                Comment.toEntity(targetIdCard.getId(), currentUser.getId(), request.getContents());
        commentAdaptor.save(comment);

        notificationService.createAndPublishNotification(
                getNotificationTargetUserId(targetIdCard),
                getCurrentUserIdCard(targetIdCard.getCommunityId(), currentUser.getId()).getId(),
                NotificationType.ID_CARD_COMMENT,
                NotificationContent.create(
                        targetIdCard.getCommunityId(), idCardId, comment.getId()));

        return comment.getId();
    }

    /** 대댓글 생성 */
    @Transactional
    public Long createCommentReply(Long idCardId, Long commentId, CreateCommentRequest request) {
        User currentUser = userHelper.getCurrentUser();
        IdCard targetIdCard = validateAndGetIdCard(idCardId, currentUser.getId());

        Comment comment = validateAndGetComment(targetIdCard, commentId);

        Comment commentReply =
                Comment.toEntity(targetIdCard.getId(), currentUser.getId(), request.getContents());

        commentReply.updateParentCommentId(comment.getId());

        commentAdaptor.save(commentReply);

        notificationService.createAndPublishNotification(
                getNotificationTargetUserId(comment),
                getCurrentUserIdCard(targetIdCard.getCommunityId(), currentUser.getId()).getId(),
                NotificationType.COMMENT_REPLY,
                NotificationContent.create(
                        targetIdCard.getCommunityId(), idCardId, commentReply.getId()));

        return commentReply.getId();
    }

    /** 댓글 조회 */
    public Slice<CommentDto> getComments(Long idCardId, Pageable pageable) {
        User currentUser = userHelper.getCurrentUser();
        IdCard idCard = idCardAdaptor.findById(idCardId);
        communityValidator.validateUserExistInCommunity(currentUser, idCard.getCommunityId());
        Slice<CommentVo> comments =
                commentAdaptor.findCommentsByIdCard(
                        idCard.getId(), idCard.getCommunityId(), pageable);

        return SliceUtil.createSliceWithPageable(
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

    /** 대댓글 조회 */
    public List<CommentReplyDto> getReplies(Long idCardId, Long commentId) {
        User currentUser = userHelper.getCurrentUser();
        IdCard idCard = idCardAdaptor.findById(idCardId);

        Long communityId = idCard.getCommunityId();

        communityValidator.validateUserExistInCommunity(currentUser, idCard.getCommunityId());

        Comment comment = commentAdaptor.findById(commentId);

        idCardValidator.isValidIdCardComment(idCard, comment);

        List<CommentVo> replies =
                commentAdaptor.findCommentsByParentCommentId(communityId, commentId);

        return replies.stream()
                .map(
                        reply ->
                                CommentReplyDto.of(
                                        reply.getComment(),
                                        reply.getUserInfo(),
                                        currentUser.getId()))
                .toList();
    }

    /** 댓글 좋아요 생성 */
    @Transactional
    public void createCommentLike(Long idCardId, Long commentId) {
        User currentUser = userHelper.getCurrentUser();
        IdCard targetIdCard = validateAndGetIdCard(idCardId, currentUser.getId());

        Comment comment = validateAndGetComment(targetIdCard, commentId);

        commentValidator.isExistCommentLike(comment, currentUser.getId());

        notificationService.createAndPublishNotification(
                getNotificationTargetUserId(comment),
                getCurrentUserIdCard(targetIdCard.getCommunityId(), currentUser.getId()).getId(),
                NotificationType.COMMENT_LIKE,
                NotificationContent.create(
                        targetIdCard.getCommunityId(), idCardId, comment.getId()));

        comment.addLike(CommentLike.toEntity(comment.getId(), currentUser.getId()));
    }

    /** 대댓글 좋아요 생성 */
    @Transactional
    public void createCommentReplyLike(Long idCardId, Long commentId, Long commentReplyId) {
        User currentUser = userHelper.getCurrentUser();
        IdCard targetIdCard = validateAndGetIdCard(idCardId, currentUser.getId());

        Comment comment = validateAndGetComment(targetIdCard, commentId);
        CommentReply commentReply = validateAndGetCommentReply(comment, commentReplyId);

        commentValidator.isExistCommentReplyLike(commentReply, currentUser.getId());

        notificationService.createAndPublishNotification(
                getNotificationTargetUserId(commentReply),
                getCurrentUserIdCard(targetIdCard.getCommunityId(), currentUser.getId()).getId(),
                NotificationType.COMMENT_REPLY_LIKE,
                NotificationContent.create(
                        targetIdCard.getCommunityId(), idCardId, commentReply.getId()));

        commentReply.updateReplyLikes(
                CommentReplyLike.toEntity(commentReplyId, currentUser.getId()));
    }

    /** 댓글 삭제 */
    @Transactional
    public void deleteComment(Long idCardId, Long commentId) {
        User currentUser = userHelper.getCurrentUser();
        IdCard idCard = idCardAdaptor.findById(idCardId);
        Comment comment = validateAndGetComment(idCard, commentId);

        commentValidator.isValidCommentUser(comment, currentUser.getId());

        comment.delete();
    }

    /** 대댓글 삭제 */
    @Transactional
    public void deleteCommentReply(Long idCardId, Long commentId, Long commentReplyId) {
        User currentUser = userHelper.getCurrentUser();
        IdCard idCard = idCardAdaptor.findById(idCardId);
        Comment comment = validateAndGetComment(idCard, commentId);
        CommentReply commentReply = validateAndGetCommentReply(comment, commentReplyId);

        commentValidator.isValidCommentReplyUser(commentReply, currentUser.getId());

        comment.deleteReply(commentReply);
    }

    /** 댓글 좋아요 취소 */
    @Transactional
    public void deleteCommentLike(Long idCardId, Long commentId) {
        User currentUser = userHelper.getCurrentUser();
        IdCard idCard = idCardAdaptor.findById(idCardId);
        Comment comment = validateAndGetComment(idCard, commentId);
        CommentLike commentLike =
                commentAdaptor.findCommentLikeByUserId(comment, currentUser.getId());

        comment.deleteLike(commentLike);
    }

    /** 대댓글 좋아요 취소 */
    @Transactional
    public void deleteCommentReplyLike(Long idCardId, Long commentId, Long commentReplyId) {
        User currentUser = userHelper.getCurrentUser();
        IdCard idCard = idCardAdaptor.findById(idCardId);
        Comment comment = validateAndGetComment(idCard, commentId);
        CommentReply commentReply = validateAndGetCommentReply(comment, commentReplyId);
        CommentReplyLike commentReplyLike =
                commentAdaptor.findCommentReplyLike(commentReply, currentUser.getId());

        commentReply.deleteLike(commentReplyLike);
    }

    /** comment 가져오는 공통 메서드 */
    private Comment validateAndGetComment(IdCard idCard, Long commentId) {
        Comment comment = commentAdaptor.findById(commentId);
        idCardValidator.isValidIdCardComment(idCard, comment);
        return comment;
    }

    private Long getNotificationTargetUserId(IdCard idCard) {
        return idCard.getUserInfo().getUserId();
    }

    private Long getNotificationTargetUserId(Comment comment) {
        return comment.getUserId();
    }

    private Long getNotificationTargetUserId(CommentReply commentReply) {
        return commentReply.getUserId();
    }

    private CommentReply validateAndGetCommentReply(Comment comment, Long commentReplyId) {
        CommentReply commentReply = commentAdaptor.findCommentReply(comment, commentReplyId);
        commentValidator.isValidCommentReply(commentReply, comment.getId());
        return commentReply;
    }

    private IdCard validateAndGetIdCard(Long idCardId, Long userId) {
        IdCard targetIdCard = idCardAdaptor.findById(idCardId);
        idCardValidator.validateUserIdCardInCommunity(targetIdCard.getCommunityId(), userId);
        return targetIdCard;
    }

    private IdCard getCurrentUserIdCard(Long communityId, Long userId) {
        return idCardAdaptor
                .findByUserAndCommunity(communityId, userId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_ID_CARD));
    }
}
