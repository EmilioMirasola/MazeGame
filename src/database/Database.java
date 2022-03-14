package database;

import game2022.Player;

import java.util.ArrayList;
import java.util.List;

public class Database {
	private static final List<Player> players = new ArrayList<>();

	public static List<Player> getPlayers() {
		return players;
	}

	public static void addPlayer(Player player) {
		players.add(player);
	}

	public static void removePlayer(Player player) {
		players.remove(player);
	}
}
