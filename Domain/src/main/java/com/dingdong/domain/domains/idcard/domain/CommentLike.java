package com.dingdong.domain.domains.idcard.domain;


import com.dingdong.domain.domains.AbstractTimeStamp;
import com.esotericsoftware.kryo.serializers.FieldSerializer.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_comment_like")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long commentId;

    @NotNull private Long userId;

    private CommentLike(Long commentId, Long userId) {
        this.commentId = commentId;
        this.userId = userId;
    }

    public static CommentLike toEntity(Long commentId, Long userId) {
        return new CommentLike(commentId, userId);
    }
}
