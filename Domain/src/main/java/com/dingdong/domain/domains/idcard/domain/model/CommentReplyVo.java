package com.dingdong.domain.domains.idcard.domain.model;


import com.dingdong.domain.domains.idcard.domain.entity.CommentReply;
import lombok.Getter;

@Getter
public class CommentReplyVo {

    private final CommentReply commentReply;

    private final UserInfo userInfo;

    public CommentReplyVo(CommentReply commentReply, UserInfo userInfo) {
        this.commentReply = commentReply;
        this.userInfo = userInfo;
    }
}
