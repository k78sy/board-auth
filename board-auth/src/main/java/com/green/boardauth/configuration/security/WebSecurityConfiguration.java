package com.green.boardauth.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // 빈등록
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //시큐리티에서 session 사용 안하겠다
                .httpBasic( hb -> hb.disable() ) //시큐리티에서 제공해주는 로그인 화면 사용하지 않겠다
                .formLogin( fl -> fl.disable() ) // BE가 화면을 만들지 않기 떄문에 formLogin 기능도 비활성하겠다
                .csrf(csrf -> csrf.disable()) // BE가 화면을 만드는 게 아니면 csrf 공격이 의미가 없기 때문에 비활성하겠다
                // 인가 (권한처리)

                // 아래 내용은 (POST) /api/board로 요청이 올 때는 반드시 로그인이 되어있어야 한다.
                .authorizeHttpRequests( req -> req.requestMatchers(HttpMethod.POST, "/api/board").authenticated()
                    .anyRequest().permitAll() //나머지 요청에 대해서는 허용하겠다.
                )
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // 현존 최강 단방향 암호화. 시큐리티 기본 내장
    }
}
