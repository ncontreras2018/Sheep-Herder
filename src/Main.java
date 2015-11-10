import javax.swing.SwingUtilities;

import util.MyPoint;


public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new GamePanel());
	}
	
	public static double getAngleBetween(MyPoint p1, MyPoint p2) {
		
		double xDist = p2.getX() - p1.getX();
		
		double yDist = p2.getY() - p1.getY();
		
		return Math.atan(yDist / xDist);
		
	}

}
