package controller;

import logic.game.objects.Cell;
import logic.states.GameState;
import lombok.Getter;
import lombok.Setter;

public class GameController implements Runnable {
	// Controllers
	@Getter
	private StateController stateController;
	@Getter
	private InputController inputController;
	@Getter
	private GraphicsController graphicsController;

	// View
	@Getter
	private final int FRAME_WIDTH = 600, FRAME_HEIGHT = 400;

	// Time
	private long lastFrameTime = 0;
	private long currentTime = 0;
	private double deltaTime = 0;

	// Game variable
	@Getter
	@Setter
	private int playerTurn = 1;

	public GameController() {
		graphicsController = new GraphicsController();
		inputController = new InputController();
		stateController = new StateController();
	}

	private boolean init() {
		if (!graphicsController.init(this, FRAME_WIDTH, FRAME_HEIGHT) || !inputController.init(this)
				|| !stateController.init(this))
			return false;

		return true;
	}

	@Override
	public void run() {
		if (!init())
			return;

		// Inicializar estado
		stateController.pushState(new GameState(this));

		while (true) {
			updateDeltaTime();
			stateController.currentState().handleInput(inputController.getUserEvents());
			stateController.currentState().update(deltaTime);
			paint();
		}
	}

	private void updateDeltaTime() {
		currentTime = System.nanoTime();
		long nanoElapsedTime = currentTime - lastFrameTime;
		lastFrameTime = currentTime;
		deltaTime = (double) nanoElapsedTime / 1.0E9;
	}

	private void paint() {
		// TEST
		Cell cell = new Cell(FRAME_WIDTH - graphicsController.getWidth() + 20,
				FRAME_HEIGHT - graphicsController.getHeight() + 20, 40, this);

		// Bucle principal de renderizado
		do {
			do {
				graphicsController.prepareFrame();
				try {
					// Render de la logica
					stateController.currentState().render(graphicsController);
				}

				finally {
					graphicsController.dispose();
				}
			} while (graphicsController.getFrame().getBufferStrategy().contentsRestored());
			graphicsController.getFrame().getBufferStrategy().show();
		} while (graphicsController.getFrame().getBufferStrategy().contentsLost());
	}
}
