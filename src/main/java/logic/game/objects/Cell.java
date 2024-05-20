package logic.game.objects;

import java.awt.Point;
import java.util.List;

import controller.GraphicsController;
import logic.GameObject;
import logic.enums.CellState;
import logic.enums.UserEvent;
import logic.states.GameState;
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
	private int size;

	@Getter
	private int row, col;

	private boolean blocked = false;

	private GameState game;

	private Cell() {
		this.state = CellState.EMPTY;
		this.size = 0;
		this.position = new Point();
	}

	public Cell(Point position, int size, int row, int col, GameState game) {
		this();
		this.position = position;
		this.size = size;
		this.row = row;
		this.col = col;
		this.game = game;
	}

	public Cell(int x, int y, int size, int row, int col, GameState game) {
		this();
		this.position = new Point(x, y);
		this.size = size;
		this.row = row;
		this.col = col;
		this.game = game;
	}

	@Override
	public void handleInput(List<UserEvent> userEvents) {
		for (UserEvent event : userEvents) {
			if (event == UserEvent.CLICK) {
				if (clickInside(new Point(event.getX(), event.getY()))) {
					performClick(game.getSelectedCell(), game.getPlayerFigure());
				}
			}
		}

	}

	@Override
	public void update(double deltaTime) {
	}

	@Override
	public void render(GraphicsController graphics) {
		graphics.drawSquare(0x000000, position.x - size / 2, position.y - size / 2, size, 5);

		int figureSize = (int) (size * 0.7);
		switch (state) {
		case CROSS:
			graphics.drawCross(0x00F00FF, position.x - figureSize / 2, position.y - figureSize / 2, figureSize, 10);
			break;

		case CIRCLE:
			graphics.drawCircle(0xFF0000, position.x - figureSize / 2, position.y - figureSize / 2, figureSize, 10);
			break;

		case EMPTY:
			break;
		}
	}

	/**
	 * Realiza la accion del click en funcion del jugador que esté jugando
	 * 
	 */
	private void performClick(Cell selectedCell, CellState playerFigure) {
		if (state == CellState.EMPTY) {
			state = playerFigure;

			if (selectedCell != null && selectedCell.getState() == state) {
				selectedCell.setState(CellState.EMPTY);
			}

			game.setSelectedCell(this);

		} else if (!blocked) {
			state = CellState.EMPTY;
			game.setSelectedCell(null);
		}
	}

	/**
	 * Comprueba si se ha hecho click dentro de la celda
	 * 
	 * @param clickPosition la posicion del click
	 * @return true si se hace click dentro, false en otro caso
	 */
	private boolean clickInside(Point clickPosition) {
		return clickPosition.x >= position.x - size / 2 && clickPosition.x <= position.x + size / 2
				&& clickPosition.y >= position.y - size / 2 && clickPosition.y <= position.y + size / 2;
	}

	public void block() {
		blocked = true;
	}

}
