package logic.states;

public interface State {
	/**
	 * Update the logic of the actual state
	 */
	public void update();

	/**
	 * Render all the elements of the actual state
	 */
	public void render();

	/**
	 * Handle the input of the actual state
	 */
	public void handleInput();
}
