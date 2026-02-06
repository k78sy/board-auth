package com.green.boardauth.application.user;

import com.green.boardauth.application.user.model.UserGetOneRes;
import com.green.boardauth.application.user.model.UserSignInReq;
import com.green.boardauth.application.user.model.UserSignInRes;
import com.green.boardauth.application.user.model.UserSignUpReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
//    private final JwtTokenProvider jwtTokenProvider;

    public int signUp(UserSignUpReq req){
        String hashedPw = passwordEncoder.encode(req.getUpw());
        log.info("hashedPw:{}", hashedPw);
        req.setUpw(hashedPw);
        return userMapper.signUp(req);
    }

    public UserSignInRes signIn(UserSignInReq req){
        UserGetOneRes res = userMapper.findByUid(req.getUid());
        log.info("res: {}", res);
        if(!passwordEncoder.matches(req.getUpw(), res.getUpw())){
            return null;
        }
        /// 로그인 성공? 예전에는 AT,RT를 FE에 전달했음. 지금은? >>보안쿠키 사용할거임
//        JwtUser jwtUser = new JwtUser(res.getId());
//        String accessToken = jwtTokenProvider.generateAccessToken(jwtUser);
//        String refreshToken = jwtTokenProvider.generateRefreshToken(jwtUser);

        return UserSignInRes.builder()
                .signedUserId( res.getId() )
                .nm( res.getNm() )
                .build();
    }
}
