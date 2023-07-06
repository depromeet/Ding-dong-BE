package com.dingdong.domain.domains.idcard.domain.model;


import com.dingdong.domain.domains.idcard.domain.entity.Comment;
import lombok.Getter;

@Getter
public class CommentVo {

    private final Comment comment;

    private final UserInfo userInfo;

    public CommentVo(Comment comment, UserInfo userInfo) {
        this.comment = comment;
        this.userInfo = userInfo;
    }
}
