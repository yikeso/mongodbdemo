package com.china.ciic.mongodemo.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * json序列号不返回null
 * @param <T>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse<T> implements Serializable {

    int status;
    String msg;
    T data;

    public ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 响应成功
     * @return
     */
    public static ServerResponse OK(){
        return new ServerResponse(ResponseCode.SUCCESS,null);
    }

    /**
     * 处理失败
     * @return
     */
    public static ServerResponse ERROR(){
        return new ServerResponse(ResponseCode.ERROR,null);
    }

    public int getStatus() {
        return status;
    }

    public ServerResponse status(int status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ServerResponse msg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ServerResponse data(T data) {
        this.data = data;
        return this;
    }

    static interface ResponseCode{
        int SUCCESS = 0;
        int ERROR = 1;
    }
}
