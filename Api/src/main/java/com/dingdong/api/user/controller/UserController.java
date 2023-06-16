package com.dingdong.api.user.controller;


import com.dingdong.api.user.controller.response.UserProfileResponse;
import com.dingdong.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저")
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
}
