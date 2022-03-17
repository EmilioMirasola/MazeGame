package server;

import client.Command;
import server.service.PlayerService;

import java.util.Optional;

public class Controller {
	PlayerService playerService = new PlayerService();

	public void handleRequest(String request) {
		String requestCommand = request.split(" ")[0].strip();
		System.out.println(requestCommand);

		Optional<Command> command = Command.get(requestCommand);

		if (command.isEmpty()) {
			return;
		}

		switch (command.get()) {
			case CONNECT:
				playerService.connectPlayer(request);
				break;
			default:
				throw new IllegalArgumentException("Unknown command");
		}

	}
}
