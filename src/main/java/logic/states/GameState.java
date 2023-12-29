package logic.states;

import logic.game.objects.Board;

public class GameState implements State {

	private Board board;

	public GameState() {

	}

	@Override
	public void update() {
		board.update();

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}

}
