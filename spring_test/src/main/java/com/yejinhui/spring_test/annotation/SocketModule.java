package com.yejinhui.spring_test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模块号
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/29 20:52
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketModule {

    /**
     * 模块
     *
     * @return
     */
    short module();

}
