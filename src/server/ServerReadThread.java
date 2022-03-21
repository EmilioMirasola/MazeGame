package server;

import client.Player;
import database.Database;
import lombok.Getter;
import model.Command;
import model.Direction;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

@Getter
public class ServerReadThread extends Thread {
	private final Socket connectionSocket;
	private final DataOutputStream dataOutputStream;

	public ServerReadThread(Socket connectionSocket) throws IOException {
		this.connectionSocket = connectionSocket;
		this.dataOutputStream = new DataOutputStream(connectionSocket.getOutputStream());
	}

	public void run() {
		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			while (true) {
				String clientSentence = inFromClient.readLine();
				handleRequest(clientSentence);
				TCPServer.pushDataToClients();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleRequest(String request) {
		String requestCommand = request.split(" ")[0].strip();
		System.out.println(requestCommand);

		Optional<Command> command = Command.get(requestCommand);

		if (command.isEmpty()) {
			return;
		}

		switch (command.get()) {
			case CONNECT:
				connectPlayer(request);
				break;
			case MOVE:
				movePlayer(request);
				break;
			default:
				throw new IllegalArgumentException("Unknown command");
		}
	}

	private void connectPlayer(String requestCommand) {
		int lastXPosition = 1;
		int lastYPosition = 1;

		String playerName = requestCommand.split(" ")[1];
		Player player = new Player(playerName, lastXPosition, lastYPosition, Direction.UP);
		Database.addPlayer(player);
	}

	private void movePlayer(String request) {
		String[] params = request.split(" ");
		String playerName = params[1];
		Player playerToUpdate = null;
		List<Player> players = Database.getPlayers();
		for (Player p : players) {
			if (p.getName().equals(playerName)) {
				playerToUpdate = p;
			}
		}
		if (playerToUpdate != null) {
			Optional<Direction> direction = Direction.get(params[2]);
			if (direction.isPresent()) {
				playerToUpdate.setDirection(direction.get());
			}
		}
	}
}


