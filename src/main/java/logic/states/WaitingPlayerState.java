package logic.states;

import java.util.ArrayList;
import java.util.List;

import controller.GameController;
import controller.GraphicsController;
import logic.GameObject;
import logic.enums.CellState;
import logic.enums.UserEvent;
import logic.game.objects.GameAnimatedText;

public class WaitingPlayerState implements State {
	private ArrayList<GameObject> objects;
	private GameController gameController;

	private int titleTextSize;

	public WaitingPlayerState(GameController gameController) {
		objects = new ArrayList<GameObject>();
		this.gameController = gameController;

		titleTextSize = 40;

		GameAnimatedText waitingText = new GameAnimatedText("Waiting for players...",
				(int) (gameController.getFRAME_WIDTH() * 0.5)
						- gameController.getGraphicsController().getStringWidth("Waiting for players...", titleTextSize)
								/ 2,
				(int) (gameController.getFRAME_HEIGHT() * 0.5)
						- gameController.getGraphicsController().getStringHeight(titleTextSize) / 2,
				0xFF101010, titleTextSize, 2.5);

		objects.add(waitingText);
	}

	@Override
	public void update(double deltaTime) {
		objects.forEach(o -> o.update(deltaTime));
	}

	@Override
	public void render(GraphicsController graphics) {
		objects.forEach(o -> o.render(graphics));
	}

	@Override
	public void handleInput(List<UserEvent> userEvents) {
		objects.forEach(o -> o.handleInput(userEvents));
	}

	@Override
	public void receiveMessage(String message) {
		String[] splitMessage = message.split(" ");
		CellState playerFigure = CellState.EMPTY;
		boolean playerTurn = Boolean.parseBoolean(splitMessage[2]);
		String starterUser = splitMessage[0];

		if (splitMessage[1].equalsIgnoreCase("o"))
			playerFigure = CellState.CIRCLE;
		else if (splitMessage[1].equalsIgnoreCase("x"))
			playerFigure = CellState.CROSS;

		gameController.setPlaying(true);
		gameController.getStateController()
				.pushState(new GameState(gameController, playerFigure, playerTurn, starterUser));
	}

}
