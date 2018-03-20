package com.wang.blog.helper.config.security;

import com.wang.blog.BaseTest;
import com.wang.blog.DTO.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomAuthenticationFilterTest  {
    @LocalServerPort
    Integer port;

    @Test
    public void Options() {
        RestTemplate restTemplate  = new RestTemplate();
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("wangwang");
        userDTO.setPassword("wang");
        //restTemplate.postForObject("http://localhost:{port}/login",userDTO,String.class,port);
        restTemplate.optionsForAllow("http://localhost:{port}/login",port);

    }
}