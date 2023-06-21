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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CommentReply> replies = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CommentLike> likes = new ArrayList<>();

    private Comment(Long idCardId, Long userId, String content) {
        this.idCardId = idCardId;
        this.userId = userId;
        this.content = content;
    }

    public static Comment toEntity(Long idCardId, Long userId, String content) {
        return new Comment(idCardId, userId, content);
    }

    public void updateReplies(CommentReply commentReply) {
        replies.add(commentReply);
    }

    public void updateLikes(CommentLike commentLike) {
        likes.add(commentLike);
    }
}
