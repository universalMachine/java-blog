package com.wang.blog.helper.config.ParamDataBInd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;

@Component
public class JsonArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
       return parameter.hasParameterAnnotation(JsonArg.class);

    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        try{
            JsonNode jsonNode = getRequestBody(webRequest);
            String jsonPath = parameter.getParameterAnnotation(JsonArg.class).value();

            return jsonNode.findValue(jsonPath).asText("");
        }catch (Exception e){
            return "";
        }

    }

    protected JsonNode getRequestBody(NativeWebRequest webRequest) {
        ContentCachingRequestWrapper servletRequest = webRequest.getNativeRequest(ContentCachingRequestWrapper.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            // System.out.println("getRequestBody3");
            //System.out.println(servletRequest);
            //System.out.println(servletRequest.getHeader("content-type"));
            //jsonParser = jsonFactory.createParser(servletRequest.getInputStream());



            JsonNode jsonNode = mapper.readTree(servletRequest.getContentAsByteArray());
            return jsonNode;
            //  return  jsonParser.getText();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}