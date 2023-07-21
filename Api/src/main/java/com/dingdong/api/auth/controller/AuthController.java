package com.dingdong.api.auth.controller;


import com.dingdong.api.auth.controller.request.AuthRequest;
import com.dingdong.api.auth.controller.response.AuthResponse;
import com.dingdong.api.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증관련 컨트롤러")
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
    public AuthResponse reissue(@RequestHeader(value = "Refresh-Token") String refreshToken) {
        return authService.reissue(refreshToken);
    }

    @SecurityRequirement(name = "access-token")
    @Operation(summary = "로그아웃")
    @PostMapping("/logouts")
    public void logout(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        authService.logout(token);
    }
}
