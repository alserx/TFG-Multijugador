package logic.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * User input events
 */
public enum UserEvent {
	CLICK, MOUSE_MOVED, KEY_TYPED;

	@Getter
	@Setter
	private int x, y;

	@Getter
	@Setter
	private char keyChar;
}
