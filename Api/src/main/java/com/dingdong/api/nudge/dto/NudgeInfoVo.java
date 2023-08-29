package com.dingdong.api.nudge.dto;


import com.dingdong.api.idcard.dto.UserInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NudgeInfoVo {

    @Schema(description = "콕 찌르기 id")
    private Long nudgeId;

    @Schema(description = "상대 유저 정보")
    private UserInfoDto opponentUser;

    @Schema(description = "나에게 보내는 콕 찌르기 유형")
    private String toUserNudgeType;

    @Schema(description = "내가 보내는 콕 찌르기 유형")
    private String fromUserNudgeType;

    //    @QueryProjection
    //    public NudgeInfoVo(Long nudgeId, UserInfoDto toUser, String toUserNudgeType, String
    // fromUserNudgeType) {
    //        this.nudgeId = nudgeId;
    //        this.opponentUser = toUser;
    //        this.toUserNudgeType = toUserNudgeType;
    //        this.fromUserNudgeType = fromUserNudgeType;
    //    }
}
