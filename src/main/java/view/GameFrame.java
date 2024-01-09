package view;

import java.awt.Dimension;
import java.awt.Insets;
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

	public boolean init(int width, int height) {
		setSize(width, height);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		JFrame.setDefaultLookAndFeelDecorated(true);

		// Renderizado activo: Nosotros le decimos cuando se pinta
		setIgnoreRepaint(true);

		if (!createBufferStrategy())
			return false;

		return true;
	}

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

	public int getPanelWidth() {
		return (int) getContentPane().getSize().getWidth();
	}

	public int getPanelHeight() {
		return (int) getContentPane().getSize().getHeight();
	}

}
