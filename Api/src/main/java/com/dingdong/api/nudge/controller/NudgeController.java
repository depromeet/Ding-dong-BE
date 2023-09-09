package com.dingdong.api.nudge.controller;


import com.dingdong.api.nudge.controller.request.NudgeRequest;
import com.dingdong.api.nudge.controller.response.NudgeDetailResponse;
import com.dingdong.api.nudge.controller.response.NudgeInfoListResponse;
import com.dingdong.api.nudge.service.NudgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "access-token")
@Tag(name = "콕 찌르기")
@RestController
@RequiredArgsConstructor
@RequestMapping("/nudges")
public class NudgeController {

    private final NudgeService nudgeService;

    @Operation(summary = "상대 유저를 콕 찌릅니다.")
    @PostMapping("/users/{userId}")
    public void postNudge(@PathVariable Long userId, @RequestBody @Valid NudgeRequest body) {
        nudgeService.createNudge(userId, body);
    }

    @Operation(summary = "상대 유저를 콕 찌르기 종류를 변경합니다.")
    @PutMapping("/users/{userId}/{nudgeId}")
    public void updateNudge(
            @PathVariable Long userId,
            @PathVariable Long nudgeId,
            @RequestBody @Valid NudgeRequest body) {
        nudgeService.updateNudge(userId, nudgeId, body);
    }

    @Operation(summary = "상대 유저가 나에게 콕 찌르기 한 정보를 확인합니다.")
    @GetMapping("/users/{userId}/{communityId}")
    public NudgeDetailResponse getNudgeDetail(
            @PathVariable Long userId, @PathVariable Long communityId) {
        return NudgeDetailResponse.from(nudgeService.getNudgeDetail(userId, communityId));
    }

    @Operation(summary = "나의 콕 찌르기 현황을 조회합니다.")
    @GetMapping
    public NudgeInfoListResponse getNudges(@ParameterObject Pageable pageable) {
        return NudgeInfoListResponse.from(nudgeService.getNudges(pageable));
    }
}
