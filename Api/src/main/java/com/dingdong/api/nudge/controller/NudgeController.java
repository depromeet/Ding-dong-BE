package com.dingdong.api.nudge.controller;


import com.dingdong.api.nudge.controller.request.NudgeRequest;
import com.dingdong.api.nudge.controller.response.NudgeDetailResponse;
import com.dingdong.api.nudge.dto.NudgeInfoVo;
import com.dingdong.api.nudge.service.NudgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.awt.print.Pageable;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "access-token")
@Tag(name = "콕 찌르기")
@RestController
@RequiredArgsConstructor
@RequestMapping("/nudges")
public class NudgeController {

    private final NudgeService nudgeService;

    @Operation(summary = "상대 유저를 콕 찌릅니다.")
    @PostMapping("/users/{userId}")
    public void postNudge(@PathVariable Long userId, @RequestBody @Valid NudgeRequest body) {}

    @Operation(summary = "상대 유저가 나에게 콕 찌르기 한 정보를 확인합니다.")
    @GetMapping("/users/{userId}")
    public NudgeDetailResponse getNudgeDetail(@PathVariable Long userId) {
        return new NudgeDetailResponse();
    }

    @Operation(summary = "나의 콕 찌르기 현황을 조회합니다.")
    @GetMapping
    public NudgeInfoVo getNudges(@ParameterObject Pageable pageable) {
        return new NudgeInfoVo();
    }
}
