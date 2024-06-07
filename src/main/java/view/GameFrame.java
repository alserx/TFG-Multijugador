package view;

import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import lombok.Getter;

public class GameFrame extends JFrame {
	/**
	 * Default
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	private BufferStrategy strategy;

	public GameFrame(String title) {
		super(title);

	}

	/**
	 * Creates a new Frame with the given parameters
	 * 
	 * @param width  Frame width
	 * @param height Frame height
	 * @return true if successful initialization
	 */
	public boolean init(int width, int height) {
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
		JFrame.setDefaultLookAndFeelDecorated(true);
		setResizable(false);

		// Renderizado activo, le decimos cuando se pinta
		setIgnoreRepaint(true);

		if (!createBufferStrategy())
			return false;

		return true;
	}

	/**
	 * Creates a double buffer render strategy
	 * 
	 * @return true if the strategy is created, false in any other case
	 */
	private boolean createBufferStrategy() {
		int tries = 100;
		while (tries-- > 0) {
			try {
				this.createBufferStrategy(2);
				break;
			} catch (Exception e) {
				return false;
			}
		}
		if (tries <= 0) {
			System.err.println("No pude crear la BufferStrategy");
			return false;
		}

		strategy = this.getBufferStrategy();
		return true;
	}

	/**
	 * @return The panel width
	 */
	public int getPanelWidth() {
		return (int) getContentPane().getSize().getWidth();
	}

	/**
	 * @return The panel height
	 */
	public int getPanelHeight() {
		return (int) getContentPane().getSize().getHeight();
	}

}
