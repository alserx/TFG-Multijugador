package logic.states;

import java.util.ArrayList;
import java.util.List;

import controller.GameController;
import controller.GraphicsController;
import logic.GameObject;
import logic.enums.CellState;
import logic.enums.UserEvent;
import logic.game.objects.Board;
import logic.game.objects.Cell;
import logic.game.objects.GameButton;
import lombok.Getter;
import lombok.Setter;

public class GameState implements State {

	private ArrayList<GameObject> objects;
	private GameController gameController;

	// Game variables
	private int playerTurn;
	@Getter
	@Setter
	private Cell selectedCell = null;
	@Getter
	private CellState playerFigure;
	private Board board;

	// Server side variables
	private int serverTurn;
	private int totalTurns;
	private int winner;
	private String currentUser;

	public GameState(GameController gameController, CellState playerFigure, int playerTurn, String currentUser) {
		this.gameController = gameController;
		this.playerFigure = playerFigure;
		this.playerTurn = playerTurn;
		this.currentUser = currentUser;

		objects = new ArrayList<GameObject>();

		// Create scene objects
		board = new Board(gameController.getFRAME_WIDTH() / 2, gameController.getFRAME_HEIGHT() / 2 + 20, this,
				gameController.getFRAME_HEIGHT() * 2 / 3);
		GameButton confirmButton = initConfirmButton(gameController);

		// Add to the scene
		objects.add(board);
		objects.add(confirmButton);

		// totalTurns++;
	}

	@Override
	public void update(double deltaTime) {
		if (gameController.isPlaying())
			objects.forEach(o -> o.update(deltaTime));
		else {
			gameController.getGameClient().close();
			gameController.getStateController().pushState(new EndGameState(gameController, winner));
		}

	}

	@Override
	public void render(GraphicsController graphics) {
		objects.forEach(o -> o.render(graphics));
		playerTurnText(graphics);

		graphics.drawText("Turno: " + totalTurns, 0x000000, (int) (gameController.getFRAME_WIDTH() * 0.05),
				(int) (gameController.getFRAME_HEIGHT() * 0.5), 32);

	}

	@Override
	public void handleInput(List<UserEvent> userEvents) {
		if (gameController.isPlaying() && playerTurn == serverTurn)
			objects.forEach(o -> o.handleInput(userEvents));
	}

	private void playerTurnText(GraphicsController graphics) {
		String text = "Turno del jugador " + currentUser;
		int color = 0;

		switch (playerFigure) {
		case CROSS:
			color = 0x0000FF;
			break;
		case CIRCLE:
			color = 0xFF0000;
			break;

		default:
			break;
		}

		graphics.drawText(text, color, (int) (gameController.getFRAME_WIDTH() * 0.05),
				(int) (gameController.getFRAME_HEIGHT() * 0.15), 32);

	}

	private void confirmMovement() {
		if (selectedCell != null && selectedCell.getState() != CellState.EMPTY) {
			// this is managed by server
//			if (playerTurn == 2) {
//				totalTurns++;
//			}
//
//			if (board.checkWin()) {
//				winner = playerTurn;
//				gameController.setPlaying(false);
//			} else if (board.checkDraw()) {
//				gameController.setPlaying(false);
//			}

			selectedCell.block();
			toggleServerPlayer();

			// Send message to server
			// formato:
			// {user} {figure} {board row} {board col} {next turn}
			gameController.getGameClient().sendMessage(currentUser + " " + playerFigureString() + " "
					+ selectedCell.getRow() + " " + selectedCell.getCol() + " " + serverTurn);

			selectedCell = null;

		}

	}

	private String playerFigureString() {
		String parseFigure;

		switch (playerFigure) {
		case CROSS:
			parseFigure = "X";
			break;
		case CIRCLE:
			parseFigure = "O";
			break;
		default:
			parseFigure = "none";
			break;
		}

		return parseFigure;
	}

	private void toggleServerPlayer() {
		if (serverTurn == 1)
			serverTurn++;
		else
			serverTurn--;
	}

	// BUTTONS --------------------------------------------------------------------

	private GameButton initExitButton(GameController gameController) {
		int width = (int) (gameController.getFRAME_WIDTH() * 0.15);
		int height = (int) (gameController.getFRAME_HEIGHT() * 0.07);
		int x = (int) (gameController.getFRAME_WIDTH() * 0.85) - width / 2;
		int y = (int) (gameController.getFRAME_HEIGHT() * 0.7) - height / 2;

		GameButton exitButton = new GameButton("FF", x, y, width, height, 0xDD1010, 0xFF1010, 0x000000, 20);
		exitButton.setAction(() -> {
			gameController.setRunning(false);
			// TODO desconectar cliente del servidor
		});

		return exitButton;
	}

	private GameButton initConfirmButton(GameController gameController) {
		int width = (int) (gameController.getFRAME_WIDTH() * 0.15);
		int height = (int) (gameController.getFRAME_HEIGHT() * 0.07);
		int x = (int) (gameController.getFRAME_WIDTH() * 0.85) - width / 2;
		int y = (int) (gameController.getFRAME_HEIGHT() * 0.4) - height / 2;

		GameButton confirmButton = new GameButton("OK", x, y, width, height, 0x20DD20, 0x20FF20, 0x000000, 20);

		confirmButton.setAction(this::confirmMovement);
		return confirmButton;
	}

	@Override
	public void receiveMessage(String message) {
		String[] splitMessage = message.split(" ");

		CellState clickedCell = CellState.EMPTY;

		currentUser = splitMessage[0];
		int row = Integer.parseInt(splitMessage[2]);
		int col = Integer.parseInt(splitMessage[3]);
		serverTurn = Integer.parseInt(splitMessage[4]);

		if (splitMessage[1].equalsIgnoreCase("o"))
			clickedCell = CellState.CIRCLE;
		else if (splitMessage[1].equalsIgnoreCase("x"))
			clickedCell = CellState.CROSS;

		board.getCells()[row][col].setState(clickedCell);
		board.getCells()[row][col].block();
	}

}
