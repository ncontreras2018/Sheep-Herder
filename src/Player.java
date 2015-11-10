import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import util.MyPoint;

public class Player implements KeyListener {

	private MyPoint location;

	private boolean up, down, left, right;

	private double speed = 3;

	private int radius = 11;

	public Player(double x, double y) {
		location = new MyPoint(x, y);
	}

	public void update() {
		move();
	}

	private void move() {

		double diagMove = Math.sqrt(.5) * speed;

		if (down && right) {
			location.translate(diagMove, diagMove);
		} else if (up && right) {
			location.translate(diagMove, -diagMove);
		} else if (up && left) {
			location.translate(-diagMove, -diagMove);
		} else if (down && left) {
			location.translate(-diagMove, diagMove);
		} else if (down) {
			location.translate(0, speed);
		} else if (up) {
			location.translate(0, -speed);
		} else if (left) {
			location.translate(-speed, 0);
		} else if (right) {
			location.translate(speed, 0);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyChar() == 'w') {
			up = true;
		}
		if (e.getKeyChar() == 's') {
			down = true;
		}
		if (e.getKeyChar() == 'a') {
			left = true;
		}
		if (e.getKeyChar() == 'd') {
			right = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 'w') {
			up = false;
		}
		if (e.getKeyChar() == 's') {
			down = false;
		}
		if (e.getKeyChar() == 'a') {
			left = false;
		}
		if (e.getKeyChar() == 'd') {
			right = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public void display(Graphics2D g2d) {
		g2d.setColor(Color.DARK_GRAY);

		g2d.fillOval(location.x() - radius, location.y() - radius, radius * 2,
				radius * 2);
	}
	
	public MyPoint getLocation() {
		return location;
	}

}
