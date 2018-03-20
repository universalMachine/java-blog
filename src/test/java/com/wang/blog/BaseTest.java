package com.wang.blog;

import com.config.XlsDataSetLoader;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.*;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
       TransactionDbUnitTestExecutionListener.class
})
@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
@ActiveProfiles("test")
public class BaseTest {

    @Autowired
    protected TransactionTemplate transactionTemplate;

    @Autowired
    protected SessionFactory sessionFactory;

    protected  <T> void printPage(Page<T> page){
        List<T> elements = page.getContent();
        ListIterator<T> listIterator = elements.listIterator();
        listIterator.forEachRemaining(element -> {
            System.out.println("=======");
            System.out.println(element);
            System.out.println("=======");
        });
    }

}