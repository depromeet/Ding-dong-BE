package com.dingdong.api.nudge.dto;


import com.dingdong.api.notification.dto.*;
import com.dingdong.domain.domains.idcard.domain.model.NudgeVo;
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
    private UserDto opponentUser;

    @Schema(description = "나에게 보내는 콕 찌르기 유형")
    private String toUserNudgeType;

    @Schema(description = "내가 보내는 콕 찌르기 유형 (받기만 할 경우 null)")
    private String fromUserNudgeType;

    public static NudgeInfoDto of(NudgeVo vo, String fromUserNudgeType) {
        return NudgeInfoDto.builder()
                .nudgeId(vo.getNudge().getId())
                .opponentUser(
                        new UserDto(
                                vo.getUserInfo().getUserId(),
                                vo.getUserInfo().getProfileImageUrl(),
                                vo.getUserInfo().getNickname()))
                .fromUserNudgeType(vo.getNudge().getType().getValue())
                .toUserNudgeType(fromUserNudgeType)
                .build();
    }
}
