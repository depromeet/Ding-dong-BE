package com.dingdong.api.community.controller.response;


import com.dingdong.api.community.dto.ValidateInvitationCodeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValidateInvitationCodeResponse {

    @Schema(description = "행성 초대 코드 검사 성공 시 조회된 행성 정보")
    private ValidateInvitationCodeDto validateInvitationCodeDto;

    public static ValidateInvitationCodeResponse from(ValidateInvitationCodeDto dto) {
        return ValidateInvitationCodeResponse.builder().validateInvitationCodeDto(dto).build();
    }
}
