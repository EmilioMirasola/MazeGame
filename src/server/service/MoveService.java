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
            int xPos = Integer.parseInt(params[2]);
            int yPos = Integer.parseInt(params[3]);
            Optional<Direction> direction = Direction.get(params[4]);
            if (direction.isPresent()) {
                playerToUpdate.setXpos(xPos);
                playerToUpdate.setYpos(yPos);
                playerToUpdate.setDirection(direction.get());
            }
        }
    }
}
