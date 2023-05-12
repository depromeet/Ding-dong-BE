package com.demo.core.config;


import com.demo.core.properties.JwtProperties;
import com.demo.core.properties.OauthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({JwtProperties.class, OauthProperties.class})
@Configuration
public class ConfigurationPropertiesConfig {}
