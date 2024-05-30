package controller;

import connection.GameClient;
import logic.states.MenuState;
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
	@Getter
	private GameClient gameClient;

	// View
	@Getter
	private final int FRAME_WIDTH = 800, FRAME_HEIGHT = 600;

	// Time
	private long lastFrameTime = 0;
	private long currentTime = 0;
	private double deltaTime = 0;

	// Game loop
	@Getter
	@Setter
	private boolean running;
	@Getter
	@Setter
	private boolean playing;

	public GameController() {
		graphicsController = new GraphicsController();
		inputController = new InputController();
		stateController = new StateController();
		gameClient = new GameClient("127.0.0.1", 4000);
	}

	/**
	 * Initializes the rest of the modules to get the game running
	 * 
	 * @return true if the initialization is successful, false in other case
	 */
	private boolean init() {
		if (!graphicsController.init(this, FRAME_WIDTH, FRAME_HEIGHT) || !inputController.init(this)
				|| !stateController.init(this) || !gameClient.init(this))
			return false;
		return true;
	}

	@Override
	public void run() {
		// Inicia el controllador si no funciona acaba el juego
		if (!init())
			return;

		paint();

		// Inicializar estado
		stateController.pushState(new MenuState(this));
		running = true;

		// Bucle principal
		while (running) {

			updateDeltaTime();

			// Actualiza el estado
			stateController.currentState().update(deltaTime);

			// Pinta el estado usando una estrategia de doble bufer
			paint();

			// Llama al input del estado
			stateController.currentState().handleInput(inputController.getUserEvents());
		}

		stateController.clearStates();
		graphicsController.getFrame().setVisible(false);
		graphicsController.getFrame().dispose();
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
					graphicsController.clear();

					// Render de la logica
					if (stateController.currentState() != null)
						stateController.currentState().render(graphicsController);
				}

				finally {
					graphicsController.dispose();
				}
			} while (graphicsController.getFrame().getBufferStrategy().contentsRestored());
			graphicsController.getFrame().getBufferStrategy().show();
		} while (graphicsController.getFrame().getBufferStrategy().contentsLost());
	}

	public void onMessageReceived(String message) {
		System.out.println("Received message from server: [" + message + "]");

		// Send message to the current state
		stateController.currentState().receiveMessage(message);
	}
}
