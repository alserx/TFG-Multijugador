package logic.game.objects;

import java.awt.Point;
import java.util.List;

import controller.GraphicsController;
import logic.enums.CellState;
import logic.enums.UserEvent;
import logic.states.GameState;
import lombok.Getter;

public class Board extends AbstractGameObject {

	// Filas y columnas
	private final int BOARD_ROWS = 3, BOARD_COLS = 3;

	// Tablero
	@Getter
	private Cell[][] cells;

	// Dimensiones del tablero
	@Getter
	private final int size;

	// Dimensiones de las celdas
	private int cellSize;

	private Board(GameState game, int size) {
		cells = new Cell[BOARD_ROWS][BOARD_COLS];
		position = new Point();
		this.size = size;
	}

	public Board(int x, int y, GameState game, int size) {
		this(game, size);
		this.position = new Point(x - size / 3, y - size / 3);

		cellSize = (int) (Math.min(size / BOARD_COLS, size / BOARD_ROWS));

		initBoard(game);
	}

	public Board(Point position, GameState game, int size) {
		this(game, size);

		this.position.x = position.x - size / 3;
		this.position.y = position.y - size / 3;

		initBoard(game);
	}

	@Override
	public void update(double deltaTime) {
		if (this.isActive()) {
			for (int row = 0; row < BOARD_ROWS; row++) {
				for (int col = 0; col < BOARD_COLS; col++) {
					cells[row][col].update(deltaTime);
				}
			}
		}
	}

	@Override
	public void render(GraphicsController graphics) {
		if (this.isActive()) {
			for (int row = 0; row < BOARD_ROWS; row++) {
				for (int col = 0; col < BOARD_COLS; col++) {
					cells[row][col].render(graphics);
				}
			}
		}
	}

	@Override
	public void handleInput(List<UserEvent> userEvents) {
		if (this.isActive()) {
			for (int row = 0; row < BOARD_ROWS; row++) {
				for (int col = 0; col < BOARD_COLS; col++) {
					cells[row][col].handleInput(userEvents);
				}
			}
		}
	}

	/**
	 * Creates a new centered board based on the current window dimensions
	 * 
	 * @param gameController the GameController to extract the frame information
	 */
	private void initBoard(GameState game) {

		for (int row = 0; row < BOARD_ROWS; row++) {
			for (int col = 0; col < BOARD_COLS; col++) {
				cells[row][col] = new Cell((int) (row * size / BOARD_ROWS + position.getX()),
						(int) (col * size / BOARD_COLS + position.getY()), cellSize, row, col, game);
			}
		}
	}

	/**
	 * Checks the board rows if there is a TicTacToe
	 * 
	 * @return true if one of the players wins, false in other case
	 */
	private boolean checkRows() {
		CellState prevState = CellState.EMPTY;
		boolean win = true;

		for (int col = 0; col < BOARD_COLS; col++) {
			win = true;
			for (int row = 0; row < BOARD_ROWS; row++) {
				CellState actualState = cells[row][col].getState();
				if (row == 0) // Al principio se asigna el estado de la primera celda
					prevState = actualState;

				if (actualState == CellState.EMPTY) {
					win = false;
					break;
				}

				if (actualState != prevState) {
					win = false;
					break;
				}

				if (row > 0) // Solo despues de la primera celda
					prevState = actualState;
			}
			if (win)
				break;
		}

		return win;
	}

	/**
	 * Checks the board columns if there is a TicTacToe
	 * 
	 * @return true if one of the players wins, false in other case
	 */
	private boolean checkCols() {
		CellState prevState = CellState.EMPTY;
		boolean win = true;
		for (int row = 0; row < BOARD_ROWS; row++) {
			win = true;
			for (int col = 0; col < BOARD_COLS; col++) {
				CellState actualState = cells[row][col].getState();
				if (col == 0) // Al principio se asigna el estado de la primera celda
					prevState = actualState;

				if (actualState == CellState.EMPTY) {
					win = false;
					break;
				}

				if (actualState != prevState) {
					win = false;
					break;
				}

				if (col > 0) // Solo despues de la primera celda
					prevState = actualState;
			}
			if (win)
				break;
		}

		return win;
	}

	/**
	 * Checks the board diagonals if there is a TicTacToe
	 * 
	 * @return true if one of the players wins, false in other case
	 */
	private boolean checkDiagonals() {
		return checkFirstDiagonal() || checkSecondDiagonal();
	}

	/**
	 * Check if there is a TicTacToe in the diagonal
	 * 
	 * @return true if one of the player wins, false in other case
	 */
	private boolean checkSecondDiagonal() {
		CellState prevState = CellState.EMPTY;
		boolean win = true;
		// Diagonal 2
		int col = BOARD_COLS - 1;
		for (int row = 0; row < BOARD_ROWS; row++) {
			CellState actualState = cells[row][col].getState();
			if (row == 0) // Al principio se asigna el estado de la primera celda
				prevState = actualState;

			if (actualState == CellState.EMPTY)
				win = false;

			if (actualState != prevState)
				win = false;

			if (row > 0) // Solo despues de la primera celda
				prevState = actualState;

			col--;
		}

		return win;
	}

	/**
	 * Checks if there is a TicTacToe in the first diagonal
	 * 
	 * @return true if one of the players wins, false in other case
	 */
	private boolean checkFirstDiagonal() {
		CellState prevState = CellState.EMPTY;

		boolean win = true;
		// Diagonal 1
		for (int row = 0; row < BOARD_ROWS; row++) {
			CellState actualState = cells[row][row].getState();
			if (row == 0) // Al principio se asigna el estado de la primera celda
				prevState = actualState;

			if (actualState == CellState.EMPTY) {
				win = false;
				break;
			}

			if (actualState != prevState) {
				win = false;
				break;
			}

			if (row > 0) // Solo despues de la primera celda
				prevState = actualState;
		}

		return win;
	}

	/**
	 * Check if one of the player won the match
	 * 
	 * @return true if there is a win, false in other case
	 */
	public boolean checkWin() {
		return checkRows() || checkCols() || checkDiagonals();
	}

	/**
	 * Check if is a tie
	 * 
	 * @return true if there is a tie false in other case
	 */
	public boolean checkDraw() {
		for (int row = 0; row < BOARD_ROWS; row++) {
			for (int col = 0; col < BOARD_COLS; col++) {
				if (cells[row][col].getState() == CellState.EMPTY)
					return false;
			}
		}

		if (checkWin())
			return false;

		return true;
	}
}
