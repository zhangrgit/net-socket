package com.rz.many_client;


import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
 
  
public class Client {  
	
   static ExecutorService exec = Executors.newCachedThreadPool();  
     
   public Client() {  
       try {  
           Socket socket = new Socket("127.0.0.1", 22222);  
           System.out.println(socket);
           exec.execute(new Sender(socket));     
       } catch (Exception e) {  
           e.printStackTrace();  
       }    
   }  
     
   class Sender implements Runnable {  
       Socket socket;   //客户端Socket对象  
       DataInputStream in; //输入输出流  
       DataOutputStream ou;  
       Scanner scanner;
       public Sender(Socket s){  
           socket = s;  
       }  
       public void run() {  
           try {   
               in = new DataInputStream(socket.getInputStream());//获取Socket中的出入输出流，以完成通信  
               ou = new DataOutputStream(socket.getOutputStream()); 
               System.out.println("please input...");
               scanner = new Scanner(System.in);
               String p = scanner.nextLine();
               ou.writeUTF(p);  //向服务器端发送信息  
               String receive = in.readUTF();  //接受服务器的反馈信息  
               in.close();  
               ou.close();  
               socket.close();  
               exec.shutdownNow(); //一旦执行，线程将从线程池消失  
               System.out.println(receive);  
                 
           }catch (Exception e) {  
              e.printStackTrace();  
           }  
       }  
   }  
     
   public static void main(String[] args) {  
       new Client();  
   }  
}  