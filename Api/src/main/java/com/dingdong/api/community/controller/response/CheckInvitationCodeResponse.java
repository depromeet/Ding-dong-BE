package com.dingdong.api.community.controller.response;


import com.dingdong.api.community.dto.CheckInvitationCodeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckInvitationCodeResponse {

    @Schema(description = "행성 초대 코드 검사 성공 시 조회된 행성 정보")
    private CheckInvitationCodeDto checkInvitationCodeDto;

    public static CheckInvitationCodeResponse from(CheckInvitationCodeDto dto) {
        return CheckInvitationCodeResponse.builder().checkInvitationCodeDto(dto).build();
    }
}
