package com.dingdong.domain.domains.idcard.domain.vo;


import com.dingdong.domain.domains.idcard.domain.entity.Comment;
import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import lombok.Getter;

@Getter
public class CommentVo {

    private final Comment comment;

    private final UserInfo userInfo;

    public CommentVo(Comment comment, IdCard idCard) {
        this.comment = comment;
        this.userInfo = idCard.getUserInfo();
    }
}
