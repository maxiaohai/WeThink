package com.wethink.dto;

import lombok.Data;

@Data
public class Result<T> {
    private T data;
    private Integer code;
    private String msg;

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     * @param code
     * @param msg
     */
    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 有数据返回时
     * @param data
     */
    public Result(Integer code, String msg, T data) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

}
