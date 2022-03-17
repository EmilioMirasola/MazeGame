package model;

import java.util.Arrays;
import java.util.Optional;

public enum Direction {
	UP("up"), DOWN("down"), LEFT("left"), RIGHT("right");

	private String direction;

	Direction(String direction) {
		this.direction = direction;
	}

	public static Optional<Direction> get(String direction) {
		return Arrays.stream(Direction.values())
				.filter(dir -> dir.direction.equals(direction))
				.findFirst();
	}
}
