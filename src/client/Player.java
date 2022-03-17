package client;

import lombok.Getter;
import lombok.Setter;
import model.Direction;

@Getter
@Setter
public class Player {
	String name;
	int xpos;
	int ypos;
	int point;
	Direction direction;

	public Player(String name, int xpos, int ypos, Direction direction) {
		this.name = name;
		this.xpos = xpos;
		this.ypos = ypos;
		this.direction = direction;
		this.point = 0;
	}

	public String toString() {
		return (
				"player" + " " +
						this.getName() + " " +
						this.getXpos() + " " +
						this.getYpos() + " " +
						this.getDirection() + " " +
						this.getPoint() + " "
						+ ","
				);
	}
}
