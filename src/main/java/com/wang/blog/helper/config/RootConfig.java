package com.wang.blog.helper.config;


import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableConfigurationProperties
@Configuration
public class RootConfig {
    @Bean(name="dataSource")
    @Profile("dev")
    @ConfigurationProperties(prefix="spring.datasource")
    public javax.sql.DataSource devDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="dataSource")
    @Profile("test")
    @ConfigurationProperties(prefix="spring.datasource")
    public javax.sql.DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }


}
