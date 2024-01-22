package logic.states;

import java.util.ArrayList;
import java.util.List;

import controller.GameController;
import controller.GraphicsController;
import logic.GameObject;
import logic.enums.UserEvent;

public class MenuState implements State {
	private GameController gameController;
	private ArrayList<GameObject> objects;

	public MenuState(GameController gameController) {
		this.gameController = gameController;
		objects = new ArrayList<GameObject>();
	}

	@Override
	public void update(double deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GraphicsController graphics) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleInput(List<UserEvent> userEvents) {
		// TODO Auto-generated method stub

	}

}
