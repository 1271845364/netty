package com.yejinhui.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求模块
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 14:51
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketModule {

    /**
     * 请求的模块号
     */
    short module();

}
