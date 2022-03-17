package client;

import model.Direction;

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

	public String getName() {
		return name;
	}

	public int getXpos() {
		return xpos;
	}

	public int getYpos() {
		return ypos;
	}

	public int getPoint() {
		return point;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setXpos(int xpos) {
		this.xpos = xpos;
	}

	public void setYpos(int ypos) {
		this.ypos = ypos;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
