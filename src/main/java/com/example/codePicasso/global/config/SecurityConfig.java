package com.example.codePicasso.global.config;

import com.example.codePicasso.domain.user.entity.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                // Todo url 맵핑 바꿔야됨
                // Spring의 패턴 매칭 규칙에 따르면, “” 와일드카드는 패턴의 마지막에만 위치할 수 있습니다.
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/**/admin").hasRole(UserStatus.ADMIN.name()) //todo admin 검증 로직이 없어서 접근 불가
                                .requestMatchers("/**/hi").permitAll()
                                .requestMatchers("/index.html").permitAll()
                                .requestMatchers("/ws/**").permitAll()
                                .anyRequest().authenticated()

                );
        return httpSecurity.build();
    }
}
