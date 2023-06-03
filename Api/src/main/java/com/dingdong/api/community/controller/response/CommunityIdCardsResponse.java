package com.dingdong.api.community.controller.response;


import com.dingdong.api.community.dto.CommunityIdCardsDto;
import com.dingdong.api.global.dto.PagesDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommunityIdCardsResponse {
    @Schema(description = "주민증 목록")
    private List<CommunityIdCardsDto> communityIdCardsDtos;

    @Schema(description = "페이징 정보")
    private PagesDto pages;
}
