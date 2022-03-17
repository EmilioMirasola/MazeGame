package server;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Getter
public class ServerReadThread extends Thread {
	private Socket connectionSocket;
	private Controller controller;

	public ServerReadThread(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
		this.controller = new Controller();
	}

	public void run() {
		while (true) {
			try {
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				String clientSentence = inFromClient.readLine();
				controller.handleRequest(clientSentence);
				TCPServer.pushDataToClients();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}


