package com.wang.blog.result;

public class Data<T> {
    private final T data ;

    public Data(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
