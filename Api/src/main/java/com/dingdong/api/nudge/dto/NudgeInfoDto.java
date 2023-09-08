package com.dingdong.api.nudge.dto;


import com.dingdong.api.idcard.dto.UserInfoDto;
import com.dingdong.api.notification.dto.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NudgeInfoDto {

    @Schema(description = "콕 찌르기 id")
    private Long nudgeId;

    @Schema(description = "상대 유저 정보")
    private UserInfoDto opponentUser;

    @Schema(description = "나에게 보내는 콕 찌르기 유형")
    private String toUserNudgeType;

    @Schema(description = "내가 보내는 콕 찌르기 유형 (받기만 할 경우 null)")
    private String fromUserNudgeType; // 받기만 할 경우 null
}
