package com.green.boardauth.application.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDelReq {
    private long id;
    private long signedUserId;
}
