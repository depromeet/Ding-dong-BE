package com.dingdong.api.config.security;

import static com.dingdong.core.consts.StaticVal.*;

import com.dingdong.core.dto.AccessTokenDetail;
import com.dingdong.core.dto.ErrorResponse;
import com.dingdong.core.exception.BaseErrorCode;
import com.dingdong.core.exception.BaseException;
import com.dingdong.core.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        String token = parseToken(req);

        /*
        토큰이 널이 아닐 때
         */
        if (token != null) {
            Authentication authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        /*
        필터 돌다가 에러 떨어지면 에러 반환
         */
        try {
            filterChain.doFilter(req, res);
        } catch (BaseException e) {
            exceptionHandle(res, getErrorResponse(e.getErrorCode()));
        }
    }

    private String parseToken(HttpServletRequest req) {
        String authorization = req.getHeader("Authorization");

        /*
        Bearer 토큰인지 검증한 후 리턴 || 아니면 null
         */
        if (authorization != null
                && authorization.length() > BEARER.length()
                && authorization.startsWith(BEARER)) {
            return authorization.substring(BEARER.length());
        }
        return null;
    }

    private Authentication getAuthentication(String token) {
        AccessTokenDetail accessTokenDetail = jwtTokenProvider.parseAccessToken(token);

        UserDetails userDetails =
                CustomUserDetails.of(
                        accessTokenDetail.getUserId().toString(), accessTokenDetail.getRole());
        return new UsernamePasswordAuthenticationToken(
                userDetails, "user", userDetails.getAuthorities());
    }

    private ErrorResponse getErrorResponse(BaseErrorCode errorCode) {
        return new ErrorResponse(errorCode.getErrorDetail());
    }

    private void exceptionHandle(HttpServletResponse res, ErrorResponse errorResponse)
            throws IOException {
        res.setCharacterEncoding("UTF-8");
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setStatus(errorResponse.getStatusCode());
        res.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
