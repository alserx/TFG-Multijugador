package controller;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.MouseInputListener;

import logic.enums.UserEvent;

public class InputController implements MouseInputListener {
	private GameController gameController;
	private ArrayList<UserEvent> events;

	public boolean init(GameController gameController) {
		this.gameController = gameController;
		events = new ArrayList<UserEvent>();

		if (this.gameController == null)
			return false;

		return true;
	}

	public synchronized List<UserEvent> getUserEvents() {
		if (!events.isEmpty()) {
			List<UserEvent> userEvents = new ArrayList<>(events);
			events.clear();
			return userEvents;
		} else
			return events;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		UserEvent currentEvent = UserEvent.CLICK;
		currentEvent.setX(e.getX());
		currentEvent.setY(e.getY());

		events.add(currentEvent);

		System.out.println("Click en X: " + e.getX() + " Y: " + e.getY());

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
