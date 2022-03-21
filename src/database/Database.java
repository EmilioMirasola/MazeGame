package database;

import client.Player;

import java.util.ArrayList;
import java.util.List;

public class Database {
	private static final List<Player> players = new ArrayList<>();

	public static synchronized List<Player> getPlayers() {
		return players;
	}

	public static synchronized void  addPlayer(Player player) {
		players.add(player);
	}

}
