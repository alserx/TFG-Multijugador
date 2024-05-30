package controller;

import java.util.Stack;

import logic.states.State;

public class StateController {
	// State stack
	private Stack<State> states;

	// GameController reference
	private GameController gameController;

	public StateController() {
		states = new Stack<State>();
	}

	/**
	 * Initializes the StateController and pushes the first State of the game
	 * 
	 * @param gameController GameController reference to get the necessary data to
	 *                       initialize correctly the States.
	 * 
	 * @return true if the initialization is correct, false in other case
	 */
	public boolean init(GameController gameController) {
		this.gameController = gameController;
		if (this.gameController == null)
			return false;

		return true;
	}

	/**
	 * 
	 * @return The current State
	 */
	public State currentState() {
		if (!states.isEmpty())
			return states.peek();

		return null;
	}

	/**
	 * Pops out the current State
	 * 
	 * @return true if pop is successful
	 */
	public boolean popState() {
		if (!states.isEmpty()) {
			if (states.pop() != null)
				return true;
		}

		return false;
	}

	/**
	 * Push the new given State
	 * 
	 * @param newState the State to push in
	 * @return true if push is susccessful
	 */
	public boolean pushState(State newState) {
		if (states.push(newState) != null)
			return true;

		return false;
	}

	/**
	 * Clears all the States stack
	 */
	public void clearStates() {
		states.clear();
	}
}
