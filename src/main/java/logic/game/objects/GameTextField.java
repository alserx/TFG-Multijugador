package logic.game.objects;

import java.awt.Point;
import java.util.List;

import controller.GraphicsController;
import logic.enums.UserEvent;
import lombok.Getter;
import lombok.Setter;

public class GameTextField extends AbstractGameObject {
	@Getter
	@Setter
	private String text;
	private final int width;
	private final int height;
	private final int backgroundColor;
	private final int foregroundColor;
	private final int borderColor;
	private final int fontSize;
	private final double cursorTime;
	private double timer;
	private int cursorColor;

	@Getter
	private boolean focused;

	public GameTextField(int x, int y, int width, int height, int backgroundColor, int foregroundColor, int borderColor,
			int fontSize) {
		this.position = new Point(x, y);
		this.width = width;
		this.height = height;
		this.backgroundColor = backgroundColor;
		this.foregroundColor = foregroundColor;
		this.borderColor = borderColor;
		this.cursorColor = foregroundColor;
		this.fontSize = fontSize;
		this.text = "";
		this.focused = false;
		this.cursorTime = 0.5;
	}

	@Override
	public void update(double deltaTime) {
		timer += deltaTime;

		if (timer >= cursorTime) {
			cursorColor = swapCursorColor(cursorColor);
			timer = 0;
		}
	}

	@Override
	public void render(GraphicsController graphics) {
		if (this.isActive()) {
			// Render background and border
			graphics.drawBorderedRect(backgroundColor, borderColor, position.x, position.y, width, height, 2);

			// Render text
			int textWidth = graphics.getStringWidth(text, fontSize);
			int textHeight = graphics.getStringHeight(fontSize);
			int textX = position.x + 5; // Adjust text padding
			int textY = position.y + (height - textHeight) / 2 + textHeight / 2;

			graphics.drawText(text, foregroundColor, textX, textY, fontSize);

			// Render cursor if focused
			if (focused) {
				int cursorX = textX + textWidth;
				int cursorY = position.y + (height - textHeight) / 2;
				graphics.drawLine(cursorColor, cursorX, cursorY, cursorX, cursorY + textHeight);
			}
		}
	}

	@Override
	public void handleInput(List<UserEvent> userEvents) {
		if (this.isActive()) {
			for (UserEvent event : userEvents) {
				if (event == UserEvent.CLICK) {
					focused = isMouseInside(event.getX(), event.getY());
				}
				if (event == UserEvent.KEY_TYPED && focused) {
					handleTextInput(event.getKeyChar());
				}
			}
		}
	}

	private boolean isMouseInside(int mouseX, int mouseY) {
		return mouseX >= position.x && mouseX <= position.x + width && mouseY >= position.y
				&& mouseY <= position.y + height;
	}

	private void handleTextInput(char inputChar) {
		if (inputChar == '\b') { // Handle backspace
			if (!text.isEmpty()) {
				text = text.substring(0, text.length() - 1);
			}
		} else if (Character.isAlphabetic(inputChar) || Character.isDigit(inputChar) || inputChar == ' ') {
			text += inputChar;
		}
	}

	private int swapCursorColor(int color) {
		if (color == foregroundColor) {
			cursorColor = 0x00000000;
		} else {
			cursorColor = foregroundColor;
		}

		return cursorColor;
	}
}
