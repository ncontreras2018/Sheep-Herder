import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.MyPoint;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

	private JFrame frame;

	private Dimension panelSize = new Dimension(600, 600);

	private int borderSize = 50;

	private ArrayList<Sheep> sheep = new ArrayList<Sheep>();

	private Player player;

	public GamePanel() {

		frame = new JFrame();

		frame.add(this);

		setPreferredSize(panelSize);
		
		frame.setTitle("Sheep Herding Simulator 2016");

		frame.pack();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);

		player = new Player(100, 100);

		frame.addKeyListener(player);

	}

	@Override
	public void run() {
		new GameThread(this).start();
		generateSheep(50);
		Sheep.setPlayer(player);
	}

	public void refresh() {

		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.RED);

		g2d.fillRect(0, 0, getWidth(), getHeight());

		g2d.setColor(Color.GREEN.darker().darker().darker());

		g2d.fillRect(borderSize, borderSize, getWidth() - borderSize * 2,
				getHeight() - borderSize * 2);

		for (Sheep s : sheep) {
			s.update();
			s.display(g2d);
		}

		player.update();
		player.display(g2d);
	}

	public MyPoint randomPointInPlay() {
		return new MyPoint((Math.random() * (getWidth() - borderSize * 2))
				+ borderSize, (Math.random() * (getWidth() - borderSize * 2))
				+ borderSize);
	}

	public boolean pointInBounds(MyPoint p) {
		if (p.getX() > borderSize
				&& p.getX() < panelSize.getWidth() - borderSize) {
			if (p.getY() > borderSize
					&& p.getY() < panelSize.getHeight() - borderSize) {
				return true;
			}
		}
		return false;
	}

	public MyPoint randomPointAtDist(MyPoint center, double radius) {

		MyPoint newPoint = new MyPoint(-1, -1);

		while (center.distance(newPoint) > radius) {

			newPoint = randomPointInPlay();
		}
		
		return newPoint;

	}

	private ArrayList<Sheep> generateSheep(int count) {

		for (int i = 0; i < count; i++) {

			boolean regenerateSheep = true;

			Sheep newSheep = null;

			while (regenerateSheep) {

				newSheep = new Sheep((int) (Math.random() * 400) + 100,
						(int) (Math.random() * 400) + 100, this);

				regenerateSheep = false;

				if (newSheep.getLocation().distance(player.getLocation()) < 100) {
					regenerateSheep = true;
					continue;
				}

				for (Sheep cur : sheep) {
					if (newSheep.touching(cur)) {
						regenerateSheep = true;
						continue;
					}
				}

			}

			sheep.add(newSheep);

		}

		return sheep;
	}

	private double getDist(int x1, int y1, int x2, int y2) {
		return MyPoint.distance(x1, y1, x2, y2);
	}

	public ArrayList<Sheep> getSheep() {
		return sheep;
	}
}
