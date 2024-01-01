package logic.game.objects;

import controller.GraphicsController;
import logic.enums.CellState;
import lombok.Getter;
import lombok.Setter;

public class Cell {
	@Getter
	@Setter
	private CellState state;

	public Cell() {
		state = CellState.EMPTY;
	}

	public void update() {

	}

	public void render(GraphicsController graphics) {
		graphics.drawSquare(0x000000, 10, 38, 100, 5);

		switch (state) {
		case CROSS:
			graphics.drawCross(0xFF00FF, 0, 0, 0, 0, 10);
			break;

		case CIRCLE:
			graphics.drawCircle(0xFF0000, 0, 0, 0, 10);
			break;

		default:
			break;
		}
	}
}
