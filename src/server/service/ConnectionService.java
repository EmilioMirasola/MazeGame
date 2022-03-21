package server.service;

import model.Direction;
import database.Database;
import client.Player;

public class ConnectionService {
	private int lastXPosition = 1;
	private int lastYPosition = 1;

	public void connectPlayer(String requestCommand) {
		String playerName = requestCommand.split(" ")[1];
		Player player = new Player(playerName, lastXPosition, lastYPosition, Direction.UP);
//		lastXPosition++;
//		lastYPosition++;
		Database.addPlayer(player);
	}
}
