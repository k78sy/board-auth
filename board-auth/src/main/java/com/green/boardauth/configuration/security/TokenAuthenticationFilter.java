package com.green.boardauth.configuration.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenManager jwtTokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("req-uri:{}", request.getRequestURI());//요청 주소 로그에 출력

        // 쿠키에 AT 없음 = null 리턴
        // 쿠키에 AT가 있다면 = 주소값 넘어옴
        Authentication authentication = jwtTokenManager.getAuthentication(request);
        log.info("authentication: {}", authentication);
        if(authentication != null){ //로그인 상태
            SecurityContextHolder.getContext().setAuthentication(authentication); //시큐리티 인증처리가 완료
        }
        //다음 필터에게 req, res 전달
        filterChain.doFilter(request,response);
    }
}
