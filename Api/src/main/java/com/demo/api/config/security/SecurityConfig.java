package com.demo.api.config.security;

import static com.demo.core.consts.StaticVal.*;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final FilterConfig filterConfig;

    @Value("${swagger.user}")
    private String swaggerUser;

    @Value("${swagger.password}")
    private String swaggerPassword;

    /** 스웨거용 인메모리 유저 설정 */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user =
                User.withUsername(swaggerUser)
                        .password(passwordEncoder().encode(swaggerPassword))
                        .roles("SWAGGER")
                        .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin().disable().cors().and().csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().expressionHandler(expressionHandler());

        http.authorizeRequests()
                .mvcMatchers(SwaggerPatterns)
                .permitAll()
                .mvcMatchers("/auth/oauth/**")
                .permitAll()
                .mvcMatchers("/auth/token/refresh")
                .permitAll()
                .mvcMatchers("**/health")
                .permitAll()
                .anyRequest()
                .hasRole("USER");
        http.apply(filterConfig);

        return http.build();
    }

    @Bean
    public DefaultWebSecurityExpressionHandler expressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler =
                new DefaultWebSecurityExpressionHandler();
        //        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }
}
