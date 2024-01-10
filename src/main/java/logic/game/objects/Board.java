package logic.game.objects;

import java.awt.Point;
import java.util.List;

import controller.GameController;
import controller.GraphicsController;
import logic.GameObject;
import logic.enums.UserEvent;
import lombok.Getter;
import lombok.Setter;

public class Board implements GameObject {

	private final int BOARD_ROWS = 3, BOARD_COLS = 3;

	@Getter
	private Cell[][] cells;

	@Getter
	@Setter
	private Point position;

	@Getter
	@Setter
	private int size;

	private int cellSize;

	private Board(GameController gameController) {
		cells = new Cell[BOARD_ROWS][BOARD_COLS];
		position = new Point();
		size = 0;
	}

	public Board(int x, int y, int size, GameController gameController) {
		this(gameController);
		this.position = new Point(x, y);
		this.size = size;

		cellSize = (int) (Math.min(size / BOARD_COLS, size / BOARD_ROWS));

		initBoard(gameController);
	}

	public Board(Point position, int size, GameController gameController) {
		this(gameController);

		this.position = position;
		this.size = size;

		initBoard(gameController);
	}

	@Override
	public void update(double deltaTime) {
		for (int row = 0; row < BOARD_ROWS; row++) {
			for (int col = 0; col < BOARD_COLS; col++) {
				cells[row][col].update(deltaTime);
			}
		}
	}

	@Override
	public void render(GraphicsController graphics) {

		graphics.drawSquare(0x00FF00, (int) position.getX(), (int) position.getY(), 2, 5);

		for (int row = 0; row < BOARD_ROWS; row++) {
			for (int col = 0; col < BOARD_COLS; col++) {
				cells[row][col].render(graphics);
			}
		}
	}

	@Override
	public void handleInput(List<UserEvent> userEvents) {
		for (int row = 0; row < BOARD_ROWS; row++) {
			for (int col = 0; col < BOARD_COLS; col++) {
				cells[row][col].handleInput(userEvents);
			}
		}
	}

	private void initBoard(GameController gameController) {

		for (int row = 0; row < BOARD_ROWS; row++) {
			for (int col = 0; col < BOARD_COLS; col++) {
				cells[row][col] = new Cell((int) (row * size / BOARD_ROWS + position.getX()),
						(int) (col * size / BOARD_COLS + position.getY()), cellSize, gameController);
			}
		}
	}
}
