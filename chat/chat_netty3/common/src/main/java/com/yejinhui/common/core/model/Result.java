package com.yejinhui.common.core.model;

import com.yejinhui.common.core.serial.Serializer;

/**
 * 结果对象
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 19:57
 */
public class Result<T extends Serializer> {

    /**
     * 结果码
     */
    private int resultCode;

    /**
     * 结果内容
     */
    private T content;

    public static <T extends Serializer> Result<T> SUCCESS(T t) {
        Result result = new Result();
        result.resultCode = ResultCode.SUCCESS;
        result.content = t;
        return result;
    }

    public static Result SUCCESS() {
        Result result = new Result();
        result.resultCode = ResultCode.SUCCESS;
        return result;
    }

    public static <T extends Serializer> Result<T> ERROR(int resultCode, T t) {
        Result<T> result = new Result<>();
        result.resultCode = resultCode;
        result.content = t;
        return result;
    }

    public static Result ERROR(int resultCode) {
        Result result = new Result();
        result.resultCode = resultCode;
        return result;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public boolean isSuccess() {
        return this.resultCode == ResultCode.SUCCESS;
    }
}
