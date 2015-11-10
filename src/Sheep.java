import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import util.MyPoint;
import util.MyPoly;

public class Sheep {
	
	private double dir;

	private MyPoly body;
	
	private MyPoint targLocation;

	private GamePanel gamePanel;

	public Sheep(int x, int y, double dir, GamePanel gp) {

		body = new MyPoly(
				new MyPoint[] { new MyPoint(-10, -20), new MyPoint(10, -20),
						new MyPoint(10, 20), new MyPoint(-10, 20) });
		
		body.translate(x, y);
		
		body.rotateDegrees(dir);

		this.dir = dir;
		this.gamePanel = gp;
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
	
	public void wander() {
	}
}
