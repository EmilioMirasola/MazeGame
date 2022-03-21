package server;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Getter
public class ServerReadThread extends Thread {
	private Socket connectionSocket;
	private Controller controller;
	private DataOutputStream dataOutputStream;

	public ServerReadThread(Socket connectionSocket) throws IOException {
		this.connectionSocket = connectionSocket;
		this.controller = new Controller();
		this.dataOutputStream = new DataOutputStream(connectionSocket.getOutputStream());
	}

	public void run() {
		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			while (true) {
				String clientSentence = inFromClient.readLine();
				controller.handleRequest(clientSentence);
				TCPServer.pushDataToClients();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


