package com.dingdong.domain.domains.idcard.domain.entity;


import com.dingdong.domain.domains.AbstractTimeStamp;
import com.esotericsoftware.kryo.serializers.FieldSerializer.NotNull;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_comment_reply")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReply extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long idCardId;

    @NotNull private Long commentId;

    @NotNull private Long userId;

    @NotNull private String content;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CommentReplyLike> replyLikes = new ArrayList<>();

    private CommentReply(Long idCardId, Long commentId, Long userId, String content) {
        this.idCardId = idCardId;
        this.commentId = commentId;
        this.userId = userId;
        this.content = content;
    }

    public static CommentReply toEntity(
            Long idCardId, Long commentId, Long userId, String content) {
        return new CommentReply(idCardId, commentId, userId, content);
    }
}
