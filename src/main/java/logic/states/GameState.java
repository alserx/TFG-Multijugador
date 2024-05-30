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
	@Getter
	@Setter
	private Cell selectedCell = null;
	@Getter
	private CellState playerFigure;
	private Board board;

	// Server side variables
	private String winner;
	private String currentUser;
	private boolean myTurn;

	public GameState(GameController gameController, CellState playerFigure, boolean playerTurn, String currentUser) {
		this.gameController = gameController;
		this.playerFigure = playerFigure;
		this.myTurn = playerTurn;
		this.currentUser = currentUser;
		this.winner = null;
		objects = new ArrayList<GameObject>();

		// Create scene objects
		board = new Board(gameController.getFRAME_WIDTH() / 2, gameController.getFRAME_HEIGHT() / 2 + 20, this,
				gameController.getFRAME_HEIGHT() * 2 / 3);
		GameButton confirmButton = initConfirmButton(gameController);

		// Add to the scene
		objects.add(board);
		objects.add(confirmButton);
	}

	@Override
	public void update(double deltaTime) {
		if (gameController.isPlaying())
			objects.forEach(o -> o.update(deltaTime));
		else {
			try {
				Thread.sleep(1000);
				gameController.getStateController().pushState(new EndGameState(gameController, winner));
			} catch (Exception e) {
				gameController.getStateController().pushState(new EndGameState(gameController, winner));
			}

		}

	}

	@Override
	public void render(GraphicsController graphics) {
		objects.forEach(o -> o.render(graphics));
		playerTurnText(graphics);
	}

	@Override
	public void handleInput(List<UserEvent> userEvents) {
		if (gameController.isPlaying() && myTurn)
			objects.forEach(o -> o.handleInput(userEvents));
	}

	private void playerTurnText(GraphicsController graphics) {
		String text = "Turno del jugador " + currentUser;
		int color = 0;

		switch (playerFigure) {
		case CROSS:
			color = 0xFF0000FF;
			break;
		case CIRCLE:
			color = 0xFFFF0000;
			break;

		default:
			break;
		}

		graphics.drawText(text, color, (int) (gameController.getFRAME_WIDTH() * 0.05),
				(int) (gameController.getFRAME_HEIGHT() * 0.15), 32);

	}

	private void confirmMovement() {
		if (selectedCell != null && selectedCell.getState() != CellState.EMPTY) {
			selectedCell.block();

			// Send message to server
			// formato: {board row} {board col}
			gameController.getGameClient().sendMessage(selectedCell.getRow() + " " + selectedCell.getCol());
			myTurn = false;
			selectedCell = null;

		}

	}

	// BUTTONS --------------------------------------------------------------------
	private GameButton initConfirmButton(GameController gameController) {
		int width = (int) (gameController.getFRAME_WIDTH() * 0.15);
		int height = (int) (gameController.getFRAME_HEIGHT() * 0.07);
		int x = (int) (gameController.getFRAME_WIDTH() * 0.85) - width / 2;
		int y = (int) (gameController.getFRAME_HEIGHT() * 0.4) - height / 2;

		GameButton confirmButton = new GameButton("OK", x, y, width, height, 0xFF20DD20, 0xFF20FF20, 0xFF000000, 20);

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

		if (splitMessage[1].equalsIgnoreCase("o"))
			clickedCell = CellState.CIRCLE;
		else if (splitMessage[1].equalsIgnoreCase("x"))
			clickedCell = CellState.CROSS;

		board.getCells()[row][col].setState(clickedCell);
		board.getCells()[row][col].block();

		myTurn = true;
		// ha acabado la partida
		if (splitMessage.length > 4) {
			// no se juega
			gameController.setPlaying(false);
		}
	}

}
