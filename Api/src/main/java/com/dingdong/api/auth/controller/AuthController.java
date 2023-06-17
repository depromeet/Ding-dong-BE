package com.dingdong.api.auth.controller;


import com.dingdong.api.auth.controller.request.AuthRequest;
import com.dingdong.api.auth.controller.response.AuthResponse;
import com.dingdong.api.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인/회원가입")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "카카오 소셜 로그인")
    @PostMapping("/login/kakao")
    public AuthResponse loginKakao(@RequestBody AuthRequest request) {
        return authService.loginKakao(request);
    }

    @Operation(summary = "리프레시 토큰으로 액세스 토큰 재발급")
    @GetMapping("/login/reissue")
    public AuthResponse reissue(@RequestHeader(value = "REFRESH_TOKEN") String refreshToken) {
        return authService.reissue(refreshToken);
    }
}
