package logic.game.objects;

import java.awt.Point;
import logic.GameObject;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractGameObject implements GameObject {
	@Getter
	@Setter
	protected Point position;

	@Getter
	@Setter
	private boolean active = true;
}
