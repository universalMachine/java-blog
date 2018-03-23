package com.wang.blog.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResultTest {
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void setExtra() throws Exception{

        Result result = new Result();
        result.setExtra("{\"obj\":%d , \"obj2\":\"%s\"}",23,"mine");
        String resultStr = objectMapper.writeValueAsString(result);
        System.out.println();
    }
}