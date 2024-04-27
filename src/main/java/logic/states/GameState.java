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

	@Getter
	@Setter
	private Cell selectedCell = null;

	private int totalTurns = 0;

	public GameState(GameController gameController) {
		this.gameController = gameController;
		objects = new ArrayList<GameObject>();
		Board board = new Board(gameController.getFRAME_WIDTH() / 2, gameController.getFRAME_HEIGHT() / 2 + 20, this,
				gameController.getFRAME_HEIGHT() * 2 / 3);

		GameButton confirmButton = new GameButton("OK", (int) (gameController.getFRAME_WIDTH() * 0.79),
				gameController.getFRAME_HEIGHT() / 3, (int) (gameController.getFRAME_WIDTH() * 0.15),
				(int) (gameController.getFRAME_HEIGHT() * 0.1), 0x20DD20, 0x20FF20, 0x000000, 20);

		confirmButton.setAction(this::confirmMovement);

		objects.add(board);
		objects.add(confirmButton);

		totalTurns++;
	}

	@Override
	public void update(double deltaTime) {
		objects.forEach(o -> o.update(deltaTime));

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

			selectedCell.block();
			togglePlayer();
			selectedCell = null;

		}
	}

	private void togglePlayer() {
		if (playerTurn == 1)
			playerTurn++;
		else
			playerTurn--;
	}

}
