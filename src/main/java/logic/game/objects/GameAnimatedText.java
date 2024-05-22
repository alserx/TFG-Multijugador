package logic.game.objects;

import java.awt.Point;
import java.util.List;

import controller.GraphicsController;
import logic.enums.UserEvent;

public class GameAnimatedText extends AbstractGameObject {
	private String text;
	private int color;
	private int fontSize;
	private double time;
	private double speedFactor;

	public GameAnimatedText(String text, int x, int y, int color, int fontSize, double speedFactor) {
		this.text = text;
		this.position = new Point(x, y);
		this.color = color;
		this.fontSize = fontSize;
		this.time = 0;
		this.speedFactor = speedFactor;
	}

	@Override
	public void update(double deltaTime) {
		time += deltaTime * speedFactor;
	}

	@Override
	public void render(GraphicsController graphics) {
		// Calculate the alpha value for the waving effect
		int alpha = (int) ((Math.sin(time) * 0.5 + 0.5) * 255);
		int animatedColor = (alpha << 24) | (color & 0x00FFFFFF);

		graphics.drawText(text, animatedColor, position.x, position.y, fontSize);
	}

	@Override
	public void handleInput(List<UserEvent> userEvents) {
		return;
	}

}
