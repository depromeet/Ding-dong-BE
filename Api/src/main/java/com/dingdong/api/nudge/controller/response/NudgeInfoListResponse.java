package com.dingdong.api.nudge.controller.response;


import com.dingdong.api.nudge.dto.NudgeInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NudgeInfoListResponse {

    @Schema(description = "콕찌르기 현황(정보)")
    private List<NudgeInfoDto> nudgeInfoDtos;

    public static NudgeInfoListResponse from(List<NudgeInfoDto> nudgeInfoDtos) {
        return NudgeInfoListResponse.builder().nudgeInfoDtos(nudgeInfoDtos).build();
    }
}
