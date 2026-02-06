package com.green.boardauth.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 빈등록
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //시큐리티에서 session 사용 안하겠다
                .httpBasic( hb -> hb.disable() ) //시큐리티에서 제공해주는 로그인 화면 사용하지 않겠다
                .formLogin( fl -> fl.disable() ) // BE가 화면을 만들지 않기 떄문에 formLogin 기능도 비활성하겠다
                .csrf(csrf -> csrf.disable()) // BE가 화면을 만드는 게 아니면 csrf 공격이 의미가 없기 때문에 비활성하겠다
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // 현존 최강 단방향 암호화. 시큐리티 기본 내장
    }
}
