package com.wang.blog.Dao;

import com.wang.blog.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDao extends BaseDao{
    private  final String onlyFromBoard  = " from Board as b ";
    private  final String GET_PAGED_BOARD = onlyFromBoard + "order by b.topicNum desc";

    public Page<Board> getPagedBoards(Integer pageNo, Integer pageSize){
        return pagedQuery(GET_PAGED_BOARD,pageNo,pageSize,Board.class);
    }
}
