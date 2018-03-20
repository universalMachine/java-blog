package com.wang.blog.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.wang.blog.BaseTest;
import com.wang.blog.repository.BoardRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BoardServiceTest extends BaseTest{
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    BoardService boardService;

    @Test
    public void addBoard() {
    }
    @DatabaseSetup("classpath:/dataset/board.xls")
    @ExpectedDatabase(value = "classpath:/dataset/boardService-deleteBoardByIdExpected.xls",assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void deleteBoardById() {
        boardService.deleteBoardById(1);
    }
}