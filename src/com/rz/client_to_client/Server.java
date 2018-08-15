package com.rz.client_to_client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
	
	 // socket服务
    ServerSocket serverSocket = null;
    // 用来存放已连接的客户端的socket会话
    static Map<Integer, Socket> sessionMap = new HashMap<Integer, Socket>();

    public void socket() {
        try {
            // 创建serverSocket，绑定端口为8888
            serverSocket = new ServerSocket(8888);
            System.out.println(serverSocket);
            // 客户端编号
            int i = 1;
            // 实现多个客户端连接
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("客户端" + i + "连接成功。。。");
                if (socket != null) {
                    // 将socket放入map，key为客户端编号
                    sessionMap.put(i, socket);
                    // 开启线程处理本次会话
                    Thread thread = new Thread(new NotifyHandler(socket, sessionMap));
                    thread.setDaemon(true);
                    thread.start();
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
    public static void main(String[] args) throws IOException {
        new Server().socket();
    }

}
