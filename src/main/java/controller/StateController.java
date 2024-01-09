package controller;

import java.util.Stack;

import logic.states.MenuState;
import logic.states.State;

public class StateController {
	private Stack<State> states;
	private GameController gameController;

	public StateController() {
		states = new Stack<State>();
	}

	public boolean init(GameController gameController) {
		this.gameController = gameController;
		if (this.gameController == null)
			return false;

		states.push(new MenuState(this.gameController));

		return true;
	}

	public State currentState() {
		if (!states.isEmpty())
			return states.peek();

		return null;
	}

	public boolean popState() {
		if (!states.isEmpty()) {
			if (states.pop() != null)
				return true;
		}

		return false;
	}

	public boolean pushState(State newState) {
		if (states.push(newState) != null)
			return true;

		return false;
	}

	public void clearStates() {
		states.clear();
	}
}
