package com.dingdong.api.community.controller.response;


import com.dingdong.api.community.dto.CommunityListDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class CommunityListResponse {
    @Schema(description = "내가 속한 커뮤니티 목록")
    List<CommunityListDto> communityListDtos = new ArrayList<>();
}
