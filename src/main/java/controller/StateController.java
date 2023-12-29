package controller;

import java.util.Stack;

import logic.states.State;
import lombok.Getter;

public class StateController {
	@Getter
	private Stack<State> states;

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
