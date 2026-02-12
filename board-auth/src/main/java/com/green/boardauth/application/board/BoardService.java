package com.green.boardauth.application.board;

import com.green.boardauth.application.board.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    public long postBoard(BoardPostReq req){
        int result = boardMapper.save(req); // 영향받은 행
        return req.getId();
    }

    public List<BoardGetRes> getBoardList(BoardGetReq req){
        return boardMapper.findAll(req);
    }
    public int getBoardMaxPage(BoardGetMaxPageReq req){
        return boardMapper.findMaxPage(req);
    }

    public BoardGetOneRes getBoardDetail(int id){
        return boardMapper.findOne(id);
    }

    public int deleteBoard(BoardDelReq req){
        return boardMapper.deleteOne(req);
    }

    public int updateBoard(BoardPutReq req){
        return boardMapper.update(req);
    }

    public List<String> relatedSearchTitle(String searchText){
        return boardMapper.relatedSearchTitle(searchText);
    }
}
