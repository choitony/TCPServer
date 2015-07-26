package com.choi.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Choi
 * 
 * @version 1.0
 * 
 * */

public class TCPServer implements Cloneable, Runnable {

	Thread runner = null;
	protected ServerSocket server = null;
	Socket data = null;
	volatile boolean shouldStop = false;

	public synchronized void startServer(int port) throws IOException {
		if (runner == null) {
			server = new ServerSocket(port);
			runner = new Thread(this);
			runner.start();
		}
	}

	public synchronized void stopServer() {
		if (server != null) {
			shouldStop = true;
			runner.interrupt();
			runner = null;
			try {
				server.close();
			} catch (Exception e) {
			}
			server = null;
		}
	}

	@Override
	public void run() {
		if (server != null) {
			while (!shouldStop) {
				try {
					Socket dataSocket = server.accept();
					System.out.println("建立一个连接");
					TCPServer newServer = (TCPServer) clone();
					newServer.server = null;
					newServer.data = dataSocket;
					newServer.runner = new Thread(newServer);
					newServer.runner.start();
				} catch (Exception e) {
				}
			}
		} else {
			System.out.println("新连接可用");
			run(data);
		}
	}

	public void run(Socket dataSocket) {
		System.out.println("空处理");
	}
}
