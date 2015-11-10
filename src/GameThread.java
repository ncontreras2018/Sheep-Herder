
public class GameThread extends Thread {
	
	GamePanel panel;
	
	public GameThread(GamePanel gp) {
		this.panel = gp;
	}
	
	@Override
	public void run() {
		
		while (true) {
			panel.refresh();
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
