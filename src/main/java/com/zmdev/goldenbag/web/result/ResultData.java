package com.zmdev.goldenbag.web.result;

import java.util.HashMap;
import java.util.Map;

public class ResultData<T> extends Result {
    private T data;
    private Map<String, Object> meta;

    public ResultData(T data) {
        this(data, new HashMap<>());
    }

    public ResultData(T data, Map<String, Object> meta) {
        this.data = data;
        this.meta = meta;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }

    public ResultData addMeta(String key, Object value) {
        meta.put(key, value);
        return this;
    }
}
