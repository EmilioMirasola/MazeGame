package server.service;

import client.Player;
import database.Database;
import model.Direction;

import java.util.List;
import java.util.Optional;

public class MoveService {
    public void movePlayer(String request) {
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
