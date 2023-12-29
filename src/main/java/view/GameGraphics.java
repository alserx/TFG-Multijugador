package view;

import java.awt.Graphics2D;

import controller.GameController;
import lombok.Getter;

public class GameGraphics {
	@Getter
	private GameFrame frame;

	private Graphics2D graphics;

	public GameGraphics() {
		frame = new GameFrame("TicTacToe");
	}

	public boolean init(GameController mainController) {

		frame.addMouseListener(mainController.getInputController());
		frame.addMouseMotionListener(mainController.getInputController());

		return frame.init(600, 480);
	}

	public void prepareFrame() {
		// Espera hasta que se genere un BufferStrategy
		while (frame.getBufferStrategy() == null)
			;

		graphics = (Graphics2D) frame.getBufferStrategy().getDrawGraphics();

	}

	public void dispose() {
		graphics.dispose();
	}
}
