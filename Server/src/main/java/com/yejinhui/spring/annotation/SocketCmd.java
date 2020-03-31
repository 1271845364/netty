package com.yejinhui.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 命令号
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/29 20:55
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketCmd {

    /**
     * 命令号
     *
     * @return
     */
    short cmd();
}
