package logic.game.objects;

import java.awt.Point;
import java.util.List;

import controller.GraphicsController;
import logic.GameObject;
import logic.enums.UserEvent;
import lombok.Getter;
import lombok.Setter;

public class GameButton implements GameObject {
	@Getter
	@Setter
	private String text;
	@Getter
	@Setter
	private Point position;
	@Getter
	@Setter
	private int width;
	@Getter
	@Setter
	private int height;

	private int backgroundColor;
	private int hoverColor;
	private int textColor;
	private int fontSize = 14;

	private boolean hovered;

	@Setter
	private Runnable action;

	public GameButton(String text, int x, int y, int width, int height, int backgroundColor, int hoverColor,
			int foregroundColor) {
		this.text = text;
		this.position = new Point(x, y);
		this.width = width;
		this.height = height;
		this.backgroundColor = backgroundColor;
		this.textColor = foregroundColor;
		this.hoverColor = hoverColor;
		this.hovered = false;
	}

	public GameButton(String text, int x, int y, int width, int height, int backgroundColor, int hoverColor,
			int foregroundColor, int textSize) {
		this.text = text;
		this.position = new Point(x, y);
		this.width = width;
		this.height = height;
		this.backgroundColor = backgroundColor;
		this.textColor = foregroundColor;
		this.hoverColor = hoverColor;
		this.fontSize = textSize;
		this.hovered = false;
	}

	@Override
	public void update(double deltaTime) {
		return;
	}

	@Override
	public void render(GraphicsController graphics) {
		if (!hovered)
			graphics.drawBorderedRect(backgroundColor, textColor, position.x, position.y, width, height, 2);
		else
			graphics.drawBorderedRect(hoverColor, textColor, position.x, position.y, width, height, 2);

		int textWidth = graphics.getStringWidth(text, fontSize);
		int textHeight = graphics.getStringHeight(fontSize);
		int textX = position.x + (width - textWidth) / 2;
		int textY = position.y + (height - textHeight) / 2 + textHeight / 2;

		graphics.drawText(text, textColor, textX, textY, fontSize);
	}

	@Override
	public void handleInput(List<UserEvent> userEvents) {
		for (UserEvent event : userEvents) {
			if (event == UserEvent.MOUSE_MOVED) {
				hovered = mouseInside(new Point(event.getX(), event.getY()));
			} else if (event == UserEvent.CLICK && hovered) {
				performClick();
			}
		}
	}

	private void performClick() {
		if (action != null) {
			action.run();
		}
	}

	private boolean mouseInside(Point mousePoisition) {
		return mousePoisition.x >= position.x && mousePoisition.x <= position.x + width
				&& mousePoisition.y >= position.y && mousePoisition.y <= position.y + height;
	}

}
