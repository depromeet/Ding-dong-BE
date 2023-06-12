package com.dingdong.api.community.controller;


import com.dingdong.api.community.controller.response.CommunityDetailsResponse;
import com.dingdong.api.community.controller.response.CommunityIdCardsResponse;
import com.dingdong.api.community.controller.response.CommunityListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "커뮤니티")
@RestController
@RequestMapping("/communities")
public class CommunityController {

    @Operation(summary = "유저가 소속되어 있는 모든 행성 목록 조회")
    @GetMapping("/users/{userId}")
    public CommunityListResponse getUserCommunityList(@PathVariable Long userId) {
        return new CommunityListResponse();
    }

    @Operation(summary = "행성의 모든 주민증 목록 조회")
    @GetMapping("/{communityId}/idCards")
    public CommunityIdCardsResponse getCommunityIdCards(@PathVariable Long communityId) {

        return new CommunityIdCardsResponse();
    }

    @Operation(summary = "행성 세부 정보 조회")
    @GetMapping("/{communityId}")
    public CommunityDetailsResponse getCommunityDetails(@PathVariable Long communityId) {
        return new CommunityDetailsResponse();
    }
}
