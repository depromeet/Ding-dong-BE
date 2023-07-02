package com.dingdong.api.community.controller.response;


import com.dingdong.api.community.dto.MyInfoInCommunityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyInfoInCommunityResponse {

    @Schema(description = "현재 본인이 속한 행성 내 정보")
    private MyInfoInCommunityDto myInfoInInCommunityDto;

    public static MyInfoInCommunityResponse from(MyInfoInCommunityDto myInfoInInCommunityDto) {
        return MyInfoInCommunityResponse.builder()
                .myInfoInInCommunityDto(myInfoInInCommunityDto)
                .build();
    }
}
