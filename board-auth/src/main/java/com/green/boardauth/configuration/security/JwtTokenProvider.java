package com.green.boardauth.configuration.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.green.boardauth.configuration.constants.ConstJwt;
import com.green.boardauth.configuration.model.JwtUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component //역할이 없는 빈등록
public class JwtTokenProvider {
    private final ObjectMapper objectMapper; //(내장)Jackson 라이브러리 DI받을 속성
    private final ConstJwt constJwt;
    private final SecretKey secretKey;

    public JwtTokenProvider(ObjectMapper objectMapper, ConstJwt constJwt) {
        this.objectMapper = objectMapper;
        this.constJwt = constJwt;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(constJwt.getSecretKey()));

        log.info("constJwt: {}", this.constJwt);
    }

    public String generateAccessToken(JwtUser jwtUser){
        return generateToken(jwtUser , constJwt.getAccessTokenValidityMilliseconds());
    }
    public String generateRefreshToken(JwtUser jwtUser){
        return generateToken(jwtUser , constJwt.getRefreshTokenValidityMilliseconds());
    }



//JWT 문자열을 만드는 메소드, 암호화된 문자열(데이터, 토큰만료시간 포함)
    public String generateToken(JwtUser jwtUser, long tokenValidityMilleSeconds){
        Date now = new Date();
        return Jwts.builder()
                // header
                .header().type(constJwt.getBearerFormat())// JWT header (중요하지 않음)
                .and()
                //payload
                .issuer(constJwt.getIssuer())
                .issuedAt(now) // Jwt 만든 일시 (토큰 생성일)
                .expiration(new Date(now.getTime() + tokenValidityMilleSeconds)) // JWT 종료 일시(토큰 만료 일시)
                .claim(constJwt.getClaimKey(), makeClaimMyUserToJson(jwtUser)) // JSON화된 객체를 받아 처리
                /// ////// 키 , 밸유
                // signature
                .signWith(secretKey)
                .compact();
    }

    public String makeClaimMyUserToJson(JwtUser jwtUser){ // 직렬화(Serialization)
        return objectMapper.writeValueAsString(jwtUser); // 객체>JSON문자열로 변환
    }

}