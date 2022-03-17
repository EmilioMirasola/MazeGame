package model;

import java.util.Arrays;
import java.util.Optional;

public enum Command {
	CONNECT("connect");

	public final String command;

	Command(String command) {
		this.command = command;
	}

	public static Optional<Command> get(String requestCommand) {
		return Arrays.stream(Command.values())
				.filter(command -> command.command.equals(requestCommand))
				.findFirst();
	}
}
