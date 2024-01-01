package controller;

import java.awt.Graphics2D;

import logic.game.objects.Cell;
import lombok.Getter;

public class GameController implements Runnable {
	// Controllers
	@Getter
	private StateController stateController;
	@Getter
	private InputController inputController;

	// View
	@Getter
	private GraphicsController gameGraphics;

	// Time
	private long lastFrameTime = 0;
	private long currentTime = 0;
	private double deltaTime = 0;

	public GameController() {
		gameGraphics = new GraphicsController();
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
					cell.render(gameGraphics);

					gameGraphics.drawSquare(0xFF0000, 200, 200, 100, 10);
					gameGraphics.drawCircle(0x20FFFF, 205, 205, 90, 10);
				}

				finally {
					gameGraphics.dispose();
				}
			} while (gameGraphics.getFrame().getBufferStrategy().contentsRestored());
			gameGraphics.getFrame().getBufferStrategy().show();
		} while (gameGraphics.getFrame().getBufferStrategy().contentsLost());
	}

	// TEST
	Cell cell = new Cell();
}
