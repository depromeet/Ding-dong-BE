package com.dingdong.domain.domains.idcard.domain.entity;

import static com.dingdong.domain.common.consts.Status.N;
import static com.dingdong.domain.common.consts.Status.Y;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_COMMENT_LIKE;

import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.common.consts.Status;
import com.dingdong.domain.domains.AbstractTimeStamp;
import com.esotericsoftware.kryo.serializers.FieldSerializer.NotNull;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_comment")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long idCardId;

    @NotNull private Long userId;

    @NotNull private String content;

    private Long parentCommentId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status isDeleted;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CommentLike> likes = new ArrayList<>();

    private Comment(Long idCardId, Long userId, String content) {
        this.idCardId = idCardId;
        this.userId = userId;
        this.content = content;
        this.isDeleted = N;
    }

    public static Comment toEntity(Long idCardId, Long userId, String content) {
        return new Comment(idCardId, userId, content);
    }

    public void addLike(CommentLike commentLike) {
        this.likes.add(commentLike);
    }

    public void deleteLike(CommentLike commentLike) {
        if (!this.likes.remove(commentLike)) {
            throw new BaseException(NOT_FOUND_COMMENT_LIKE);
        }
    }

    public void delete() {
        this.likes.clear();
        this.isDeleted = Y;
    }

    public void updateParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}
