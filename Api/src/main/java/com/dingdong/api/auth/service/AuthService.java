package com.dingdong.api.auth.service;

import static com.dingdong.core.consts.StaticVal.BEARER;
import static com.dingdong.domain.domains.user.domain.enums.GenderType.findGenderType;

import com.dingdong.api.auth.controller.request.AuthRequest;
import com.dingdong.api.auth.controller.response.AuthResponse;
import com.dingdong.core.jwt.JwtTokenProvider;
import com.dingdong.domain.domains.user.domain.UserRepository;
import com.dingdong.domain.domains.user.domain.adaptor.UserAdaptor;
import com.dingdong.domain.domains.user.domain.entity.User;
import com.dingdong.infrastructure.client.feign.KakaoApiFeignClient;
import com.dingdong.infrastructure.client.feign.KakaoAuthFeignClient;
import com.dingdong.infrastructure.client.feign.dto.request.KakaoAuthRequest;
import com.dingdong.infrastructure.client.feign.dto.response.KakaoAuthResponse;
import com.dingdong.infrastructure.client.feign.dto.response.KakaoUserInfoResponse;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserAdaptor userAdaptor;
    private final RedisTemplate<String, String> redisTemplate;

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

    @Transactional
    public AuthResponse loginKakao(AuthRequest request) {
        KakaoUserInfoResponse kakaoUserInfoResponse =
                getKakaoUserInfo(
                        getKakaoAuthToken(request.getAuthCode(), request.getRedirectUri()));

        return saveUserAndGetToken(kakaoUserInfoResponse);
    }

    public AuthResponse saveUserAndGetToken(KakaoUserInfoResponse kakaoUserInfoResponse) {
        User user =
                userRepository
                        .findByEmail(kakaoUserInfoResponse.getKakaoAcount().getEmail())
                        .orElseGet(() -> createUser(kakaoUserInfoResponse));

        return createAuthResponse(user);
    }

    public AuthResponse reissue(String refreshToken) {
        Long userId = jwtTokenProvider.parseRefreshToken(refreshToken);
        return createAuthResponse(userAdaptor.findById(userId));
    }

    public void logout(String token) {
        String parseToken = validateAndParseToken(token);
        Long leftAccessTokenTTlSecond = jwtTokenProvider.getLeftAccessTokenTTLSecond(parseToken);

        redisTemplate
                .opsForValue()
                .set(parseToken, "logout", leftAccessTokenTTlSecond, TimeUnit.MILLISECONDS);
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

    private KakaoAuthResponse getKakaoAuthToken(String authCode, String redirectUri) {
        return kakaoAuthFeignClient.getKakaoAuth(
                KakaoAuthRequest.createAuthFormData(authCode, clientId, clientSecret, redirectUri));
    }

    private KakaoUserInfoResponse getKakaoUserInfo(KakaoAuthResponse response) {
        return kakaoApiFeignClient.getKakaoUserInfo(
                response.toAccessToken(response.getAccessToken()));
    }

    private String validateAndParseToken(String token) {
        return (token != null && token.startsWith(BEARER))
                ? token.substring(BEARER.length())
                : null;
    }
}
