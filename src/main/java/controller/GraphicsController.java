package controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import lombok.Getter;
import view.GameFrame;

public class GraphicsController {
	// Referencia de la ventana
	@Getter
	private GameFrame frame;

	// Para pintar graficos con Swing
	private Graphics2D graphics;

	/**
	 * Constructor, crea un nuevo frame
	 */
	public GraphicsController() {
		frame = new GameFrame("TicTacToe");
	}

	/**
	 * Inicia el modulo y genera la ventana con el tamaño asignado
	 * 
	 * @param mainController referencia al controlador del juego
	 * @return true si ha salido todo bien, false en caso contrario
	 */
	public boolean init(GameController mainController) {

		frame.addMouseListener(mainController.getInputController());
		frame.addMouseMotionListener(mainController.getInputController());

		return frame.init(600, 480);
	}

	/**
	 * Prepara el siguiente frame para su pintado
	 */
	public void prepareFrame() {
//		// Espera hasta que se genere un BufferStrategy
//		while (frame.getBufferStrategy() == null)
//			;

		graphics = (Graphics2D) frame.getStrategy().getDrawGraphics();

	}

	/**
	 * Libera la memoria utilizada
	 */
	public void dispose() {
		graphics.dispose();
	}

	/**
	 * Dibuja un circulo
	 * 
	 * @param color    Color de la circunferencia
	 * @param x        Posicion en el eje X
	 * @param y        Posiciion en el eje Y
	 * @param diameter Diametro del circulo
	 */
	public void drawCircle(int color, int x, int y, int diameter, int strokeWidth) {
		graphics.setColor(new Color(color));
		graphics.setStroke(new BasicStroke(strokeWidth));

		graphics.drawOval(x, y, diameter, diameter);
	}

	public void drawSquare(int color, int x, int y, int side, int strokeWidth) {
		graphics.setColor(new Color(color));
		graphics.setStroke(new BasicStroke(strokeWidth));

		graphics.drawRect(x, y, side, side);
	}

	public void drawCross(int color, int x1, int y1, int x2, int y2, int strokeWidth) {
		graphics.setColor(new Color(color));
		graphics.setStroke(new BasicStroke(strokeWidth));

		graphics.drawLine(x1, y1, x2, y2);
		graphics.drawLine(x1, y2, x2, y1);
	}
}
