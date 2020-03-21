package com.yejinhui.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求命令号
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 14:58
 */
@Target(value={ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketCommand {

    /**
     * 请求命令号
     *
     * @return
     */
    short command();
}
