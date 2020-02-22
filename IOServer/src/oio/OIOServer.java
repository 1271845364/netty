package oio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 传统Socket服务端，OIO中，每个线程只能处理一个channel(同步的，该线程和该channel绑定)
 * 线程发起IO请求，不管内核是否准备好IO操作，从发起请求，线程一直阻塞，直至操作完成
 *
 * @author ye.jinhui
 * @description
 * @program netty
 * @create 2018/12/21 14:03
 */
public class OIOServer {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        //线程池
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        //创建Socket服务，监听10101端口
        ServerSocket serverSocket = new ServerSocket(10101);
        System.out.println("服务端启动！");

        //循环监听客户端链接
        while (true) {
            //获取一个套接字(阻塞)
            final Socket socket = serverSocket.accept();
            System.out.println("来一个新客户端！");

            //将客户端的链接交给线程池处理
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //业务处理
                    handler(socket);
                }
            });
        }
    }

    /**
     * 读取数据
     *
     * @param socket
     */
    private static void handler(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            //读取数据，阻塞
            int len = 0;
            while((len = inputStream.read(bytes)) != -1) {
                System.out.println(new String(bytes,0,len));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                System.out.println("socket关闭：" + socket);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
