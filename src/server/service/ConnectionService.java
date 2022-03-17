package server.service;

import model.Direction;
import database.Database;
import client.Player;

public class ConnectionService {

	public void connectPlayer(String requestCommand) {
		String playerName = requestCommand.split(" ")[1];
		Player player = new Player(playerName, 9, 4, Direction.DOWN);
		Database.addPlayer(player);
	}
}
