package com.dingdong.api.idcard.controller.response;


import com.dingdong.api.idcard.dto.IdCardDetailsDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class IdCardDetailsResponse {
    @Schema(description = "주민증 세부 내용")
    private IdCardDetailsDto idCardDetailsDto;
}
