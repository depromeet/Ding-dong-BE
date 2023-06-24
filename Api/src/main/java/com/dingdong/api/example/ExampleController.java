package com.dingdong.api.example;


import com.dingdong.core.annotation.ApiErrorCodeExample;
import com.dingdong.core.exception.GlobalException;
import com.dingdong.domain.domains.community.exception.CommunityErrorCode;
import com.dingdong.domain.domains.idcard.exception.IdCardErrorCode;
import com.dingdong.domain.domains.user.exception.AuthErrorCode;
import com.dingdong.domain.domains.user.exception.UserErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
@RequiredArgsConstructor
@Tag(name = "에러 코드 문서화")
public class ExampleController {

    @GetMapping("/global")
    @Operation(summary = "글로벌 (서버 내부 오류, 인증 관련 오류 등) 도메인 관련 에러 코드")
    @ApiErrorCodeExample(GlobalException.class)
    public void getGlobalErrorCode() {}

    @GetMapping("/auth")
    @Operation(summary = "인증 도메인 관련 에러 코드 (글로벌 에러 코드에도 포함)")
    @ApiErrorCodeExample(AuthErrorCode.class)
    public void getAuthErrorCode() {}

    @GetMapping("/user")
    @Operation(summary = "유저 도메인 관련 에러 코드")
    @ApiErrorCodeExample(UserErrorCode.class)
    public void getUserErrorCode() {}

    @GetMapping("/communities")
    @Operation(summary = "행성(커뮤니티) 도메인 관련 에러 코드")
    @ApiErrorCodeExample(CommunityErrorCode.class)
    public void getCommunityErrorCode() {}

    @GetMapping("/id-cards")
    @Operation(summary = "주민증 도메인 관련 에러 코드")
    @ApiErrorCodeExample(IdCardErrorCode.class)
    public void getIdCardErrorCode() {}
}
