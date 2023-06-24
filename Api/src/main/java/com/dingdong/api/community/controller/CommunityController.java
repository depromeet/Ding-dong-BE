package com.dingdong.api.community.controller;


import com.dingdong.api.community.controller.request.CreateCommunityNameRequest;
import com.dingdong.api.community.controller.request.CreateCommunityRequest;
import com.dingdong.api.community.controller.request.UpdateCommunityRequest;
import com.dingdong.api.community.controller.response.CommunityDetailsResponse;
import com.dingdong.api.community.controller.response.CommunityListResponse;
import com.dingdong.api.community.service.CommunityService;
import com.dingdong.api.global.response.IdResponse;
import com.dingdong.api.global.response.SliceResponse;
import com.dingdong.api.idcard.controller.response.IdCardDetailsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Tag(name = "커뮤니티")
@RestController
@RequestMapping("/communities")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @Operation(summary = "유저가 소속되어 있는 모든 행성 목록 조회")
    @GetMapping("/users/{userId}")
    public CommunityListResponse getUserCommunityList(@PathVariable Long userId) {
        return CommunityListResponse.from(communityService.getUserCommunityList(userId));
    }

    @Operation(summary = "행성의 모든 주민증 목록 조회")
    @GetMapping("/{communityId}/idCards")
    public SliceResponse getCommunityIdCards(
            @PathVariable Long communityId, @PageableDefault Pageable pageable) {
        return SliceResponse.from(communityService.getCommunityIdCards(communityId, pageable));
    }

    @Operation(summary = "행성 만들기")
    @PostMapping
    public IdResponse createCommunity(@RequestBody @Valid CreateCommunityRequest request) {
        return IdResponse.from(communityService.createCommunity(request));
    }

    @Operation(summary = "행성 꾸미기")
    @PutMapping("/{communityId}")
    public IdResponse updateCommunity(
            @PathVariable Long communityId, @RequestBody @Valid UpdateCommunityRequest request) {

        return IdResponse.from(communityService.updateCommunity(communityId, request));
    }

    @Operation(summary = "행성 세부 정보 조회")
    @GetMapping("/{communityId}")
    public CommunityDetailsResponse getCommunityDetails(@PathVariable Long communityId) {
        return CommunityDetailsResponse.from(communityService.getCommunityDetails(communityId));
    }

    @Operation(summary = "행성의 유저 주민증 조회 (유저 정보는 토큰으로)")
    @GetMapping("/{communityId}/users/idCards")
    public IdCardDetailsResponse getUserIdCardDetails(@PathVariable Long communityId) {
        return IdCardDetailsResponse.from(communityService.getUserIdCardDetails(communityId));
    }

    @Operation(summary = "행성 이름 중복 체크")
    @PostMapping("/check")
    public void checkDuplicatedName(@RequestBody @Valid CreateCommunityNameRequest request) {
        communityService.checkDuplicatedName(request);
    }
}
