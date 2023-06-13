package com.dingdong.api.community.controller;


import com.dingdong.api.community.controller.request.CreateCommunityRequest;
import com.dingdong.api.community.controller.request.UpdateCommunityRequest;
import com.dingdong.api.community.controller.response.CommunityCodeResponse;
import com.dingdong.api.community.controller.response.CommunityDetailsResponse;
import com.dingdong.api.community.controller.response.CommunityIdCardsResponse;
import com.dingdong.api.community.controller.response.CommunityListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "행성 만들기")
    @PostMapping
    public CommunityCodeResponse createCommunity(@RequestBody CreateCommunityRequest request) {
        return new CommunityCodeResponse();
    }

    @Operation(summary = "행성 꾸미기")
    @PatchMapping("/{communityId}")
    public void updateCommunity(
            @PathVariable Long communityId, @RequestBody @Valid UpdateCommunityRequest request) {}

    @Operation(summary = "행성 세부 정보 조회")
    @GetMapping("/{communityId}")
    public CommunityDetailsResponse getCommunityDetails(@PathVariable Long communityId) {
        return new CommunityDetailsResponse();
    }
}
