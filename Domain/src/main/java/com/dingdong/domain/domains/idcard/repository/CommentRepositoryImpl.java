package com.dingdong.domain.domains.idcard.repository;

import static com.dingdong.domain.domains.idcard.domain.entity.QComment.comment;
import static com.dingdong.domain.domains.idcard.domain.entity.QCommentLike.commentLike;
import static com.dingdong.domain.domains.idcard.domain.entity.QCommentReply.commentReply;
import static com.dingdong.domain.domains.idcard.domain.entity.QIdCard.idCard;

import com.dingdong.domain.common.util.SliceUtil;
import com.dingdong.domain.domains.idcard.domain.entity.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Comment> findCommentsByIdCardId(Long idCardId, Pageable pageable) {
        List<Comment> comments =
                queryFactory
                        .selectFrom(comment)
                        .leftJoin(idCard)
                        .on(idCard.id.eq(comment.idCardId))
                        .leftJoin(comment.replies, commentReply)
                        .fetchJoin()
                        .leftJoin(comment.likes, commentLike)
                        .fetchJoin()
                        .where(comment.idCardId.eq(idCardId))
                        .orderBy(comment.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        return SliceUtil.valueOf(comments, pageable);
    }
}
