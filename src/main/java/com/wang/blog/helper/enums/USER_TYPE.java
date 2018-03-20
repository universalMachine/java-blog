package com.wang.blog.helper.enums;

public enum USER_TYPE {
    NORMAL_USER,
    BORARD_MANAGER,
    FORUM_MANAGER;

    private int value;

    USER_TYPE() {

    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
