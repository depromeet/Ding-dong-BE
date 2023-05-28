package com.dingdong.core.config;


import com.dingdong.core.properties.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({JwtProperties.class})
@Configuration
public class ConfigurationPropertiesConfig {
}
