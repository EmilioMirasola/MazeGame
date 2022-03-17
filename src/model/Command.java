package model;

import java.util.Arrays;
import java.util.Optional;

public enum Command {
	CONNECT("connect"),
	MOVE("move");

    public final String command;

	Command(String command) {
		this.command = command;
	}

	public static Optional<Command> get(String requestCommand) {
		return Arrays.stream(Command.values())
				.filter(command -> command.command.equalsIgnoreCase(requestCommand))
				.findFirst();
	}

}
