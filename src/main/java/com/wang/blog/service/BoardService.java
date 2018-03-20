package com.wang.blog.service;

import com.wang.blog.DTO.BoardDTO;
import com.wang.blog.Dao.BoardDao;
import com.wang.blog.domain.Board;
import com.wang.blog.domain.User;
import com.wang.blog.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardDao boardDao;

    @Autowired
    private DTOService dtoService;

    public void updateTopicNum(Board board, int updateNum){
        board.setTopicNum(board.getTopicNum()+ updateNum);
    }

    public void incTopicNum(Board board){
        updateTopicNum(board,1);
    }

    public void addBoard(BoardDTO boardDTO){
        Board board = dtoService.convertBoardDtoToPost(boardDTO);

        List<User> users = board.getUsers();
        for(User user: users ) user.addBoard(board);

        if(board.getBoardDesc() == null){
            board.setBoardDesc("这是一个很有趣的版块哦！");
        }

        boardRepository.save(board);
    }

    @Transactional
   public void deleteBoardById(int boardId){
        Board board = boardRepository.findOne(boardId);
        List<User> users = board.getUsers();
        for(User user: users ) user.removeBoard(board);

        boardRepository.delete(boardId);
   }

   public Page<Board> getPagedBoards(Integer pageNum,Integer pageSize){
       return boardDao.getPagedBoards(pageNum,pageSize);
   }

   public  List<Board> getAllBoards(){
       return boardRepository.findAll();
   }
}
