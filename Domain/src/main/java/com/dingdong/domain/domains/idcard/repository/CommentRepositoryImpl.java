package com.dingdong.domain.domains.idcard.repository;

import static com.dingdong.domain.common.consts.Status.N;
import static com.dingdong.domain.domains.idcard.domain.entity.QComment.comment;
import static com.dingdong.domain.domains.idcard.domain.entity.QCommentLike.commentLike;
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
    public Slice<CommentVo> findCommentsByIdCardId(Long idCardId, Pageable pageable) {
        List<CommentVo> comments =
                queryFactory
                        .select(Projections.constructor(CommentVo.class, comment, idCard))
                        .from(comment)
                        .leftJoin(comment.likes, commentLike)
                        .fetchJoin()
                        .join(idCard)
                        .on(idCard.id.eq(comment.idCardId))
                        .where(comment.idCardId.eq(idCardId), comment.isDeleted.eq(N))
                        .orderBy(comment.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        return SliceUtil.valueOf(comments, pageable);
    }
}
