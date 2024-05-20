package controller;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.JTextField;

import lombok.Getter;
import view.GameFrame;

public class GraphicsController {
	// Referencia de la ventana
	@Getter
	private GameFrame frame;

	// Para pintar graficos con Swing
	@Getter
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
	public boolean init(GameController mainController, int width, int height) {
		Point centerPoint = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();

		frame.addMouseListener(mainController.getInputController());
		frame.addMouseMotionListener(mainController.getInputController());
		frame.addKeyListener(mainController.getInputController());

		int posX = centerPoint.x - width / 2;
		int posY = centerPoint.y - height / 2;

		frame.setLocation(posX, posY);
		boolean initResult = frame.init(width, height);

		return initResult;
	}

	/**
	 * Prepara el siguiente frame para su pintado
	 */
	public void prepareFrame() {
		graphics = (Graphics2D) frame.getStrategy().getDrawGraphics();
	}

	/**
	 * Libera la memoria utilizada
	 */
	public void dispose() {
		graphics.dispose();
	}

	/**
	 * 
	 * Dibuja un circulo
	 * 
	 * @param color       Color de la circunferencia
	 * @param x           Posicion en el eje X
	 * @param y           Posiciion en el eje Y
	 * @param diameter    Diametro del circulo
	 * @param strokeWidth Ancho de la linea de dibujado
	 */
	public void drawCircle(int color, int x, int y, int diameter, int strokeWidth) {
		graphics.setColor(new Color(color));
		graphics.setStroke(new BasicStroke(strokeWidth));

		graphics.drawOval(x, y, diameter, diameter);
	}

	/**
	 * Dibuja un cuadrado
	 * 
	 * @param color       Color de la linea
	 * @param x           Posicion en el eje X
	 * @param y           Posicion en el eje Y
	 * @param side        Tamaño del lado del cuadrado
	 * @param strokeWidth Ancho de la linea de dibujado
	 */
	public void drawSquare(int color, int x, int y, int side, int strokeWidth) {
		graphics.setColor(new Color(color));
		graphics.setStroke(new BasicStroke(strokeWidth));

		graphics.drawRect(x, y, side, side);
	}

	/**
	 * Dibuja un rectangulo
	 * 
	 * @param color       Color de la linea
	 * @param x           Posicion en el eje X
	 * @param y           Posicion en el eje Y
	 * @param side        Tamaño del lado del cuadrado
	 * @param strokeWidth Ancho de la linea de dibujado
	 */
	public void drawRect(int color, int x, int y, int width, int height, int strokeWidth) {
		graphics.setColor(new Color(color));
		graphics.setStroke(new BasicStroke(strokeWidth));

		graphics.drawRect(x, y, width, height);
	}

	public void drawBorderedRect(int backgroundColor, int borderColor, int x, int y, int width, int height,
			int strokeWidth) {
		graphics.setColor(new Color(backgroundColor));
		graphics.fillRect(x, y, width, height);

		drawRect(borderColor, x, y, width, height, strokeWidth);
	}

	/**
	 * Dibuja una cruz
	 * 
	 * @param color       Color de la linea
	 * @param x           Posicion en el eje X
	 * @param y           Posicion en el eje Y
	 * @param size        Tamaño de la cruz
	 * @param strokeWidth Ancho de la linea de dibujado
	 */
	public void drawCross(int color, int x, int y, int size, int strokeWidth) {
		graphics.setColor(new Color(color));
		graphics.setStroke(new BasicStroke(strokeWidth));

		graphics.drawLine(x, y, x + size, y + size);
		graphics.drawLine(x, y + size, x + size, y);
	}

	public void drawLine(int color, int x1, int y1, int x2, int y2) {
		graphics.setColor(new Color(color));
		graphics.setStroke(new BasicStroke(1));

		graphics.drawLine(x1, y1, x2, y2);
	}

	int getWidth() {
		return frame.getPanelWidth();
	}

	int getHeight() {
		return frame.getPanelHeight();
	}

	public void drawText(String text, int color, int x, int y, int size) {
		graphics.setColor(new Color(color));

		setFontSize((float) size);

		graphics.drawString(text, x, y);
	}

	public void setFontSize(float size) {
		Font resizedFont = graphics.getFont().deriveFont(size);
		graphics.setFont(resizedFont);
	}

	public int getStringWidth(String text, int fontSize) {
		setFontSize((float) fontSize);
		return graphics.getFontMetrics().stringWidth(text);
	}

	public int getStringHeight(int fontSize) {
		setFontSize((float) fontSize);
		return graphics.getFontMetrics().getHeight();
	}

	public void clear() {
		graphics.clearRect(0, 0, getWidth(), getHeight());
	}

}
