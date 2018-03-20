package com.wang.blog.Dao;

import com.wang.blog.BaseTest;
import com.wang.blog.domain.Board;
import com.wang.blog.repository.BoardRepository;
import org.hibernate.internal.SessionImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
@ActiveProfiles(value = "dev",inheritProfiles = false)
public class BoardDaoTest extends BaseTest{
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardDao boardDao;
    @Test
    public void testFindAllLazyCollections(){
        List<Board> boards = boardRepository.findAll();
     //   Board board = boardRepository.findOne(8);
        return;

    }

    @Test
    public void testPagedLazyCollections(){
        Page<Board> boards = boardDao.getPagedBoards(5,1);
        return;
    }

    @Test
    public void testStream() {
        List<Integer> test = new ArrayList<>();
        test = Stream.of(1,2,3,4).collect(Collectors.toList());
        test.stream().map(i -> {return i +2;});
        System.out.println(test);

    }
}