package logic.states;

import java.util.ArrayList;

import controller.GameController;
import controller.GraphicsController;
import logic.GameObject;
import logic.game.objects.Board;

public class GameState implements State {

	private GameController gameController;
	private ArrayList<GameObject> objects;
	private Board board;

	public GameState(GameController gameController) {
		this.gameController = gameController;
		objects = new ArrayList<GameObject>();
		board = new Board(300 - 100, 150, 200);
	}

	@Override
	public void update(double deltaTime) {
		board.update(deltaTime);

		objects.forEach(o -> o.update(deltaTime));

	}

	@Override
	public void render(GraphicsController graphics) {
		board.render(graphics);

		objects.forEach(o -> o.render(graphics));

	}

	@Override
	public void handleInput() {
		objects.forEach(o -> o.handleInput());
	}

}
