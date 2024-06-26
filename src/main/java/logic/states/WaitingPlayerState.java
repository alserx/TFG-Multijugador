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
		System.out.println(message);

		String[] splitMessage = message.split(" ");
		CellState playerFigure = CellState.EMPTY;
		String playersMatch = splitMessage[0] + " VS " + splitMessage[1];
		boolean playerTurn = Boolean.parseBoolean(splitMessage[3]);

		if (splitMessage[2].equalsIgnoreCase("o"))
			playerFigure = CellState.CIRCLE;
		else if (splitMessage[2].equalsIgnoreCase("x"))
			playerFigure = CellState.CROSS;

		State newState = new GameState(gameController, playerFigure, playerTurn, playersMatch);

		gameController.setPlaying(true);
		gameController.getStateController().pushState(newState);

	}

}
