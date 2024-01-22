package logic.states;

import java.util.List;

import controller.GraphicsController;
import logic.enums.UserEvent;

public interface State {
	/**
	 * Update the logic of the actual state
	 */
	public void update(double deltaTime);

	/**
	 * Render all the elements of the actual state
	 */
	public void render(GraphicsController graphics);

	/**
	 * Handle the input of the actual state
	 */
	public void handleInput(List<UserEvent> userEvents);
}
