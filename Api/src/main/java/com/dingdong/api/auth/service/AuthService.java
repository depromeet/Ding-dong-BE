package com.dingdong.api.auth.service;

import static com.dingdong.core.exception.GlobalException.NOT_FOUND_USER;
import static com.dingdong.domain.domains.user.domain.enums.GenderType.findGenderType;

import com.dingdong.api.auth.controller.response.AuthResponse;
import com.dingdong.api.config.ApplicationProperty;
import com.dingdong.core.exception.BaseException;
import com.dingdong.core.jwt.JwtTokenProvider;
import com.dingdong.domain.domains.user.domain.User;
import com.dingdong.domain.domains.user.domain.UserRepository;
import com.dingdong.infrastructure.client.feign.KakaoApiFeignClient;
import com.dingdong.infrastructure.client.feign.KakaoAuthFeignClient;
import com.dingdong.infrastructure.client.feign.dto.request.KakaoAuthRequest;
import com.dingdong.infrastructure.client.feign.dto.response.KakaoAuthResponse;
import com.dingdong.infrastructure.client.feign.dto.response.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    private final KakaoAuthFeignClient kakaoAuthFeignClient;
    private final KakaoApiFeignClient kakaoApiFeignClient;

    @Value("${client.kakao.authUrl}")
    private String kakaoAuthUrl;

    @Value("${client.kakao.apiUrl}")
    private String kakaoApiUrl;

    @Value("${client.kakao.clientId}")
    private String clientId;

    @Value("${client.kakao.clientSecret}")
    private String clientSecret;

    private final ObjectProvider<ApplicationProperty> provider;

    @Transactional
    public AuthResponse loginKakao(String authCode) {
        KakaoUserInfoResponse kakaoUserInfoResponse = getKakaoUserInfo(getKakaoAuthToken(authCode));

        return saveUserAndGetToken(kakaoUserInfoResponse);
    }

    public AuthResponse saveUserAndGetToken(KakaoUserInfoResponse kakaoUserInfoResponse) {
        User user =
                userRepository
                        .findByEmail(kakaoUserInfoResponse.getKakaoAcount().getEmail())
                        .orElseGet(() -> createUser(kakaoUserInfoResponse));

        return createAuthResponse(user);
    }

    @Transactional
    public AuthResponse reissue(String refreshToken) {
        Long userId = jwtTokenProvider.parseRefreshToken(refreshToken);
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new BaseException(NOT_FOUND_USER));
        return createAuthResponse(user);
    }

    private User createUser(KakaoUserInfoResponse kakaoUserInfoResponse) {
        User user =
                User.toEntity(
                        kakaoUserInfoResponse.getKakaoAcount().getEmail(),
                        findGenderType(kakaoUserInfoResponse.getKakaoAcount().getGender()),
                        kakaoUserInfoResponse.getKakaoAcount().getAgeRange());

        return userRepository.save(user);
    }

    private AuthResponse createAuthResponse(User user) {
        return AuthResponse.of(
                jwtTokenProvider.generateAccessToken(user.getId(), "USER"),
                jwtTokenProvider.generateRefreshToken(user.getId()),
                user.getId(),
                jwtTokenProvider.getAccessTokenTTlSecond());
    }

    private KakaoAuthResponse getKakaoAuthToken(String authCode) {
        ApplicationProperty applicationProperty = provider.getObject();
        return kakaoAuthFeignClient.getKakaoAuth(
                KakaoAuthRequest.createAuthFormData(
                        authCode,
                        clientId,
                        clientSecret,
                        applicationProperty.getLoginRedirectUri()));
    }

    private KakaoUserInfoResponse getKakaoUserInfo(KakaoAuthResponse response) {
        return kakaoApiFeignClient.getKakaoUserInfo(
                response.toAccessToken(response.getAccessToken()));
    }
}
