package logic.states;

import java.util.ArrayList;
import java.util.List;

import controller.GameController;
import controller.GraphicsController;
import logic.GameObject;
import logic.enums.CellState;
import logic.enums.UserEvent;

public class WaitingPlayerState implements State {
	private ArrayList<GameObject> objects;
	private GameController gameController;

	private int titleTextSize;

	public WaitingPlayerState(GameController gameController) {
		objects = new ArrayList<GameObject>();
		this.gameController = gameController;

		titleTextSize = 40;
	}

	@Override
	public void update(double deltaTime) {
		objects.forEach(o -> o.update(deltaTime));
	}

	@Override
	public void render(GraphicsController graphics) {
		drawSceneText(graphics, "Waiting for players...", 0x101010,
				(int) (gameController.getFRAME_WIDTH() * 0.5)
						- graphics.getStringWidth("Waiting for players...", titleTextSize) / 2,
				(int) (gameController.getFRAME_HEIGHT() * 0.5) - graphics.getStringHeight(titleTextSize) / 2,
				titleTextSize);

		objects.forEach(o -> o.render(graphics));
	}

	private void drawSceneText(GraphicsController graphics, String text, int color, int x, int y, int size) {
		graphics.drawText(text, color, x, y, size);
	}

	@Override
	public void handleInput(List<UserEvent> userEvents) {
		objects.forEach(o -> o.handleInput(userEvents));
	}

	@Override
	public void receiveMessage(String message) {
		String[] splitMessage = message.split(" ");
		CellState playerFigure = CellState.EMPTY;
		int playerTurn = 2;
		String starterUser = splitMessage[0];

		if (splitMessage[1].equalsIgnoreCase("o"))
			playerFigure = CellState.CIRCLE;
		else if (splitMessage[1].equalsIgnoreCase("x"))
			playerFigure = CellState.CROSS;

		if (splitMessage.length > 2) {
			playerTurn = 1;
		}

		gameController.setPlaying(true);
		gameController.getStateController()
				.pushState(new GameState(gameController, playerFigure, playerTurn, starterUser));
	}

}
