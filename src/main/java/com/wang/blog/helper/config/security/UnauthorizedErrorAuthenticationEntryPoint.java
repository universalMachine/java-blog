package com.wang.blog.helper.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wang.blog.result.Result;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnauthorizedErrorAuthenticationEntryPoint implements AuthenticationEntryPoint,InitializingBean {
    private  HttpMessageConverter messageConverter;

    public UnauthorizedErrorAuthenticationEntryPoint() {
        this.messageConverter = new MappingJackson2HttpMessageConverter();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

       // MyGenericError error = new MyGenericError();
        //error.setDescription(exception.getMessage());
        //ObjectMapper objectMapper = new ObjectMapper()
        ServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
        outputMessage.setStatusCode(HttpStatus.ACCEPTED);
        Result result = new Result();
        result.setReturnCode(403);
        result.setReturnMessage(exception.getLocalizedMessage());

        messageConverter.write(result, null, outputMessage);
    }

    public void setMessageConverter(HttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if (messageConverter == null) {
            throw new IllegalArgumentException("Property 'messageConverter' is required");
        }
    }
}
