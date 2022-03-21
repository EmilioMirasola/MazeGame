package server;

import client.Player;
import database.Database;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		List<Player> players = Database.getPlayers();

		Optional<Player> playerWhoMoved = players
				.stream()
				.filter(player -> player.getDirection() != null)
				.findFirst();

		if (playerWhoMoved.isPresent()) {
			for (ServerReadThread serverReadThread : serverReadThreads) {
				DataOutputStream dataOutputStream = new DataOutputStream(serverReadThread.getConnectionSocket().getOutputStream());
				{
					dataOutputStream.writeBytes(playerWhoMoved.get().toString() + "\n");
				}
			}
			playerWhoMoved.get().setDirection(null);
		}
	}
}
