package com.rz.client_to_client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    Socket socket = null;
    InputStreamReader input = null;
    InputStream in = null;
    OutputStream out = null;

    /** 
     * @param args 
     */
    public void socketStart() {
        try {
            socket = new Socket("127.0.0.1", 8888);
            System.out.println(socket);
            // 接受返回数据
            new Thread() {
                public void run() {
                    try {
                        while (true) {
                            in = socket.getInputStream();
                            ObjectInputStream ois = new ObjectInputStream(in);
                            Message msg = (Message) ois.readObject();
                            System.out.println("返回数据：" + msg.getMsg());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            out = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            while (true) {
                input = new InputStreamReader(System.in);
                String msg = new BufferedReader(input).readLine();
                Message message = new Message();
                message.setId(2);
                message.setMsg(msg);
                oos.writeObject(message);
                System.out.println("已发送");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流和连接
            try {
                in.close();
                out.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Client().socketStart();
    }
}