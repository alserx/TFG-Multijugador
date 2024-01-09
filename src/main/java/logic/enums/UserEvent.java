package logic.enums;

import lombok.Getter;
import lombok.Setter;

public enum UserEvent {
	CLICK;

	@Getter
	@Setter
	private int x, y;
}
