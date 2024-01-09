package logic;

import controller.GraphicsController;

public interface GameObject {
	/**
	 * Update the logic of the actual object
	 */
	public void update(double deltaTime);

	/**
	 * Render all the elements of the actual object
	 */
	public void render(GraphicsController graphics);

	/**
	 * Handle the input of the actual object
	 */
	public void handleInput();
}
