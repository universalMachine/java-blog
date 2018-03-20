package com.wang.blog.Dao;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.wang.blog.BaseTest;
import com.wang.blog.domain.Post;
import com.wang.blog.repository.PostRepository;
import com.wang.blog.repository.TopicRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

@RunWith(SpringJUnit4ClassRunner.class)
public class PostDaoTest extends BaseTest {
    @Autowired
    private PostDao postDao;
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private PostRepository postRepository;



    private Page<Post> page;

    @After
    public void tearDown() {
        if(page!= null)
        printPage(page);
    }

    @DatabaseSetup("classpath:/dataset/post.xls")
    @Test
    public void getPagedPost() {
        page = postDao.getPagedPost(1,1,1);
    }


    @DatabaseSetup("classpath:/dataset/post.xls")
    @ExpectedDatabase(value = "classpath:/dataset/postExpected.xls",assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void deleteTopicPosts() {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            postDao.deleteTopicPosts(38);




            }
        });




    }

    @DatabaseSetup("classpath:/dataset/post.xls")
   // @ExpectedDatabase(value = "classpath:/dataset/postExpected.xls",assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Test
    public void getPostsByTopicId(){
          transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                System.out.println(postDao.getPostsByTopicId(38));

            }
        });


    }


}