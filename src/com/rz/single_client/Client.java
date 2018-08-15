package com.rz.single_client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	public static void main(String[] args){
			Socket socket = null;
			BufferedReader br = null;
			PrintWriter pw = null;
			Scanner scanner = null;
			try {
				//客户端socket指定服务器的地址和端口号
				socket = new Socket("127.0.0.1", Server.PORT);
				System.out.println("Socket=" + socket);
				//同服务器原理一样
				br = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream())));
				
				while (true) {
					scanner = new Scanner(System.in);
	                String p = scanner.nextLine();
	                if(p.toUpperCase().equals("END")){
	                	break;
	                }
	                pw.println(p);
					pw.flush();
	                String str = br.readLine();
					System.out.println(str);
				}
				pw.println("END");
				pw.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					System.out.println("close......");
					br.close();
					pw.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}


}
