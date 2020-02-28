package com.yejinhui.serial.core;

import org.jboss.netty.buffer.ChannelBuffer;

import java.nio.charset.Charset;
import java.util.*;

/**
 * 自定义序列化接口
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/24 22:54
 */
public abstract class Serializer {

    public static final Charset CHARSET = Charset.forName("UTF-8");

    protected ChannelBuffer writeBuffer;

    protected ChannelBuffer readBuffer;

    /**
     * 反序列化具体实现
     */
    protected abstract void read();

    /**
     * 序列化具体实现
     */
    protected abstract void write();

    /**
     * 从byte数组中读取数据
     *
     * @param bytes 读取的数据
     * @return
     */
    public Serializer readFromBytes(byte[] bytes) {
        readBuffer = BufferFactory.getBuffer(bytes);
        read();
        readBuffer.clear();
        return this;
    }

    /**
     * 从buff获取数据
     *
     * @param readBuffer
     */
    public void readFromBuffer(ChannelBuffer readBuffer) {
        this.readBuffer = readBuffer;
        read();
    }

    /**
     * 写入本地buff
     *
     * @return
     */
    public ChannelBuffer writeToLocalBuff() {
        writeBuffer = BufferFactory.getBuffer();
        write();
        return writeBuffer;
    }

    /**
     * 写入目标buff
     *
     * @param buffer
     * @return
     */
    public ChannelBuffer writeToTargetBuff(ChannelBuffer buffer) {
        writeBuffer = buffer;
        write();
        return writeBuffer;
    }

    /**
     * 返回buffer数组
     *
     * @return
     */
    public byte[] getBytes() {
        writeToLocalBuff();
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
        return new String(bytes, CHARSET);
    }

    public <T> List<T> readList(Class<T> clz) {
        List<T> list = new ArrayList<>();
        int size = readBuffer.readShort();
        for (int i = 0; i < size; i++) {
            list.add(read(clz));
        }
        return list;
    }

    public <K, V> Map<K, V> readMap(Class<K> keyClz, Class<V> valueClz) {
        Map<K, V> map = new HashMap<>();
        int size = readBuffer.readShort();
        for (int i = 0; i < size; i++) {
            K k = read(keyClz);
            V v = read(valueClz);
            map.put(k, v);
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
                byte hasObjecct = this.readBuffer.readByte();
                if (hasObjecct == 1) {
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
            throw new RuntimeException(String.format("不支持类型：[%s]", clz));
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
            writeBuffer.writeShort((short) 0);
            return this;
        }
        writeBuffer.writeShort(list.size());
        for (int i = 0; i < list.size(); i++) {
            writeObject(list.get(i));
        }
        return this;
    }

    public <K, V> Serializer writeMap(Map<K, V> map) {
        if (isEmpty(map)) {
            writeBuffer.writeShort((short) 0);
            return this;
        }
        short len = (short) map.size();
        writeBuffer.writeShort(len);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            writeObject(entry.getKey());
            writeObject(entry.getValue());
        }
        return this;
    }

    public Serializer writeString(String value) {
        if (value == null || value.isEmpty()) {
            writeShort((short) 0);
            return this;
        }
        byte[] data = value.getBytes(CHARSET);
        short len = (short) data.length;
        writeBuffer.writeShort(len);
        writeBuffer.writeBytes(data);
        return this;
    }

    public Serializer writeObject(Object object) {
        if (object == null) {
            writeByte((byte) 0);
        } else {
            if (object instanceof Integer) {
                writeInt((Integer) object);
                return this;
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
                Serializer temp = (Serializer) object;
                temp.writeToTargetBuff(writeBuffer);
                return this;
            }
            throw new RuntimeException("不可序列化的类型：" + object.getClass());
        }
        return this;
    }

    public <T> boolean isEmpty(Collection<T> c) {
        return c == null || c.size() == 0;
    }

    public <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.size() == 0;
    }

}
