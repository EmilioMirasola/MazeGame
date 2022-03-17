package server;

import client.Player;
import database.Database;
import threads.ReadThread;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPServer {
	public static ArrayList<ServerReadThread> serverReadThreads = new ArrayList<>();


	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(6789);
		while (true) {
			Socket accept = serverSocket.accept();

			ServerReadThread rt = new ServerReadThread(accept);
			serverReadThreads.add(rt);

			rt.start();
		}

	}

	public static void pushDataToClients() throws IOException {
		String game = "";
		List<Player> players = Database.getPlayers();

		for (Player p : players) {
			game += p.toString();
		}
		game += "\n";

		for (ServerReadThread serverReadThread : serverReadThreads) {
			DataOutputStream dataOutputStream = new DataOutputStream(serverReadThread.getConnectionSocket().getOutputStream());
			{
				dataOutputStream.writeBytes(game);
			}
		}
	}
}
