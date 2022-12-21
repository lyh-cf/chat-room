package com.qqserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * 这是服务器, 在监听4656，等待客户端的连接，并保持通信
 */
public class QQServer {
    private static ServerSocket serverSocket;
    static {
        try {
            serverSocket = new ServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static Socket socket;
    public static void main(String[] args) {
        try {
           serverSocket.bind(new InetSocketAddress(2341));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //利用线程池优化
        //创建线程池，newFixedThreadPool里的参数为线程池大小
        ExecutorService service = Executors.newFixedThreadPool(50);
        System.out.println("==========服务端启动==========");
        // a.定义一个死循环由主线程负责不断的接收客户端的Socket管道连接。
        while (true){
            // 2、接收每一个客户端的Socket管道
            try {
              socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(socket.getRemoteSocketAddress()+ "上线了！");
            // 把当前客户端登录后，再把管道Socket加入到在线集合中去
            // 3、开始创建独立子线程处理socket
            ServerConnectClientThread serverConnectClientThread=new ServerConnectClientThread(socket);
            ServerConnectClientThread.serverConnectClientThread=serverConnectClientThread;
            service.execute(serverConnectClientThread);
        }

    }
}
