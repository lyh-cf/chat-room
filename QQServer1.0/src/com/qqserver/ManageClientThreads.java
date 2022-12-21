package com.qqserver;

import com.qqserver.ServerConnectClientThread;
import java.util.concurrent.ConcurrentHashMap;

public class ManageClientThreads {
    //创建一个集合，存放多个用户，如果是这些用户登录，就认为是合法
    //这里我们也可以使用 ConcurrentHashMap, 可以处理并发的集合
    //HashMap 没有处理线程安全，因此在多线程情况下是不安全
    //ConcurrentHashMap 处理的线程安全,即线程同步处理, 在多线程情况下是安全
    private static ConcurrentHashMap<String, ServerConnectClientThread> Onlineuser = new ConcurrentHashMap<>();
    //返回 Onlineuser
    public static ConcurrentHashMap<String, ServerConnectClientThread> getOnlineuser() {
        return Onlineuser;
    }

    //添加线程对象到 hm 集合
    public static void addClientThread(String userId, ServerConnectClientThread serverConnectClientThread) {

        Onlineuser.put(userId, serverConnectClientThread);

    }

    //根据userId 返回ServerConnectClientThread线程
    public static ServerConnectClientThread getServerConnectClientThread(String userId) {
        return Onlineuser.get(userId);
    }

    //增加一个方法，从集合中，移除某个线程对象
    public static void removeServerConnectClientThread(String userId) {
        Onlineuser.remove(userId);
    }

}
