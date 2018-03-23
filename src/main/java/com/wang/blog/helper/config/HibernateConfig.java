package com.wang.blog.helper.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;

@Configuration
public class HibernateConfig {
    @Bean
    @Autowired
    public SessionFactory sessionFactory(EntityManagerFactory factory){
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        return  factory.unwrap(SessionFactory.class);
    }

 /*   @Autowired
    @Bean(name = "jpaTx")
    public PlatformTransactionManager transactionManagerJPA(EntityManagerFactory entityManagerFactory) throws NamingException {
        JpaTransactionManager txManager = new JpaTransactionManager(entityManagerFactory());

        return txManager;
    }*/
/*    @Autowired
    @Bean
    @Primary
    public PlatformTransactionManager txManager(HibernateTransactionManager hibernateTransactionManager) throws Exception {
        HibernateTransactionManager txManager = hibernateTransactionManager;
        txManager.setNestedTransactionAllowed(true);

        return txManager;
    }

    @Autowired
    @Bean
    public HibernateTransactionManager hibTransMan(EntityManagerFactory factory){
        return new HibernateTransactionManager(sessionFactory(factory));
    }*/
}
