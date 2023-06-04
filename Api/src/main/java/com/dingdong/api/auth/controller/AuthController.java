package com.dingdong.api.auth.controller;


import com.dingdong.api.auth.dto.response.AuthResponse;
import com.dingdong.api.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인/회원가입")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "카카오 소셜 로그인")
    @GetMapping("/login/kakao")
    public AuthResponse loginKakao(@RequestParam String authCode) {
        return authService.loginKakao(authCode);
    }
}
