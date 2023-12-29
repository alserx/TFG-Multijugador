package logic.game.objects;

import lombok.Getter;

public class Board {

	@Getter
	private Cell[][] cells;

	public Board() {
		cells = new Cell[3][3];
	}

	public void update() {
		for (int row = 0; row < cells.length; row++) {
			for (int col = 0; col < cells.length; col++) {
				cells[row][col].update();
			}
		}
	}

	public void render() {
		for (int row = 0; row < cells.length; row++) {
			for (int col = 0; col < cells.length; col++) {
				cells[row][col].render();
			}
		}
	}
}
