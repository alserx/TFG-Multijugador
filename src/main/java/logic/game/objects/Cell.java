package logic.game.objects;

import logic.enums.CellState;
import lombok.Getter;
import lombok.Setter;

public class Cell {
	@Getter
	@Setter
	private CellState state;

	public Cell() {
		state = CellState.EMPTY;
	}

	public void update() {

	}

	public void render() {

	}
}
