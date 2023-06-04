package com.dingdong.api.auth.service;

import static com.dingdong.domain.domains.user.domain.enums.GenderType.findGenderType;

import com.dingdong.api.auth.client.KakaoApiFeignClient;
import com.dingdong.api.auth.client.KakaoAuthFeignClient;
import com.dingdong.api.auth.dto.request.KakaoAuthRequest;
import com.dingdong.api.auth.dto.response.AuthResponse;
import com.dingdong.api.auth.dto.response.KakaoAuthResponse;
import com.dingdong.api.auth.dto.response.KakaoUserInfoResponse;
import com.dingdong.core.jwt.JwtTokenProvider;
import com.dingdong.domain.domains.user.domain.User;
import com.dingdong.domain.domains.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
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

    @Value("${client.kakao.redirectUri}")
    private String redirectUri;

    @Transactional
    public AuthResponse loginKakao(String authCode) {

        KakaoUserInfoResponse kakaoUserInfoResponse = getKakaoUserInfo(getKakaoAuthToken(authCode));
        return saveUserAndGetToken(kakaoUserInfoResponse);
    }

    private String createToken(Long userId) {
        return jwtTokenProvider.generateAccessToken(userId, "USER");
    }

    private String createRefreshToken(Long userId) {
        return jwtTokenProvider.generateRefreshToken(userId);
    }

    private AuthResponse saveUserAndGetToken(KakaoUserInfoResponse kakaoUserInfoResponse) {

        User user = userRepository.findByEmail(kakaoUserInfoResponse.getKakaoAcount().getEmail());
        if (user == null) {
            user =
                    User.toEntity(
                            kakaoUserInfoResponse.getKakaoAcount().getEmail(),
                            findGenderType(kakaoUserInfoResponse.getKakaoAcount().getGender()),
                            kakaoUserInfoResponse.getKakaoAcount().getAgeRange(),
                            null,
                            kakaoUserInfoResponse.getProperties().getProfileImage());
            userRepository.save(user);
        }
        log.info("user.getId() : {}", user.getId());
        return AuthResponse.of(
                createToken(user.getId()),
                createRefreshToken(user.getId()),
                user.getId(),
                jwtTokenProvider.getAccessTokenTTlSecond());
    }

    // authCode 로 kakao accessToken 조회
    private KakaoAuthResponse getKakaoAuthToken(String authCode) {
        return kakaoAuthFeignClient.getKakaoAuth(
                KakaoAuthRequest.getFormData(authCode, clientId, clientSecret, redirectUri));
    }

    private KakaoUserInfoResponse getKakaoUserInfo(KakaoAuthResponse response) {
        return kakaoApiFeignClient.getKakaoUserInfo(
                response.toAccessToken(response.getAccessToken()));
    }
}
