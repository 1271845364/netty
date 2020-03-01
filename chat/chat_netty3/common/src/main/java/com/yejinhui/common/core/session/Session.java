package com.yejinhui.common.core.session;

/**
 * 会话抽象接口，这样做的目的是，因为netty的不同版本client链接server的api都不一样，
 * 不想让用户去适配，只需要在server这给每个netty版本写一个实现，暴露这个接口，client不需要修改代码
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 21:19
 */
public interface Session {

    /**
     * 获取绑定的对象
     *
     * @return
     */
    Object getAttachment();

    /**
     * 绑定对象
     */
    void setAttachment(Object attachment);

    /**
     * 移除绑定对象
     */
    void removeAttachment();

    /**
     * 向会话中写入数据
     *
     * @param message
     */
    void write(Object message);

    /**
     * 判断会话是否在链接中
     *
     * @return
     */
    boolean isConnected();

    /**
     * 关闭
     */
    void close();
}
