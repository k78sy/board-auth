package com.green.boardauth.application.board;

import com.green.boardauth.application.board.model.BoardGetReq;
import com.green.boardauth.application.board.model.BoardPostReq;
import com.green.boardauth.configuration.model.ResultResponse;
import com.green.boardauth.configuration.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
        int result = boardService.postBoard(req);
        String message = result == 1? "등록성공" : "등록실패";
        return new ResultResponse<>(message, result);
    }

    @GetMapping
    public ResultResponse<?> getBoardList(@ModelAttribute BoardGetReq req){
        log.info("req: {}", req);
        return null;
    }
}
