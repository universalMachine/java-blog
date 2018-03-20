package com.wang.blog.Dao;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.wang.blog.BaseTest;
import com.wang.blog.domain.Topic;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import static org.junit.Assert.*;




public class TopicDaoTest extends BaseTest {
    @Autowired
    private TopicDao topicDao;

    private Page<Topic> page;

    @Test
    @DatabaseSetup("classpath:/dataset/topic.xls")
    public void getBoardDigestTopics() {
      page = topicDao.getBoardDigestTopics(1,2,3);


    }

    @Test
    @DatabaseSetup("classpath:/dataset/topic.xls")
    public void queryTopicByTitle() {
        page = topicDao.queryTopicByTitle("我",1,2);

    }

    @After
    public void tearDown() {
        assertNotNull(page);

        printPage(page);
    }


}