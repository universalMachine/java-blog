package com.wang.blog.helper.enums;

public enum ReturnCode {
    success(200);

    private Integer value;

    ReturnCode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
