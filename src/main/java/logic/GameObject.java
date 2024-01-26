package logic;

import java.util.List;

import controller.GraphicsController;
import logic.enums.UserEvent;

public interface GameObject {
	/**
	 * Update the logic of the actual object
	 * 
	 * @param deltaTime the current game deltaTime
	 */
	public void update(double deltaTime);

	/**
	 * Render all the elements of the actual object
	 * 
	 * @param graphics The actual Swing graphics Component
	 */
	public void render(GraphicsController graphics);

	/**
	 * Handle the input of the actual object
	 * 
	 * @param userEvents the input made by the user
	 */
	public void handleInput(List<UserEvent> userEvents);
}
