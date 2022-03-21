package server;

import model.Command;
import server.service.ConnectionService;
import server.service.MoveService;

import java.util.Optional;

public class Controller {
	ConnectionService connectionService = new ConnectionService();
	MoveService moveService = new MoveService();



	public void handleRequest(String request) {
		String requestCommand = request.split(" ")[0].strip();
		System.out.println(requestCommand);

		Optional<Command> command = Command.get(requestCommand);

		if (command.isEmpty()) {
			return;
		}

		switch (command.get()) {
			case CONNECT:
				connectionService.connectPlayer(request);
				break;
			case MOVE:
				moveService.movePlayer(request);
				break;
			default:
				throw new IllegalArgumentException("Unknown command");
		}


	}



























}
