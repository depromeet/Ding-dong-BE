package com.dingdong.api.notification.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class IdCardDto {
    @Schema(description = "주민증 고유값")
    private Long IdCardId;
}
