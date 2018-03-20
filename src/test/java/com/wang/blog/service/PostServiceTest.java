package com.wang.blog.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.wang.blog.DTO.PostDTO;
import com.wang.blog.BaseTest;
import com.wang.blog.domain.Topic;
import com.wang.blog.repository.TopicRepository;
import com.wang.blog.repository.UserRepository;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

public class PostServiceTest extends BaseTest {
    PostDTO postDTO;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TopicRepository topicRepository;

    @Autowired
    PostService postService;

    @Before
    public void setUp() {
        postDTO = new PostDTO();
        postDTO.setPostText("testPostService");


        postDTO.setTopicId(38);
        postDTO.setUserId(7);
        postDTO.setPostType(2);
    }


    @DatabaseSetup("classpath:/dataset/post.xls")
    @ExpectedDatabase(value = "classpath:/dataset/postService-addPostExpected.xls",assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void addPost() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                postService.addPost(postDTO);
            }
        });

    }

    @DatabaseSetup("classpath:/dataset/post.xls")
    @Test
    public void deletePostById() {
        postService.deletePostById(35);
    }

    @Test
    public void testIsPersistentAutoSave() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Session session = sessionFactory.getCurrentSession();

                Topic topic = session.load(Topic.class,38);
                topic.setTopicReplies(topic.getTopicReplies()+2);

            }
        });


    }
}