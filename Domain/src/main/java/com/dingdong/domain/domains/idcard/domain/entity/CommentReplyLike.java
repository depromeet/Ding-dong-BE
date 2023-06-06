package com.dingdong.domain.domains.idcard.domain.entity;


import com.dingdong.domain.domains.AbstractTimeStamp;
import com.esotericsoftware.kryo.serializers.FieldSerializer.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_comment_reply_like")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReplyLike extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long commentReplyId;

    @NotNull private Long userId;

    private CommentReplyLike(Long commentReplyId, Long userId) {
        this.commentReplyId = commentReplyId;
        this.userId = userId;
    }

    public static CommentReplyLike toEntity(Long commentReplyId, Long userId) {
        return new CommentReplyLike(commentReplyId, userId);
    }
}
