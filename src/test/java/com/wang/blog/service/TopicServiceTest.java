package com.wang.blog.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.wang.blog.BaseTest;
import com.wang.blog.Dao.TopicDao;
import com.wang.blog.domain.Topic;
import com.wang.blog.repository.TopicRepository;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

public class TopicServiceTest extends BaseTest{
    @Autowired
    private TopicService topicService;
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @Test
    @DatabaseSetup("classpath:/dataset/topic.xls")
    public void addTopic() {
        Topic topic = topicRepository.findOne(38);
        topicService.addTopic(topic);
    }


    @Test
    //@DatabaseSetup("classpath:/dataset/topic.xls")
    //@ExpectedDatabase(value = "classpath:/dataset/postExpected.xls",assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void deleteTopic() {
       transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Topic topic = topicRepository.findOne(38);
                topicService.deleteTopic(topic);
            }
        });

    }

    @DatabaseSetup("classpath:/dataset/topic.xls")
    @ExpectedDatabase(value = "classpath:/dataset/topicService-makeTopicDigestExpected.xls",assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void makeTopicDigest() {
        topicService.makeTopicDigest(38);
    }
}