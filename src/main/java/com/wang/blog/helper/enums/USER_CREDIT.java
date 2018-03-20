package com.wang.blog.helper.enums;

public enum USER_CREDIT {
    LOGIN_SUCCESS(5),
    DELETE_MAIN_POST(-50),
    ADD_POST(5),
    ADD_TOPIC(10),
    TOPIC_DIGEST(100),
    REGISTER_SUCCESS(100);

    private Integer updateValue;

    USER_CREDIT(Integer updateValue) {
        this.updateValue = updateValue;
    }

    public Integer getUpdateValue() {
        return updateValue;
    }
}
