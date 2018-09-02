package Entity;

import java.awt.Graphics;
import java.util.ArrayList;

public class HordeController implements Runnable {
	
	public ArrayList<Zombie> z = new ArrayList<Zombie>();
	public int size = 0;
	
	private int spawnX = 0;
	private int spawnY = 0;
	
	public Thread t;
	
	Zombie TempZombie;
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		
		while(size <= 100) {
			spawnPoints();
			addZombie(new Zombie(spawnX,spawnY));
			System.out.println(spawnX + " " + spawnY);
			size++;
			
			System.out.println(size);
			try
	         {
	             t.sleep(1000);
	         }
	         catch(Exception x){}
	      }
		
		
	}
	
	public HordeController() {
		
		t = new Thread(this);
		t.start();
		
	}
	
	private void spawnPoints() {
		//1780 - 1580 <= x <= -320 - -520
		//-315 - -115 >= y >= 1280 - 1480
		
		int tempX1 = (int) (1780 + Math.random() * 500);
		int tempX2 = (int) (-720 + Math.random() * 500);
		int tempY1 = (int) (1680 + Math.random() * 500);
		int tempY2 = (int) (-515 + Math.random() * 500);
		
		int chooseX = (int) (1 + Math.random() * 2);
		int chooseY = (int) (1 + Math.random() * 2);
		
		if (chooseX == 1) spawnX = tempX1;
		else spawnX = tempX2;
		
		if(chooseY == 1) spawnY = tempY1;
		else spawnY = tempY2;
		
		
	}

	public void update() {
		
		for(int i = 0; i < z.size(); i++) {
			TempZombie = z.get(i);
			
			if(TempZombie.health <= 0) {
				removeZombie(TempZombie);
				size--;
			}
			
			TempZombie.update();
		}
	}
	
	public void addZombie(Zombie zombie) { z.add(zombie); }
	public void removeZombie(Zombie zombie) { z.remove(zombie); }
	
	public void draw(Graphics g) {
		for(int i = 0; i < z.size(); i++) {
			TempZombie = z.get(i);
			
			TempZombie.draw(g);
		}
		
	}

}
