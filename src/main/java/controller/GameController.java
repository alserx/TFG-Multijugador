package controller;

import java.awt.Graphics2D;

import lombok.Getter;
import view.GameGraphics;

public class GameController implements Runnable {
	// Controllers
	@Getter
	private StateController stateController;
	@Getter
	private InputController inputController;

	// View
	@Getter
	private GameGraphics gameGraphics;

	// Time
	private long lastFrameTime = 0;
	private long currentTime = 0;
	private double deltaTime = 0;

	public GameController() {
		gameGraphics = new GameGraphics();
		inputController = new InputController();

	}

	private boolean init() {
		if (!gameGraphics.init(this))
			return false;

		return true;
	}

	@Override
	public void run() {
		if (!init())
			return;

		while (true) {
			updateDeltaTime();
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
		// Bucle principal de renderizado
		do {
			do {
				gameGraphics.prepareFrame();
				try {
					// Render de la logica
				}

				finally {
					gameGraphics.dispose();
				}
			} while (gameGraphics.getFrame().getBufferStrategy().contentsRestored());
			gameGraphics.getFrame().getBufferStrategy().show();
		} while (gameGraphics.getFrame().getBufferStrategy().contentsLost());
	}

}
