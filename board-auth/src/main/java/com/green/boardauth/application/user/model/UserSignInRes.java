package com.green.boardauth.application.user.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder // 부분 속성에 어쩌구저쩌구
public class UserSignInRes {
    private long signedUserId;
    private String nm;
}
