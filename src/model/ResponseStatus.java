package model;

import java.util.Arrays;
import java.util.Optional;

public enum ResponseStatus {
	OK("ok");

	public final String responseStatus;

	ResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public static Optional<ResponseStatus> get(String requestCommand) {
		return Arrays.stream(ResponseStatus.values())
				.filter(command -> command.responseStatus.equals(requestCommand))
				.findFirst();
	}
}
