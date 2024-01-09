package logic.game.objects;

import java.awt.Point;

import controller.GraphicsController;
import logic.GameObject;
import lombok.Getter;
import lombok.Setter;

public class Board implements GameObject {

	@Getter
	private Cell[][] cells;

	@Getter
	@Setter
	private Point position;

	@Getter
	@Setter
	int size;

	public Board() {
		cells = new Cell[3][3];
		position = new Point();
		size = 0;

		initBoard();
	}

	public Board(int x, int y, int size) {
		this();
		this.position = new Point(x, y);
		this.size = size;

		initBoard();
	}

	public Board(Point position, int size) {
		this();

		this.position = position;
		this.size = size;

		initBoard();
	}

	@Override
	public void update(double deltaTime) {
		for (int row = 0; row < cells.length; row++) {
			for (int col = 0; col < cells.length; col++) {
				cells[row][col].update(deltaTime);
			}
		}
	}

	@Override
	public void render(GraphicsController graphics) {

		graphics.drawSquare(0x00FF00, (int) position.getX(), (int) position.getY(), 2, 5);

		for (int row = 0; row < cells.length; row++) {
			for (int col = 0; col < cells.length; col++) {
				cells[row][col].render(graphics);
			}
		}
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

	private void initBoard() {

		for (int row = 0; row < cells.length; row++) {
			for (int col = 0; col < cells.length; col++) {
				cells[row][col] = new Cell((int) (row * size / 3 + position.getX()),
						(int) (col * size / 3 + position.getY()), size / 3);
			}
		}
	}
}
