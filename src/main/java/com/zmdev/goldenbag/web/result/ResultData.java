package com.zmdev.goldenbag.web.result;

public class ResultData<T> extends Result {
    private T data;

    public ResultData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
