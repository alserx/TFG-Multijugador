package logic.game.objects;

import java.awt.Point;
import java.util.List;

import controller.GameController;
import controller.GraphicsController;
import logic.GameObject;
import logic.enums.CellState;
import logic.enums.UserEvent;
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

	private GameController gameController;

	private Cell() {
		this.state = CellState.EMPTY;
		this.size = 0;
		this.position = new Point();
	}

	public Cell(Point position, int size, GameController gameController) {
		this();
		this.position = position;
		this.size = size;

		this.gameController = gameController;
	}

	public Cell(int x, int y, int size, GameController gameController) {
		this();
		this.position = new Point(x, y);
		this.size = size;

		this.gameController = gameController;
	}

	@Override
	public void handleInput(List<UserEvent> userEvents) {
		for (UserEvent event : userEvents) {
			if (clickInside(new Point(event.getX(), event.getY()))) {
				performClick(gameController.getPlayerTurn());
			}
		}

	}

	@Override
	public void update(double deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GraphicsController graphics) {

//		 Debug
//		graphics.drawSquare(0x00FF00, position.x, position.y, 2, 5);

		graphics.drawSquare(0x000000, position.x - size / 2, position.y - size / 2, size, 5);

		int figureSize = (int) (size * 0.7);
		switch (state) {

		case CROSS:
			graphics.drawCross(0x00F00FF, position.x - figureSize / 2, position.y - figureSize / 2, figureSize, 10);
			break;

		case CIRCLE:
			graphics.drawCircle(0xFF0000, position.x - figureSize / 2, position.y - figureSize / 2, figureSize, 10);
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
			if (playerTurn == 1) {
				state = CellState.CROSS;
				gameController.setPlayerTurn(2);
			} else if (playerTurn == 2) {
				state = CellState.CIRCLE;
				gameController.setPlayerTurn(1);
			}
	}

	/**
	 * Comprueba si se ha hecho click dentro de la celda
	 * 
	 * @param clickPosition la posicion del click
	 * @return true si se hace click dentro, false en otro caso
	 */
	public boolean clickInside(Point clickPosition) {
		if (clickPosition.x >= position.x - size / 2 && clickPosition.x <= position.x + size / 2
				&& clickPosition.y >= position.y - size / 2 && clickPosition.y <= position.y + size / 2) {
			System.out.println("Click Dentro!!");
			return true;
		}

		return false;
	}

}
