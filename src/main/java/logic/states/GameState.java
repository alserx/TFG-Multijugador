package logic.states;

import java.util.ArrayList;
import java.util.List;

import controller.GameController;
import controller.GraphicsController;
import logic.GameObject;
import logic.enums.UserEvent;
import logic.game.objects.Board;

public class GameState implements State {

	private ArrayList<GameObject> objects;

	public GameState(GameController gameController) {
		objects = new ArrayList<GameObject>();
		Board board = new Board(gameController.getFRAME_WIDTH() / 2, gameController.getFRAME_HEIGHT() / 2,
				gameController);

		objects.add(board);
	}

	@Override
	public void update(double deltaTime) {
		objects.forEach(o -> o.update(deltaTime));

	}

	@Override
	public void render(GraphicsController graphics) {
		objects.forEach(o -> o.render(graphics));

	}

	@Override
	public void handleInput(List<UserEvent> userEvents) {
		objects.forEach(o -> o.handleInput(userEvents));
	}

}
