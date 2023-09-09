package com.dingdong.domain.domains.idcard.domain.model;


import com.dingdong.domain.domains.idcard.domain.entity.Nudge;
import lombok.Getter;

@Getter
public class NudgeVo {

    private final Nudge nudge;
    private final UserInfo userInfo;

    public NudgeVo(Nudge nudge, UserInfo userInfo) {
        this.nudge = nudge;
        this.userInfo = userInfo;
    }
}
