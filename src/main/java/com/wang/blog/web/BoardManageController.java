package com.wang.blog.web;

import com.wang.blog.DTO.BoardDTO;
import com.wang.blog.domain.Board;
import com.wang.blog.helper.config.ParamDataBInd.JsonArg;
import com.wang.blog.helper.enums.ReturnCode;
import com.wang.blog.repository.BoardRepository;
import com.wang.blog.result.Data;
import com.wang.blog.result.Result;
import com.wang.blog.service.BoardService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class BoardManageController {
    //@Autowired
    //private ForumService forumService;

    @Autowired
    private BoardService boardService;
    @Autowired
    private Logger logger;

    /**
     * 列出论坛模块下的主题帖子
     */
    @GetMapping("/{boardId}/boardTopics")
    public Result listBoardTopics(@PathVariable("boardID") Integer boardId,
                                  @RequestParam(value = "pageNo",required = false) Integer pageNo){
        return null;
    }

    /**
     * 列出分页的板块
     */

    @GetMapping("/board")
    public Result listPagedBoard(@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNo){
        Result result = new Result();
        try{


           /* List<Board> boards = boardRepository.findAll();
            Iterator<Board> iter = boards.iterator();
            while(iter.hasNext()){
                Board board = iter.next();
                board.setUsers(new ArrayList<>());
                board.setTopics(new ArrayList<>());
            }

            Data<List<Board>> pagedBoardData= new Data<>(boards);*/

            Page<Board> pageBoard = boardService.getPagedBoards(pageNo, pageSize);
            //pageBoard.pageBoard.getContent().stream().map((Function<? super Board, Board>) board -> {board.setUsers(new ArrayList());board.setTopics(new ArrayList());return board;});
            Iterator<Board> iter = pageBoard.getContent().iterator();
            while(iter.hasNext()){
                Board board = iter.next();
                board.setUsers(new ArrayList<>());
                board.setTopics(new ArrayList<>());
            }

            Data<Page<Board>> pagedBoardData = new Data<>(pageBoard);


            result.setReturnCode(ReturnCode.success.getValue());

            result.setData(pagedBoardData);
        }catch (Exception e){

        }finally {
            return result;
        }

    }

    /**
     * 增加版块
     *
     */

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/board/add")
    public Result addBoard(@RequestBody BoardDTO boardDTO){
        Result result = new Result();


        try {
            boardService.addBoard(boardDTO);
            result.setReturnCode(200);
            result.setReturnMessage("添加成功");
            result.setData(new Data<BoardDTO>(boardDTO));
        }catch (Exception e){
            result.setReturnCode(500);
            result.setReturnMessage("添加失败，请重新添加");
        }finally {
            return result;
        }
    }

    /**
     * 删除版块
     */
    @DeleteMapping("/board/delete")
    public Result deleteBoard(@RequestParam Integer boardId){
        Result result = new Result();
        try{
            boardService.deleteBoardById(boardId);
            result.setExtra("{\"boardId\":%d}",boardId);
            result.setReturnCode(ReturnCode.success.getValue());
        }catch (Exception e){
            logger.debug(e.getMessage());
        }finally {
            return  result;
        }
    }


}
