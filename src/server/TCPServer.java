package server;

import client.ReadThread;
import client.WriteThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(6789);
		Socket accept = serverSocket.accept();

		ReadThread rt = new ReadThread(accept);
		WriteThread wt = new WriteThread(accept);

		rt.start();
		wt.start();
	}
}
