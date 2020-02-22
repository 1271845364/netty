package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 此时的NIO是单线程工作的，单线程handler的
 * NIO服务端，NIO中，每个线程可以处理多个channel(异步)
 * 线程发起IO请求，立即返回；内核在做好IO操作的准备之后，通过调用注册的回调函数通知线程做IO操作，
 * 线程开始阻塞，直到操作完成
 *
 * @author ye.jinhui
 * @description
 * @program netty
 * @create 2018/12/21 14:03
 */
public class NIOServer {

    //通道管理器(多路复用器)
    private Selector selector;

    ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

    /**
     * 获取ServerSocket通道，并对该通道做一些初始化工作
     *
     * @param port 绑定的端口号
     * @throws IOException
     */
    public void initServer(int port) throws IOException {
        //获取一个ServerSocket通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //设置该通道为非阻塞的
        serverSocketChannel.configureBlocking(false);

        //绑定端口
        serverSocketChannel.bind(new InetSocketAddress(port));

        //获取选择器
        selector = Selector.open();

        //将通道注册到选择器上，并为该通道注册SelectionKey.OP_ACCEPT事件，注册该事件后，
        //当该事件到达的时候，selector.select()会返回，如果该事件没到达selector.select()会一直阻塞
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理
     */
    public void listen() throws IOException {
        System.out.println("服务端启动成功！");
        //轮询selector，当注册的事件到达时，方法返回；否则，该方法会一直阻塞
        while (selector.select() > 0) {
            //获取selector中选中的项的迭代器，选中的项为注册的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                //删除已选的key，防止重复处理
                iterator.remove();
                //处理请求

                //多线程处理，其实handler方法还没有执行完毕，导致selector.select()>0，继续执行
                // ，selector.selectedKeys()返回还是有东西，handlerAccept(selectionKey);
                //serverSocketChannel.accept();就获取不到了，返回null就报错空指针异常了
//                newCachedThreadPool.execute(()-> {
                    try {
                        handler(selectionKey);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                });
            }
        }
    }

    /**
     * 处理请求
     *
     * @param selectionKey
     */
    private void handler(SelectionKey selectionKey) throws IOException {
//        System.out.println("isWritable：" + selectionKey.isWritable());
        //客户端请求是链接请求
        if (selectionKey.isAcceptable()) {
            //处理链接请求
            handlerAccept(selectionKey);
        } else if (selectionKey.isReadable()) {
            //获得可读的事件
            handlerRead(selectionKey);
        }
    }

    /**
     * 处理读请求事件
     *
     * @param selectionKey
     */
    private void handlerRead(SelectionKey selectionKey) throws IOException {
        //服务端可读取消息：得到事件发生的Socket通道
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        //创建存放数据的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将通道中的数据读到缓冲区
        int read = socketChannel.read(byteBuffer);
        if (read > 0) {
            byte[] array = byteBuffer.array();
            String msg = new String(array).trim();
            System.out.println(socketChannel + "服务端收到：" + msg);

            //回写数据
            ByteBuffer outBuffer = ByteBuffer.wrap("好的".getBytes());
            //通过通道回写数据
            socketChannel.write(outBuffer);
        } else {
            System.out.println("客户端关闭：" + socketChannel);
            selectionKey.cancel();
        }
    }

    /**
     * 处理链接请求
     *
     * @param selectionKey
     */
    private void handlerAccept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        //获取客户端链接通道
        SocketChannel socketChannel = serverSocketChannel.accept();
        //设置成非阻塞
        socketChannel.configureBlocking(false);
        //在这可以给客户端发送消息
        System.out.println("新的客户端链接");
        //在和客户端链接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限
        socketChannel.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }

    //启动服务端测试
    public static void main(String[] args) throws IOException {
        NIOServer nioServer = new NIOServer();
        nioServer.initServer(10101);
        nioServer.listen();
    }


}