package Entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Zombie extends GameObjects {
	
	BufferedImage zombie;
	BufferedImage zombie1;

	public int A;
	private String facing = "rt";
	public int health = 1;

	public Zombie(double x, double y) {
		
		super(x, y);
		
		try {
			
			zombie = ImageIO.read(getClass().getResourceAsStream("/Characters/zombieLR.png"));
			zombie1 = ImageIO.read(getClass().getResourceAsStream("/Characters/zombieUD.png"));
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getHealth() {
		return health;
	}
	
	public Rectangle getBounds() {
		//40x40
		return new Rectangle((int)x, (int)y, 40, 40);
	}
	
	public void moveTowards(Player player) {
	    
		turnTowards(player);
			
		double ux = Lookup.cos[A];
		double uy = Lookup.sin[A];
	
		double vx = player.x - x;
		double vy = player.y - y;
	
		double d = vx*ux + vy*uy;
	
	
		if ( d > 20)
			moveForwardBy(3);
		
	}
	
	public void moveForwardBy(int distance) {
		x += distance * Math.cos(A*Math.PI/180);
	    y += distance * Math.sin(A*Math.PI/180);
		
	}
	
	public void turnTowards(Player player)
	   {
	      double ux = Lookup.cos[A];
	      double uy = Lookup.sin[A];

	      double nx = -uy;
	      double ny = ux;

	      double vx = player.x - x;
	      double vy = player.y - y;

	      double d = vx*nx + vy*ny;

	      if(d > 10)
	      {
	          A += 3;
	          if (A >= 360)  A -= 360;
	      }
	      if(d < -10)
	      {
	         A -= 3;
	         if(A < 0)  A += 360;
	      }

	   }
	
	public void update() {
		
		if(this.A < 45 || this.A >= 315)
			facing = "rt";
		if(this.A >= 135 && this.A < 225)
			facing = "lt";
		if(this.A >= 45 && this.A < 135 )
			facing = "up";
		if(this.A >= 225 && this.A < 315 )
			facing = "dn";
	}
	
	public void draw(Graphics g) {
		   
		if(facing.equals("lt")) {
			g.drawImage(zombie, (int)x + zombie.getWidth() - 5 , (int)y, -zombie.getWidth(), zombie.getHeight(), null);
		}
		else if(facing.equals("dn")) {
			g.drawImage(zombie1, (int)x, (int)y - 5, zombie1.getWidth(), zombie1.getHeight(), null);
		}
		else if(facing.equals("up")) {
			g.drawImage(zombie1, (int)x, (int)y + zombie1.getHeight(), zombie1.getWidth(), -zombie1.getHeight(), null);
		}
		else {
			g.drawImage(zombie, (int)x, (int)y, null);
			
		}
	      
	}
}
