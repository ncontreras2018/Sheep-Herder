import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import util.MyPoint;
import util.MyPoly;

public class Sheep {

	private MyPoly body;

	private MyPoint targLocation;

	private GamePanel gamePanel;

	private static double wanderSpeed = 1, runningSpeed = 2.5;

	private static Player player;

	private boolean runningFromPlayer = false;

	private boolean atTargLocation = true;

	public static void setPlayer(Player p) {
		player = p;
	}

	public Sheep(int x, int y, GamePanel gp) {

		body = new MyPoly(
				new MyPoint[] { new MyPoint(-10, -20), new MyPoint(10, -20),
						new MyPoint(10, 20), new MyPoint(-10, 20) });

		body.translate(x, y);
		this.gamePanel = gp;

		this.targLocation = new MyPoint(body.getCenter().getX(), body
				.getCenter().getY());
	}

	public void display(Graphics2D g2d) {

		g2d.setColor(Color.LIGHT_GRAY);

		g2d.fill(body);
	}

	public boolean touching(Sheep other) {

		return this.body.intersects(other.body);

	}

	public MyPoint getLocation() {
		return body.getCenter();
	}

	public void update() {

		if (atTargLocation) {
			wander();
		}

		if (body.getCenter().distance(player.getLocation()) < 150) {
			runningFromPlayer = true;
			runFromPlayer();
		}

		moveToTargetLocation();

	}

	private void moveToTargetLocation() {

		double curSpeed = runningFromPlayer ? runningSpeed : wanderSpeed;

		double dist = body.getCenter().distance(targLocation);

		if (dist <= curSpeed * 2) {
			body.moveTo(targLocation.getX(), targLocation.getY());
			atTargLocation = true;
			runningFromPlayer = false;
			return;
		}

		double deltaX = 0;
		double deltaY = 0;

		double xDist = body.getCenter().getX() - targLocation.getX();
		double yDist = body.getCenter().getY() - targLocation.getY();

		if (Math.abs(xDist) > curSpeed) {
			deltaX = -(xDist / Math.abs(xDist)) * curSpeed;
		}

		if (Math.abs(yDist) > curSpeed) {
			deltaY = -(yDist / Math.abs(yDist)) * curSpeed;
		}

		ArrayList<Sheep> curInside = new ArrayList<Sheep>();

		for (Sheep curSheep : gamePanel.getSheep()) {
			if (!curSheep.equals(this) && curSheep.touching(this)) {
				curInside.add(curSheep);
			}
		}

		body.translate(deltaX, deltaY);

		double angle = Main.getAngleBetween(new MyPoint(body.getCenter().getX()
				+ deltaX, body.getCenter().getY() + deltaY), body.getCenter())
				+ Math.PI / 2;

		double angleBefore = body.getAngle();

		body.setAngle(angle);

		for (Sheep curSheep : gamePanel.getSheep()) {
			if (!curSheep.equals(this) && curSheep.touching(this)) {
				if (!curInside.contains(curSheep)) {
					body.translate(-deltaX, -deltaY);
					body.setAngle(angleBefore);

					if (runningFromPlayer) {
						if (Math.random() < .3) {
							curSheep.changeTarget();
						}
					} else {
						if (Math.random() < .075) {
							curSheep.changeTarget();
						}
					}
				}
			}
		}
	}

	private void wander() {
		if (Math.random() < .005) {

			targLocation = getHerdTrend(.08);

			if (targLocation == null) {
				targLocation = gamePanel.randomPointInPlay();
			}

			atTargLocation = false;
		}
	}

	private void runFromPlayer() {

		atTargLocation = false;

		MyPoint herdTrend = getHerdTrend(.05);

		if (herdTrend == null) {
			while (targLocation.distance(player.getLocation()) < 200) {
				targLocation = gamePanel.randomPointInPlay();
			}
		} else {
			targLocation = herdTrend;
		}
	}

	private MyPoint getHerdTrend(double herdFactor) {

		MyPoint trend = null;

		ArrayList<Sheep> sheep = gamePanel.getSheep();

		for (Sheep curSheep : sheep) {
			if (!curSheep.equals(this) && !curSheep.atTargLocation) {
				if (Math.random() < herdFactor) {
					trend = gamePanel.randomPointAtDist(curSheep.targLocation,
							50);
				}
			}
		}
		return trend;
	}

	private void changeTarget() {
		targLocation = gamePanel.randomPointInPlay();
	}
}
