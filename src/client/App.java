package client;

import javafx.application.Application;
import model.Direction;
import threads.ReadThread;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class App {
	public static String name;
	public static ArrayList<Player> playersFromServer = new ArrayList<>();

	private static Socket connectionSocket;
	private static ReadThread rt;

	public static void main(String[] args) throws IOException {

		//connection establishing
		connectionSocket = new Socket("localhost", 6789);
		rt = new ReadThread(connectionSocket);
		rt.start();

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		String send = inFromUser.readLine();
		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		outToClient.writeBytes(send + "\n");

		Application.launch(GUI.class);
		GUI.setMe(new Player(name, 14,15, Direction.UP));
		GUI.setPlayers(playersFromServer);


	}
}
