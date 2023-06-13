package com.dingdong.api.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope("request")
public class ApplicationProperty {
    private String loginRedirectUri;
}
