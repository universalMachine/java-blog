package com.wang.blog.helper.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wang.blog.result.Result;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MySavedRequestAwareAuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws ServletException, IOException {

    /*    response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,"true");
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,request.getHeader(HttpHeaders.ORIGIN));
        if(request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())){
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"POST,OPTIONS");
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,HttpHeaders.CONTENT_TYPE+",x-requested-with");
        }*/

        ObjectMapper objectMapper = new ObjectMapper();
        Result result = new Result();
        result.setReturnCode(200);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
        try{
            response.getWriter().println(objectMapper.writeValueAsString(result));
        }catch (Exception e){
            response.getWriter().println("{\" returnCode:200\"}");
        }



        SavedRequest savedRequest
                = requestCache.getRequest(request, response);

        if (savedRequest == null) {
            clearAuthenticationAttributes(request);
            return;
        }
        String targetUrlParam = getTargetUrlParameter();

        if (isAlwaysUseDefaultTargetUrl()
                || (targetUrlParam != null
                && StringUtils.hasText(request.getParameter(targetUrlParam)))) {
            requestCache.removeRequest(request, response);
            clearAuthenticationAttributes(request);
            return;
        }


        clearAuthenticationAttributes(request);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}