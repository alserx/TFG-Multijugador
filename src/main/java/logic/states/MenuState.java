package logic.states;

import java.util.ArrayList;
import java.util.List;

import controller.GameController;
import controller.GraphicsController;
import logic.GameObject;
import logic.enums.UserEvent;
import logic.game.objects.GameButton;

public class MenuState implements State {
	private ArrayList<GameObject> objects;

	public MenuState(GameController gameController) {
		objects = new ArrayList<GameObject>();

		GameButton playButton = initPlayButton(gameController);
		GameButton exitButton = initExitButton(gameController);

		objects.add(playButton);
		objects.add(exitButton);
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

	private GameButton initPlayButton(GameController gameController) {
		int width = (int) (gameController.getFRAME_WIDTH() * 0.25),
				height = (int) (gameController.getFRAME_HEIGHT() * 0.1);
		int x = (int) (gameController.getFRAME_WIDTH() * 0.5) - width / 2,
				y = gameController.getFRAME_HEIGHT() / 2 - height / 2;

		GameButton playButton = new GameButton("PLAY", x, y, width, height, 0x20DD20, 0x20FF20, 0x000000, 20);
		playButton.setAction(() -> {
			gameController.getStateController().pushState(new GameState(gameController));
		});

		return playButton;
	}

	private GameButton initExitButton(GameController gameController) {
		int width = (int) (gameController.getFRAME_WIDTH() * 0.25),
				height = (int) (gameController.getFRAME_HEIGHT() * 0.1);
		int x = (int) (gameController.getFRAME_WIDTH() * 0.5) - width / 2,
				y = gameController.getFRAME_HEIGHT() / 2 + height;

		GameButton exitButton = new GameButton("EXIT", x, y, width, height, 0xDD1010, 0xFF1010, 0x000000, 20);
		exitButton.setAction(() -> {
			gameController.setRunning(false);
		});

		return exitButton;
	}

}
