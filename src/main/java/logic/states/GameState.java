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

	// Game variable
	@Getter
	@Setter
	private int playerTurn = 1;
	private int totalTurns = 0;
	private int winner = 0;
	@Getter
	@Setter
	private Cell selectedCell = null;
	private Board board;

	@Getter
	private boolean playing = true;

	public GameState(GameController gameController) {
		this.gameController = gameController;
		objects = new ArrayList<GameObject>();

		// Create scene objects
		board = new Board(gameController.getFRAME_WIDTH() / 2, gameController.getFRAME_HEIGHT() / 2 + 20, this,
				gameController.getFRAME_HEIGHT() * 2 / 3);
		GameButton confirmButton = initConfirmButton(gameController);
		GameButton exitButton = initExitButton(gameController);

		// Add to the scene
		objects.add(board);
		objects.add(confirmButton);
		objects.add(exitButton);

		totalTurns++;
	}

	@Override
	public void update(double deltaTime) {
		if (playing)
			objects.forEach(o -> o.update(deltaTime));
		else
			gameController.getStateController().pushState(new EndGameState(gameController, winner));

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
		if (playing)
			objects.forEach(o -> o.handleInput(userEvents));
	}

	private void playerTurnText(GraphicsController graphics) {
		String text = "Turno del jugador " + playerTurn;
		int color = 0;

		if (playerTurn == 1)
			color = 0x0000FF;
		else
			color = 0xFF0000;

		graphics.drawText(text, color, (int) (gameController.getFRAME_WIDTH() * 0.05),
				(int) (gameController.getFRAME_HEIGHT() * 0.15), 32);

	}

	private void confirmMovement() {
		if (selectedCell != null && selectedCell.getState() != CellState.EMPTY) {
			if (playerTurn == 2) {
				totalTurns++;
			}

			if (board.checkWin()) {
				winner = playerTurn;
				playing = false;
			} else if (board.checkDraw()) {
				playing = false;
			}

			selectedCell.block();
			togglePlayer();
			selectedCell = null;

		}

		// TODO enviar mensaje al servidor
	}

	private void togglePlayer() {
		if (playerTurn == 1)
			playerTurn++;
		else
			playerTurn--;
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

}
