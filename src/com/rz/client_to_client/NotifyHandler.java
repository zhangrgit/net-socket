package com.rz.client_to_client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class NotifyHandler extends Thread {
    Socket socket = null;
    InputStream in = null;
    Map<Integer, Socket> sessionMap = null;

    public NotifyHandler(Socket socket, Map<Integer, Socket> sessionMap) {
        this.socket = socket;
        this.sessionMap = sessionMap;
    }

    public void run() {
        try {
            in = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(in);
            // 实现一次连接多次通话
            while (true) {
                try {
                    Message msg = (Message) ois.readObject();
                    System.out.println("消息接受对象：客户端" + msg.getId() + "，消息内容：" + msg.getMsg());
                    // 发送数据
                    try {
                        Socket targetSocket = sessionMap.get(msg.getId());
                        OutputStream out = targetSocket.getOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(out);
                        oos.writeObject(msg);
                        System.out.println("服务端已转发");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (socket.isClosed()) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        socket.close();
                        break;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}