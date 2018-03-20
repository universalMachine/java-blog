package com.wang.blog.helper.config;

import net.sf.ehcache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class EhCacheConfig {
    @Bean
    public EhCacheCacheManager cacheCacheManager(CacheManager cm){
        return new EhCacheCacheManager(cm);
    }
    @Bean
    public EhCacheManagerFactoryBean ehcache(){
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean =new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean;
    }
}
