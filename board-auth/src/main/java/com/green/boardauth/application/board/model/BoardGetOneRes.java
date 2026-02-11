package com.green.boardauth.application.board.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardGetOneRes {
    private long id;
    private String title;
    private String nm;
    private String createdAt;
    private long userId;
    private String contents;
}
