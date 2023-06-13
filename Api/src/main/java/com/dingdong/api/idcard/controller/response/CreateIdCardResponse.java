package com.dingdong.api.idcard.controller.response;


import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateIdCardResponse {

    @Schema(description = "생성된 주민증 Id")
    private final Long idCardId;

    public static CreateIdCardResponse from(IdCard idCard) {
        return CreateIdCardResponse.builder().idCardId(idCard.getId()).build();
    }
}
