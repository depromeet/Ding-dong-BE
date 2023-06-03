package com.dingdong.api.community.controller.response;


import com.dingdong.api.community.dto.CommunityIdCardsDto;
import com.dingdong.api.global.response.SliceResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
public class CommunityIdCardsResponse {
    @Schema(description = "주민증 목록")
    private SliceResponse<CommunityIdCardsDto> communityIdCardsDtos;

    public static SliceResponse<CommunityIdCardsDto> from(
            Slice<CommunityIdCardsDto> communityIdCardsDtos) {
        return SliceResponse.from(communityIdCardsDtos);
    }
}
