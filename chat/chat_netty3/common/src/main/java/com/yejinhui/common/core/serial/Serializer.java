package com.yejinhui.common.core.serial;

import javafx.beans.value.WritableStringValue;
import org.jboss.netty.buffer.ChannelBuffer;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 自定义序列化
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 17:29
 */
public abstract class Serializer {

    private static final Charset CHARSET = Charset.forName("UTF-8");

    protected ChannelBuffer writeBuffer;

    protected ChannelBuffer readBuffer;

    /**
     * 反序列化
     */
    protected abstract void read();

    /**
     * 序列化
     */
    protected abstract void write();

    /**
     * 从byte数组中获取数据
     *
     * @param bytes
     * @return
     */
    public Serializer readFromBytes(byte[] bytes) {
        readBuffer = BufferFactory.getBuffer(bytes);
        read();
        readBuffer.clear();
        return this;
    }

    /**
     * 从buffer获取数据
     *
     * @param channelBuffer
     */
    public void readFromBuffer(ChannelBuffer channelBuffer) {
        this.readBuffer = channelBuffer;
        read();
    }

    /**
     * 写入本地buffer
     *
     * @return
     */
    public ChannelBuffer writeToLocalBuffer() {
        writeBuffer = BufferFactory.getBuffer();
        write();
        return writeBuffer;
    }

    /**
     * 写入本地buffer
     *
     * @return
     */
    public ChannelBuffer writeToTargetBuffer(ChannelBuffer channelBuffer) {
        writeBuffer = channelBuffer;
        write();
        return writeBuffer;
    }

    /**
     * 返回buffer的字节数组
     *
     * @return
     */
    public byte[] getBytes() {
        writeToLocalBuffer();
        byte[] bytes = null;
        if (writeBuffer.writerIndex() == 0) {
            bytes = new byte[0];
        } else {
            bytes = new byte[writeBuffer.writerIndex()];
            writeBuffer.readBytes(bytes);
        }
        writeBuffer.clear();
        return bytes;
    }

    public byte readByte() {
        return readBuffer.readByte();
    }

    public short readShort() {
        return readBuffer.readShort();
    }

    public int readInt() {
        return readBuffer.readInt();
    }

    public long readLong() {
        return readBuffer.readLong();
    }

    public float readFloat() {
        return readBuffer.readFloat();
    }

    public double readDouble() {
        return readBuffer.readDouble();
    }

    public String readString() {
        int size = readBuffer.readShort();
        if (size <= 0) {
            return "";
        }
        byte[] bytes = new byte[size];
        readBuffer.readBytes(bytes);
        return new String(bytes);
    }

    public <T> List<T> readList(Class<T> clz) {
        List<T> list = new ArrayList<>(16);
        int size = readBuffer.readShort();
        for (int i = 0; i < size; i++) {
            list.add(read(clz));
        }
        return list;
    }

    public <K, V> Map<K, V> readMap(Class<K> clzKey, Class<V> clzValue) {
        Map<K, V> map = new HashMap<>(16);
        int size = readBuffer.readShort();
        for (int i = 0; i < size; i++) {
            map.put(read(clzKey), read(clzValue));
        }
        return map;
    }

    public <I> I read(Class<I> clz) {
        Object t = null;
        if (clz == int.class || clz == Integer.class) {
            t = this.readInt();
        } else if (clz == byte.class || clz == Byte.class) {
            t = this.readByte();
        } else if (clz == short.class || clz == Short.class) {
            t = this.readShort();
        } else if (clz == long.class || clz == Long.class) {
            t = this.readLong();
        } else if (clz == float.class || clz == Float.class) {
            t = this.readFloat();
        } else if (clz == double.class || clz == Double.class) {
            t = this.readDouble();
        } else if (clz == String.class) {
            t = this.readString();
        } else if (Serializer.class.isAssignableFrom(clz)) {
            try {
                byte hasObject = this.readBuffer.readByte();
                if (hasObject == 1) {
                    Serializer temp = (Serializer) clz.newInstance();
                    temp.readFromBuffer(this.readBuffer);
                    t = temp;
                } else {
                    t = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException(String.format("不支持的类型:[%s]", clz));
        }
        return (I) t;
    }

    public Serializer writeByte(Byte value) {
        writeBuffer.writeByte(value);
        return this;
    }

    public Serializer writeShort(Short value) {
        writeBuffer.writeShort(value);
        return this;
    }

    public Serializer writeInt(Integer value) {
        writeBuffer.writeInt(value);
        return this;
    }

    public Serializer writeLong(Long value) {
        writeBuffer.writeLong(value);
        return this;
    }

    public Serializer writeFloat(Float value) {
        writeBuffer.writeFloat(value);
        return this;
    }

    public Serializer writeDouble(Double value) {
        writeBuffer.writeDouble(value);
        return this;
    }

    public <T> Serializer writeList(List<T> list) {
        if (isEmpty(list)) {
            writeBuffer.writeShort(0);
            return this;
        }
        writeBuffer.writeShort(1);
        for (int i = 0; i < list.size(); i++) {
            writeObject(list.get(i));
        }
        return this;
    }

    public Serializer writeString(String value) {
        if (value == null || value.isEmpty()) {
            writeShort((short) 0);
            return this;
        }
        byte[] bytes = value.getBytes(CHARSET);
        writeBuffer.writeShort(bytes.length);
        writeBuffer.writeBytes(bytes);
        return this;
    }

    protected Serializer writeObject(Object object) {
        if (object == null) {
            writeByte((byte) 0);
        } else {
            if (object instanceof Integer) {
                writeInt((Integer) object);
            } else if (object instanceof Long) {
                writeLong((Long) object);
            } else if (object instanceof Short) {
                writeShort((Short) object);
            } else if (object instanceof Byte) {
                writeByte((Byte) object);
            } else if (object instanceof String) {
                writeString((String) object);
            } else if (object instanceof Serializer) {
                writeByte((byte) 1);
                Serializer serializer = (Serializer) object;
                serializer.writeToLocalBuffer();
            } else {
                throw new RuntimeException("不可序列化的类型：" + object.getClass());
            }
        }
        return this;
    }

    public <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.size() == 0;
    }

    public <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.size() == 0;
    }

}
