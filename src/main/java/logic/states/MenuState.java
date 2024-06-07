package logic.states;

import java.util.ArrayList;
import java.util.List;

import controller.GameController;
import controller.GraphicsController;
import logic.GameObject;
import logic.enums.UserEvent;
import logic.game.objects.GameAnimatedText;
import logic.game.objects.GameButton;
import logic.game.objects.GameTextField;

public class MenuState implements State {
	private ArrayList<GameObject> objects;
	private GameController gameController;

	private int titleTextSize, normalTextSize, smallTextSize;

	private boolean enteredUsername;

	GameTextField userTextField;

	public MenuState(GameController gameController) {
		gameController.getGameClient().init(gameController);
		objects = new ArrayList<GameObject>();
		this.gameController = gameController;

		GameButton playButton = initPlayButton(gameController);
		GameButton exitButton = initExitButton(gameController);
		userTextField = initTextField(gameController);

		objects.add(playButton);
		objects.add(exitButton);
		objects.add(userTextField);

		titleTextSize = 40;
		normalTextSize = 20;
		smallTextSize = 16;
		enteredUsername = true;

		GameAnimatedText ticText = new GameAnimatedText("Tic",
				(int) (gameController.getFRAME_WIDTH() * 0.5)
						- gameController.getGraphicsController().getStringWidth("TicTac", titleTextSize) / 2,
				(int) (gameController.getFRAME_HEIGHT() * 0.18), 0xFFFF0000, titleTextSize, 2.5);

		GameAnimatedText tacText = new GameAnimatedText("Tac",
				(int) (gameController.getFRAME_WIDTH() * 0.5)
						- gameController.getGraphicsController().getStringWidth("Tac", titleTextSize) / 2,
				(int) (gameController.getFRAME_HEIGHT() * 0.18) + titleTextSize, 0xFF0000FF, titleTextSize, 4);

		GameAnimatedText toeText = new GameAnimatedText("Toe",
				(int) (gameController.getFRAME_WIDTH() * 0.5)
						- gameController.getGraphicsController().getStringWidth("T", titleTextSize) / 2,
				(int) (gameController.getFRAME_HEIGHT() * 0.18) + titleTextSize * 2, 0xFFBD00FF, titleTextSize, 1);

		objects.add(ticText);
		objects.add(tacText);
		objects.add(toeText);
	}

	@Override
	public void update(double deltaTime) {
		objects.forEach(o -> o.update(deltaTime));
	}

	@Override
	public void render(GraphicsController graphics) {
		drawMenuText(graphics, "Username:", 0xFF000000,
				(int) (gameController.getFRAME_WIDTH() * 0.42) - graphics.getStringWidth("Username:", normalTextSize),
				(int) (gameController.getFRAME_HEIGHT() * 0.38) + normalTextSize * 2, normalTextSize);

		if (!enteredUsername)
			drawMenuText(graphics, "Introduce a valid username! No spaces allowed", 0xFFFF0000,
					(int) (gameController.getFRAME_WIDTH() * 0.5)
							- graphics.getStringWidth("Introduce a valid username!", smallTextSize) / 2,
					(int) (gameController.getFRAME_HEIGHT() * 0.52) + smallTextSize * 2, smallTextSize);

		objects.forEach(o -> o.render(graphics));
	}

	private void drawMenuText(GraphicsController graphics, String text, int color, int x, int y, int size) {
		graphics.drawText(text, color, x, y, size);
	}

	@Override
	public void handleInput(List<UserEvent> userEvents) {
		objects.forEach(o -> o.handleInput(userEvents));
	}

	private GameButton initPlayButton(GameController gameController) {
		int width = (int) (gameController.getFRAME_WIDTH() * 0.25),
				height = (int) (gameController.getFRAME_HEIGHT() * 0.1);
		int x = (int) (gameController.getFRAME_WIDTH() * 0.5) - width / 2,
				y = (int) (gameController.getFRAME_HEIGHT() * 0.68) - height / 2;

		GameButton playButton = new GameButton("PLAY", x, y, width, height, 0xFF20DD20, 0xFF20FF20, 0xFF000000, 20);
		playButton.setAction(() -> {
			if (!userTextField.getText().isEmpty() && !userTextField.getText().contains(" ")) {
				enteredUsername = true;

				State newState = new WaitingPlayerState(gameController);
				// mandar mensaje al server
				gameController.getGameClient().sendMessage(userTextField.getText());
				gameController.getStateController().pushState(newState);
			} else {
				enteredUsername = false;
				System.err.println("No hay nombre de usuario!");
			}
		});

		return playButton;
	}

	private GameButton initExitButton(GameController gameController) {
		int width = (int) (gameController.getFRAME_WIDTH() * 0.25),
				height = (int) (gameController.getFRAME_HEIGHT() * 0.1);
		int x = (int) (gameController.getFRAME_WIDTH() * 0.5) - width / 2,
				y = (int) (gameController.getFRAME_HEIGHT() * 0.68) + height;

		GameButton exitButton = new GameButton("EXIT", x, y, width, height, 0xFFDD1010, 0xFFFF1010, 0xFF000000, 20);
		exitButton.setAction(() -> {
			gameController.setRunning(false);
		});

		return exitButton;
	}

	private GameTextField initTextField(GameController gameController) {
		int width = (int) (gameController.getFRAME_WIDTH() * 0.4);
		int height = (int) (gameController.getFRAME_HEIGHT() * 0.05);
		int x = (int) (gameController.getFRAME_WIDTH() * 0.5) - width / 2;
		int y = (int) (gameController.getFRAME_HEIGHT() * 0.5) - height / 2;

		GameTextField textField = new GameTextField(x, y, width, height, 0xFFFFFFFF, 0xFF000000, 0xFF000000, 20);

		return textField;
	}

	@Override
	public void receiveMessage(String message) {
		return;
	}
}
