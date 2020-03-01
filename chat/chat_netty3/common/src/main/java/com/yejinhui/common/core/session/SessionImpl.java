package com.yejinhui.common.core.session;


import org.jboss.netty.channel.Channel;

/**
 * 会话实现类
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 21:27
 */
public class SessionImpl implements Session {

    private Channel channel;

    public SessionImpl(Channel channel) {
        this.channel = channel;
    }

    @Override
    public Object getAttachment() {
        return channel.getAttachment();
    }

    @Override
    public void setAttachment(Object attachment) {
        channel.setAttachment(attachment);
    }

    @Override
    public void removeAttachment() {
        channel.setAttachment(null);
    }

    @Override
    public void write(Object message) {
        channel.write(message);
    }

    @Override
    public boolean isConnected() {
        return channel.isConnected();
    }

    @Override
    public void close() {
        channel.close();
    }
}
