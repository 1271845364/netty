package com.yejinhui.common.core.exception;

/**
 * 错误码携带异常
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 21:14
 */
public class ErrorCodeException extends RuntimeException {

    /**
     * 错误代码
     */
    private final int errorCode;

    public ErrorCodeException(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
