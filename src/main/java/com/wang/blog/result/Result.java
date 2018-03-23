package com.wang.blog.result;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.wang.blog.domain.User;

import java.io.IOException;

public class Result {
    private Integer returnCode;

    private String returnMessage;

    @JsonUnwrapped
    private Data data;

    //private User user;

    private JsonNode extra;

    public Result() {
    }

    public Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public JsonNode getExtra() {
        return extra;
    }

    public void setExtra(JsonNode extra) {
        this.extra = extra;
    }

    public void setExtra(String jsonStrSchema,Object ...values) throws IOException{
        String jsonStr = String.format(jsonStrSchema,values);
        ObjectMapper objectMapper = new ObjectMapper();
        this.extra = objectMapper.readTree(jsonStr);
    }
}
