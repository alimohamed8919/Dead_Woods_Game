package Entity;

import java.awt.Graphics;
import java.util.ArrayList;

public class BulletController {

	
	public ArrayList<Bullet> b = new ArrayList<Bullet>();
	
	Bullet TempBullet;
	
	
	public void update() {
		
		for(int i = 0; i < b.size(); i++) {
			TempBullet = b.get(i);
			
			if(TempBullet.getX() <= -400 || TempBullet.getX() >= 1650 ||
					TempBullet.getY() >= 1350 || TempBullet.getY() <= -215) {
				removeBullet(TempBullet);
			}
			
			TempBullet.update();
		}
	}
	
	public void addBullet(Bullet bullet) { b.add(bullet); }
	public void removeBullet(Bullet bullet) { b.remove(bullet); }
	
	public void draw(Graphics g) {
		for(int i = 0; i < b.size(); i++) {
			TempBullet = b.get(i);
			
			if(TempBullet != null)
			TempBullet.draw(g);
		}
		
	}
}
