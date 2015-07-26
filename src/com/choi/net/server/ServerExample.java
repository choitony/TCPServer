package com.choi.net.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerExample extends TCPServer {

	public void run(Socket dataSocket) {
		try {
			PrintWriter say = new PrintWriter(dataSocket.getOutputStream());
			BufferedReader hear = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
			BufferedReader sayLine = new BufferedReader(new InputStreamReader(System.in));
			String line = hear.readLine();
			System.out.println("client"+line);
			while(!line.equals("bye")){
				say.println(sayLine.readLine());
				say.flush();
				System.out.println("client:"+hear.readLine());
			}
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) throws Exception {
		ServerExample server = new ServerExample();
		server.startServer(4000);
		Thread.sleep(50000);
		server.stopServer();
	}
	
}
