package com.dingdong.api.config;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebInterceptor implements HandlerInterceptor {

    private final ObjectProvider<ApplicationProperty> provider;

    @Value("${front.domain.local}")
    private String localDomain;

    @Value("${client.kakao.localRedirectUri}")
    private String localRedirectUri;

    @Value("${client.kakao.redirectUri}")
    private String redirectUri;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        ApplicationProperty applicationProperty = provider.getObject();
        if (request.getHeader("host").equals(localDomain)) {
            applicationProperty.setLoginRedirectUri(localRedirectUri);
        } else {
            applicationProperty.setLoginRedirectUri(redirectUri);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView)
            throws Exception {}
}
