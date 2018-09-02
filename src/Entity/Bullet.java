package Entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Bullet extends GameObjects {

	BufferedImage bullet;
	BufferedImage bullet1;
	
	private String lastFacing = "rt";
	
	
	public Bullet(double x, double y, String facing) {
	
		super(x, y);
		
		try {
			
			bullet = ImageIO.read(getClass().getResourceAsStream("/Objects/bullet.png"));
			bullet1 = ImageIO.read(getClass().getResourceAsStream("/Objects/bullet1.png"));
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		lastFacing = facing;
	}

	
	public Rectangle getBounds() {
		//15x15
		return new Rectangle((int)x, (int)y, 15, 15);
	}
	
	public double getX() { return this.x; }
	public double getY() { return this.y; }
	
	public void update() {
		
		if(lastFacing.equals("lt")) {
			x -= 10;
		}
		if(lastFacing.equals("up")) {
			y -= 10;
		}
		if(lastFacing.equals("dn")) {
			y += 10;
		}
		if(lastFacing.equals("rt")) {
			x += 10;
		}
		
	}
	 
	public void draw(Graphics g) {
		
		
			
	
			if(lastFacing.equals("lt")) {
				g.drawImage(bullet1, (int)x-18, (int)y+17, -bullet1.getWidth(), bullet1.getHeight(), null);
			}
			else if(lastFacing.equals("up")) {
				g.drawImage(bullet, (int)x+18, (int)y-25, null);
			}
			else if(lastFacing.equals("dn")) {
				g.drawImage(bullet, (int)x+18, (int)y+55, bullet.getWidth(), -bullet.getHeight(), null);
			}
			else {
				g.drawImage(bullet1, (int)x+45, (int)y+17, null);
				
			}
		
		
		
	}
}
