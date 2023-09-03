package com.techeer.fashioncloud.domain.User.configuration;

import com.techeer.fashioncloud.domain.User.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static io.lettuce.core.AclCategory.ADMIN;
import static org.hibernate.cfg.AvailableSettings.USER;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {
    @Autowired
    private JwtRequestFilter requestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
//                .requestMatchers("/api/v1/address").hasRole("ADMIN")
                .requestMatchers("/api/v1/address").hasRole("ROLE_USER")
                .requestMatchers("/**").permitAll()
                )
//
//                .requestMatchers("/api/v1/users/authenticate", "/api/v1/users/sign-up").permitAll()
//                .requestMatchers("/api/v1/**").hasRole("ROLE_USER")
//                .requestMatchers("/api/v1/address").hasRole("ROLE_ADMIN")
//
//                .authorizeHttpRequests(authorize -> authorize
//                        .antMatchers("/orders/**").hasRole("ADMIN")
//                        .antMatchers("/members/my-page").hasRole("USER")
//                        .antMatchers("/**").permitAll()


                .authorizeHttpRequests().requestMatchers("/api/v1/**")
                .authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
