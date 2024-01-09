package logic.game.objects;

import java.awt.Point;

import controller.GraphicsController;
import logic.GameObject;
import logic.enums.CellState;
import lombok.Getter;
import lombok.Setter;

public class Cell implements GameObject {
	@Getter
	@Setter
	private CellState state;

	@Getter
	@Setter
	private Point position;

	@Getter
	@Setter
	int size;

	private Cell() {
		this.state = CellState.EMPTY;
		this.size = 0;
		this.position = new Point();
	}

	public Cell(Point position, int size) {
		this();
		this.position = position;
		this.size = size;
	}

	public Cell(int x, int y, int size) {
		this();
		this.position = new Point(x, y);
		this.size = size;
	}

	@Override
	public void handleInput() {

	}

	@Override
	public void update(double deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GraphicsController graphics) {
		graphics.drawSquare(0x000000, position.x - size / 2, position.y - size / 2, size, 5);

		switch (state) {
		case CROSS:
			graphics.drawCross(0xFF00FF, position.x - size / 2, position.y - size / 2, size, 10);
			break;

		case CIRCLE:
			graphics.drawCircle(0xFF0000, position.x - size / 2, position.y - size / 2, size, 10);
			break;

		default:
			break;
		}
	}

	/**
	 * Realiza la accion del click en funcion del jugador que esté jugando
	 * 
	 * @param playerTurn El jugador que realiza el movimiento
	 */
	public void performClick(int playerTurn) {
		if (state == CellState.EMPTY)
			if (playerTurn == 1)
				state = CellState.CROSS;
			else if (playerTurn == 2)
				state = CellState.CIRCLE;
	}

}
