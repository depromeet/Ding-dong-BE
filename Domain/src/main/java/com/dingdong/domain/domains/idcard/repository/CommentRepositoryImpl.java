package com.dingdong.domain.domains.idcard.repository;

import static com.dingdong.domain.common.consts.Status.N;
import static com.dingdong.domain.domains.idcard.domain.entity.QComment.comment;
import static com.dingdong.domain.domains.idcard.domain.entity.QIdCard.idCard;

import com.dingdong.domain.common.util.SliceUtil;
import com.dingdong.domain.domains.idcard.domain.model.CommentVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<CommentVo> findCommentsByIdCardId(
            Long idCardId, Long communityId, Pageable pageable) {
        List<CommentVo> comments =
                queryFactory
                        .select(Projections.constructor(CommentVo.class, comment, idCard.userInfo))
                        .from(comment)
                        .join(idCard)
                        .on(
                                idCard.userInfo.userId.eq(comment.userId),
                                idCard.communityId.eq(communityId))
                        .where(
                                comment.idCardId.eq(idCardId),
                                comment.isDeleted.eq(N),
                                comment.parentCommentId.isNull())
                        .orderBy(comment.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        return SliceUtil.createSliceWithoutPageable(comments);
    }

    @Override
    public List<CommentVo> findCommentsByParentCommentId(Long communityId, Long parentCommentId) {
        return queryFactory
                .select(Projections.constructor(CommentVo.class, comment, idCard.userInfo))
                .from(comment)
                .join(idCard)
                .on(idCard.userInfo.userId.eq(comment.userId), idCard.communityId.eq(communityId))
                .where(comment.isDeleted.eq(N), comment.parentCommentId.eq(parentCommentId))
                .orderBy(comment.id.asc())
                .fetch();
    }
}
