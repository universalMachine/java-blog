package com.wang.blog.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.wang.blog.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserRepositoryTest extends BaseTest {
    @Autowired
    private UserRepository userRepository;

   // @DatabaseSetup("classpath:/dataset/topic.xls")
    @Test
    public void getUserByUserName() {
       userRepository.getUserByUserName("wnag1");
    }

    @DatabaseSetup("classpath:/dataset/topic.xls")
    @Test
    public void getUserByUserNameNoUser() {
        assertNull(userRepository.getUserByUserName("wang99"));
    }
}