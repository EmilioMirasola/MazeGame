package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import model.Direction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GUI extends Application {

	public static final int size = 20;
	public static final int scene_height = size * 20 + 100;
	public static final int scene_width = size * 20 + 200;

	private static Socket connectionSocket;

	static {
		try {
			connectionSocket = new Socket("10.10.138.84", 6789);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ServerRequest serverRequest = new ServerRequest(connectionSocket);

	public static Image image_floor;
	public static Image image_wall;
	public static Image hero_right, hero_left, hero_up, hero_down;

	public static Player me;
	public static List<Player> players = new ArrayList<Player>();
	public static boolean playersInitialized = false;

	private Label[][] fields;
	private TextArea scoreList;

	private String[] board = {    // 20x20
			"wwwwwwwwwwwwwwwwwwww",
			"w        ww        w",
			"w w  w  www w  w  ww",
			"w w  w   ww w  w  ww",
			"w  w               w",
			"w w w w w w w  w  ww",
			"w w     www w  w  ww",
			"w w     w w w  w  ww",
			"w   w w  w  w  w   w",
			"w     w  w  w  w   w",
			"w ww ww        w  ww",
			"w  w w    w    w  ww",
			"w        ww w  w  ww",
			"w         w w  w  ww",
			"w        w     w  ww",
			"w  w              ww",
			"w  w www  w w  ww ww",
			"w w      ww w     ww",
			"w   w   ww  w      w",
			"wwwwwwwwwwwwwwwwwwww"
	};

	public GUI() {
	}


	// -------------------------------------------
	// | Maze: (0,0)              | Score: (1,0) |
	// |-----------------------------------------|
	// | boardGrid (0,1)          | scorelist    |
	// |                          | (1,1)        |
	// -------------------------------------------

	@Override
	public void start(Stage primaryStage) {
		ReadFromServer readFromServer = new ReadFromServer();
		readFromServer.start();
		String playerName = "LarsAllan";
		serverRequest.connect(playerName);
		me = new Player(playerName, 1, 1, Direction.RIGHT);
		try {
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(0, 10, 0, 10));

			Text mazeLabel = new Text("Maze:");
			mazeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

			Text scoreLabel = new Text("Score:");
			scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

			scoreList = new TextArea();

			GridPane boardGrid = new GridPane();

			image_wall = new Image(getClass().getResourceAsStream("Image/wall4.png"), size, size, false, false);
			image_floor = new Image(getClass().getResourceAsStream("Image/floor1.png"), size, size, false, false);

			hero_right = new Image(getClass().getResourceAsStream("Image/heroRight.png"), size, size, false, false);
			hero_left = new Image(getClass().getResourceAsStream("Image/heroLeft.png"), size, size, false, false);
			hero_up = new Image(getClass().getResourceAsStream("Image/heroUp.png"), size, size, false, false);
			hero_down = new Image(getClass().getResourceAsStream("Image/heroDown.png"), size, size, false, false);

			fields = new Label[20][20];
			for (int j = 0; j < 20; j++) {
				for (int i = 0; i < 20; i++) {
					switch (board[j].charAt(i)) {
						case 'w':
							fields[i][j] = new Label("", new ImageView(image_wall));
							break;
						case ' ':
							fields[i][j] = new Label("", new ImageView(image_floor));
							break;
						default:
							throw new Exception("Illegal field value: " + board[j].charAt(i));
					}
					boardGrid.add(fields[i][j], i, j);
				}
			}
			scoreList.setEditable(false);


			grid.add(mazeLabel, 0, 0);
			grid.add(scoreLabel, 1, 0);
			grid.add(boardGrid, 0, 1);
			grid.add(scoreList, 1, 1);

			Scene scene = new Scene(grid, scene_width, scene_height);
			primaryStage.setScene(scene);
			primaryStage.show();

			scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
				//Check if valid move
				try {
					switch (event.getCode()) {
						case UP:
							serverRequest.move(me.getName(), "up");
							break;
						case DOWN:
							serverRequest.move(me.getName(), "down");
							break;
						case LEFT:
							serverRequest.move(me.getName(), "left");
							break;
						case RIGHT:
							serverRequest.move(me.getName(), "right");
							break;
						default:
							break;
					}
				} catch (Error e) {
					System.out.println(e.getMessage());
				}
			});

			scoreList.setText(getScoreList());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playerMoved(String playerName, int delta_x, int delta_y, Direction direction) {
		Player playerToMove = null;
		for (Player p : players) {
			if (p.getName().equals(playerName)) {
				playerToMove = p;
				break;
			}
		}
		if (playerToMove == null) {
			playerToMove = new Player(playerName, 1, 1, direction);
			players.add(playerToMove);
		}

		playerToMove.direction = direction;
		int x = playerToMove.getXpos(), y = playerToMove.getYpos();

		if (board[y + delta_y].charAt(x + delta_x) == 'w') {
			playerToMove.setPoint(playerToMove.getPoint() - 1);
		} else {
			Player p = getPlayerAt(x + delta_x, y + delta_y);
			if (p != null) {
				playerToMove.setPoint(playerToMove.getPoint() + 10);
				p.setPoint(p.getPoint() - 10);
			} else {
				playerToMove.setPoint(playerToMove.getPoint() + 1);

				fields[x][y].setGraphic(new ImageView(image_floor));
				x += delta_x;
				y += delta_y;

				if (direction == Direction.RIGHT) {
					fields[x][y].setGraphic(new ImageView(hero_right));
				}
				;
				if (direction == Direction.LEFT) {
					fields[x][y].setGraphic(new ImageView(hero_left));
				}
				;
				if (direction == Direction.UP) {
					fields[x][y].setGraphic(new ImageView(hero_up));
				}
				;
				if (direction == Direction.DOWN) {
					fields[x][y].setGraphic(new ImageView(hero_down));
				}
				;

				playerToMove.setXpos(x);
				playerToMove.setYpos(y);
			}
		}
		scoreList.setText(getScoreList());

	}

	public String getScoreList() {
		StringBuffer b = new StringBuffer(100);
		for (Player p : players) {
//			b.append(p + "\r\n");
			b.append(p.getName() + " " + p.getPoint() + "\r\n");
		}
		return b.toString();
	}

	public Player getPlayerAt(int x, int y) {
		for (Player p : players) {
			if (p.getXpos() == x && p.getYpos() == y) {
				return p;
			}
		}
		return null;
	}


	class ReadFromServer extends Thread {
		@SneakyThrows
		public void run() {
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			while (true) {
				String playerFromServer = inFromServer.readLine();
				String[] gameArray = playerFromServer.split(",");

				//Bruges til at initialisere players i starten af spillet
				if (!playersInitialized) {
					for (int i = 0; i < gameArray.length; i++) {

						String[] playerArray = gameArray[i].split(" ");
						Optional<Direction> direction = Direction.get(playerArray[2]);
						Player player = new Player(playerArray[1],
								1,
								1,
								direction.get());
						players.add(player);

					}
					playersInitialized = true;
				}
				//----------------------------------------------------------


				for (int i = 0; i < gameArray.length; i++) {

					String[] playerArray = gameArray[i].split(" ");
					Optional<Direction> direction = Direction.get(playerArray[2]);
					int deltaX = 0;
					int deltaY = 0;

					if (direction.equals(Optional.of(Direction.UP))) {
						deltaX = 0;
						deltaY = -1;
					} else if (direction.equals(Optional.of(Direction.DOWN))) {
						deltaX = 0;
						deltaY = 1;

					} else if (direction.equals(Optional.of(Direction.LEFT))) {
						deltaX = -1;
						deltaY = 0;

					} else if (direction.equals(Optional.of(Direction.RIGHT))) {
						deltaX = 1;
						deltaY = 0;
					}


					int finalDeltaX = deltaX;
					int finalDeltaY = deltaY;
					Platform.runLater(() -> {
						playerMoved(
								playerArray[1],
								finalDeltaX,
								finalDeltaY,
								direction.get()
						);
					});
				}

			}


		}

	}


}

