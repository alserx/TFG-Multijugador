package logic.states;

import java.util.ArrayList;
import java.util.List;

import controller.GameController;
import controller.GraphicsController;
import logic.GameObject;
import logic.enums.UserEvent;
import logic.game.objects.GameButton;

public class EndGameState implements State {
	private ArrayList<GameObject> objects;
	private GameController gameController;

	private String playerVictory;

	public EndGameState(GameController gameController, String playerVictory) {
		this.gameController = gameController;
		objects = new ArrayList<GameObject>();

		this.playerVictory = playerVictory;

		GameButton exitButton = initExitButton(gameController);

		objects.add(exitButton);
	}

	@Override
	public void update(double deltaTime) {
		objects.forEach(o -> o.update(deltaTime));
	}

	@Override
	public void render(GraphicsController graphics) {
		objects.forEach(o -> o.render(graphics));

		drawResultText(playerVictory, graphics);
	}

	@Override
	public void handleInput(List<UserEvent> userEvents) {
		objects.forEach(o -> o.handleInput(userEvents));
	}

	private GameButton initExitButton(GameController gameController) {
		int width = (int) (gameController.getFRAME_WIDTH() * 0.35),
				height = (int) (gameController.getFRAME_HEIGHT() * 0.1);
		int x = (int) (gameController.getFRAME_WIDTH() * 0.5) - width / 2,
				y = (int) (gameController.getFRAME_HEIGHT() * 0.6) - height / 2;

		GameButton exitButton = new GameButton("BACK TO MENU", x, y, width, height, 0xFFDD1010, 0xFFFF1010, 0xFF000000,
				20);
		exitButton.setAction(() -> {
			// gameController.setRunning(false);

			gameController.getStateController().popState();
			gameController.getStateController().popState();
		});

		return exitButton;
	}

	private void drawResultText(String playerVictory, GraphicsController graphics) {
		String text = "Ha sido empate!";
		int color = 0xABABAB;
		int fontSize = 32;

		if (playerVictory != null) {
			text = "Ha ganado " + playerVictory + "!!";
			color = 0x0000FF;
			color = 0xFF0000;
		}

		graphics.drawText(text, color,
				(int) (gameController.getFRAME_WIDTH() * 0.5) - graphics.getStringWidth(text, fontSize),
				(int) (gameController.getFRAME_HEIGHT() * 0.5), fontSize);

	}

	@Override
	public void receiveMessage(String message) {

	}

}
