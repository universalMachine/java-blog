package com.wang.blog.helper.enums;

public enum TOPIC_DIGEST {
    NORMAL(0),
    DIGEST(1),
    HOT(2);

    private Integer value;

    TOPIC_DIGEST(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
