package com.dingdong.api.user.controller;


import com.dingdong.api.auth.controller.response.UserInfoResponse;
import com.dingdong.api.user.controller.request.UserInfoRequest;
import com.dingdong.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Deprecated(since = "DEP-63", forRemoval = true)
    @Operation(summary = "유저가 프로필 정보 등록/수정")
    @PostMapping("/info")
    public UserInfoResponse updateUserInfo(@RequestBody UserInfoRequest request) {
        // Todo: 미경님 여기 고치고 NoArg 도 삭제해주세용
        return userService.updateUserInfo(request);
    }
}
