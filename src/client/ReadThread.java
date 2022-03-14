package client;

import server.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread {
	private Socket connectionSocket;
	private Controller controller;

	public ReadThread(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
		this.controller = new Controller();
	}

	public void run() {
		while (true) {
			try {
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				String clientSentence = inFromClient.readLine();

				controller.handleRequest(clientSentence);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}


