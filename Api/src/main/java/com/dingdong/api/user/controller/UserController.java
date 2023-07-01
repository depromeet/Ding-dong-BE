package com.dingdong.api.user.controller;


import com.dingdong.api.user.controller.request.UserCharacterRequest;
import com.dingdong.api.user.controller.response.UserProfileResponse;
import com.dingdong.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저")
@SecurityRequirement(name = "access-token")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "로그인 유저 정보 조회")
    @GetMapping("/profile")
    public UserProfileResponse getUserProfile() {
        return UserProfileResponse.from(userService.getUserProfile());
    }

    @Operation(summary = "온보딩 캐릭터 정보 저장")
    @PostMapping("/character")
    public void saveUserCharacter(@RequestBody @NotNull UserCharacterRequest request) {
        userService.saveUserCharacter(request);
    }

    @Operation(summary = "온보딩 캐릭터 정보 초기화")
    @PutMapping("/character")
    public void removeUserCharacter() {
        userService.removeUserCharacter();
    }
}
