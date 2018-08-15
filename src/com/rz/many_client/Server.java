package com.rz.many_client;


import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.IOException;  
import java.net.ServerSocket;  
import java.net.Socket;  
import java.util.ArrayList;  
import java.util.List;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;

import com.rz.util.KillServer;  


public class Server {  
	
  ServerSocket server; //其方法accept 用来监听客户端的链接  
  Socket socket;  
  static List<Socket> list = new ArrayList<Socket>();//保存链接的Socket对象  
  ExecutorService exec;  
  public Server(){  
      try {  
    	  //启动监听，端口为22222  
          server = new ServerSocket(22222);
          System.out.println(server);
          //缓存型池子，先查看池中有没有以前建立的线程，如果有，就reuse.如果没有，就建一个新的线程加入池中
          exec = Executors.newCachedThreadPool();  
          while(true) {  
              socket = server.accept(); 
//              System.out.println(socket.isConnected()+","+socket.isClosed());
              list.add(socket);  
              System.out.println("已建立客户端连接数->"+list.size());
              exec.execute(new Tast(socket)); 
              System.out.println("---");
          }  
      } catch (IOException e) {  
          e.printStackTrace();  
      }    
  }  
  class Tast implements Runnable {  
	  
      Socket socket;      //服务器端Socket对象  
      DataInputStream in; //数据输入流
      DataOutputStream ou; //数据输出流 
      public Tast(Socket socket) throws IOException{  
          this.socket = socket;  
          in = new DataInputStream(socket.getInputStream()); //获取Socket中的出入输出流，以完成通信  
          ou = new DataOutputStream(socket.getOutputStream());  
      }  
      public void run() {  
          try {  
                
              String receive = in.readUTF();  //读取客户端信息  
              ou.writeUTF("server已收到信息为："+receive);//向客户端发送信息  
              ou.close();  
              in.close();  
              socket.close();  
              System.out.println(receive);     
                
          } catch (Exception e) {  
              e.printStackTrace();  
          }  
      }  
  }  
  public static void  test(){
	 
	  new Thread(new Runnable() {
			public void run() {
				 new Server();  
			}
		}).start();
      new Thread(new Runnable() {
		public void run() {
			 while (true) {
					System.out.println("a");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
		}
	}).start();
  }
  public static void main(String[] args) {  
      KillServer.killPort(new String[]{"22222"});	
      test();
  }  
}  