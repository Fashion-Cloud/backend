package com.techeer.fashioncloud.global.config;

import com.techeer.fashioncloud.global.auth.util.TokenProvider;
import com.techeer.fashioncloud.global.error.handler.JwtAccessDeniedHandler;
import com.techeer.fashioncloud.global.error.handler.JwtAuthenticationEntryPoint;
import com.techeer.fashioncloud.global.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CorsFilter corsFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()

                // 세션 미사용 설정
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // auth exception handler 추가
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // 프레임 origin 제한 설정
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .addFilter(corsFilter)
                .formLogin().disable()
                .httpBasic().disable()

                // 로그인, 회원가입 api는 토큰 없이도 호출 가능
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll() // 인증되지 않은 사용자도 허용
                .anyRequest().authenticated()

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
