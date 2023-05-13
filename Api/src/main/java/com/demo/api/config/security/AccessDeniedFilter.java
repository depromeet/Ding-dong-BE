package com.demo.api.config.security;

import static com.demo.core.consts.StaticVal.*;

import com.demo.core.dto.ErrorDetail;
import com.demo.core.dto.ErrorResponse;
import com.demo.core.exception.BaseErrorCode;
import com.demo.core.exception.BaseException;
import com.demo.core.exception.GlobalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class AccessDeniedFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) {
        String servletPath = req.getServletPath();
        return PatternMatchUtils.simpleMatch(SwaggerPatterns, servletPath);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (BaseException e) {
            exceptionHandle(response, getErrorResponse(e.getErrorCode()));
        } catch (AccessDeniedException e) {
            ErrorResponse access_denied =
                    new ErrorResponse(
                            GlobalException.NOT_VALID_ACCESS_TOKEN_ERROR.getErrorDetail());
            exceptionHandle(response, access_denied);
        }
    }

    private ErrorResponse getErrorResponse(BaseErrorCode errorCode) {
        ErrorDetail errorDetail = errorCode.getErrorDetail();
        return new ErrorResponse(errorDetail);
    }

    private void exceptionHandle(HttpServletResponse response, ErrorResponse errorResponse)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorResponse.getStatusCode());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
