package com.rz.single_client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	// 服务器
	public static final int PORT = 9999;// 端口

	public static void main(String[] arge) {
		    	
		        ServerSocket s = null;
				Socket socket = null;
				BufferedReader br = null;
				PrintWriter pw = null;
				try {
					//设定服务端的端口号
					s = new ServerSocket(PORT);
					System.out.println("ServerSocket Start:"+s);
					//等待请求,此方法会一直阻塞,直到获得请求才往下走
					socket = s.accept();
					System.out.println("Connection accept socket:"+socket);
					while(true){
						
                        if (socket.getInputStream().available()==0) {//客户端未有发送数据
                        	continue;
                        }
                        //用于接收客户端发来的请求
						br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						//用于发送返回信息,可以不需要装饰这么多io流使用缓冲流时发送数据要注意调用.flush()方法
						pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
						String str = br.readLine();
						if(str.toUpperCase().equals("END")){//客户端输入结束标志时，服务端也随之结束(也可不结束)
							break;
						}
						System.out.println("Client Socket Message:"+str);
						pw.println("Message Received");
						pw.flush();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					System.out.println("Server Close.....");
					try {
						br.close();
						pw.close();
						socket.close();
						s.close();
					} catch (Exception e2) {
						
					}
				}
	}
}
