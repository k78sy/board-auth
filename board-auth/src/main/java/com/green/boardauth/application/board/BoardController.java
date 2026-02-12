package com.green.boardauth.application.board;

import com.green.boardauth.application.board.model.*;
import com.green.boardauth.configuration.model.ResultResponse;
import com.green.boardauth.configuration.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    public final BoardService boardService;

    @PostMapping
    public ResultResponse<?> postBoard(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                       @RequestBody BoardPostReq req){
        log.info("통신됐다!");
        req.setUserId(userPrincipal.getSignedUserId());
        log.info("signedUserId: {}", userPrincipal.getSignedUserId());
        log.info("req: {}", req);
        long id = boardService.postBoard(req);
        String message = id > 0? "등록성공" : "등록실패";
        return new ResultResponse<>(message, id);
    }

    @GetMapping
    public ResultResponse<?> getBoardList(@ModelAttribute BoardGetReq req){ //ReauestParam 하나정도만 일때.. @ModelAttribute는...
        log.info("req: {}", req);
        List<BoardGetRes> list = boardService.getBoardList(req);
        return new ResultResponse<>(String.format("%d rows", list.size()), list);
    }


    @GetMapping("max_page")
    public ResultResponse<?> getBoardMaxPage(@ModelAttribute BoardGetMaxPageReq req){
        log.info("req: {}", req);
        int maxPage = boardService.getBoardMaxPage(req);
        return new ResultResponse<>(String.format("maxPage: %d", maxPage), maxPage);
    }

    @GetMapping("{id}")
    public ResultResponse<?> getBoard(@PathVariable int id){
        BoardGetOneRes detail = boardService.getBoardDetail(id);
        return new ResultResponse<>(String.format("Article no: %d", id), detail);
    }

    @DeleteMapping
    public ResultResponse<?> deleteBoard(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                         @ModelAttribute BoardDelReq req){
        req.setSignedUserId(userPrincipal.getSignedUserId()); //로그인한 사용자의 id값 담기
        log.info("req {}", req);
        int result = boardService.deleteBoard(req);
        return new ResultResponse<>(result == 1? "삭제성공" : "삭제 권한이 없습니다", result);
    }

    @PutMapping
    public ResultResponse<?> updateBoard(@AuthenticationPrincipal UserPrincipal userPrincipal
                                        ,@RequestBody BoardPutReq req){
        req.setSignedUserId( userPrincipal.getSignedUserId() );
//        log.info("signedUserId: {}", userPrincipal.getSignedUserId());
//        log.info("req: {}", req);
        boardService.updateBoard(req);
        return new ResultResponse<>("수정성공", req.getId());
    }
}
