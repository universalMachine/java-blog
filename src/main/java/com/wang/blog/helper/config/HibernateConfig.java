package com.wang.blog.helper.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
