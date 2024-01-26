package controller;

import logic.states.GameState;
import lombok.Getter;
import lombok.Setter;

/**
 * The main Controller, it manages all the game loop: handle input, update and
 * render
 */
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

	/**
	 * Initializes the rest of the modules to get the game running
	 * 
	 * @return true if the initialization is successful, false in other case
	 */
	private boolean init() {
		if (!graphicsController.init(this, FRAME_WIDTH, FRAME_HEIGHT) || !inputController.init(this)
				|| !stateController.init(this))
			return false;

		return true;
	}

	@Override
	public void run() {
		// Inicia el controllador si no funciona acaba el juego
		if (!init())
			return;

		// Inicializar estado
		stateController.pushState(new GameState(this));

		// Bucle principal
		while (true) {

			updateDeltaTime();

			// Llama al input del estado
			stateController.currentState().handleInput(inputController.getUserEvents());

			// Actualiza el estado
			stateController.currentState().update(deltaTime);

			// Pinta el estado usando una estrategia de doble bufer
			paint();
		}
	}

	/**
	 * Calculate the frame rate
	 */
	private void updateDeltaTime() {
		currentTime = System.nanoTime();
		long nanoElapsedTime = currentTime - lastFrameTime;
		lastFrameTime = currentTime;
		deltaTime = (double) nanoElapsedTime / 1.0E9;
	}

	/**
	 * Paint the current game state
	 */
	private void paint() {
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
