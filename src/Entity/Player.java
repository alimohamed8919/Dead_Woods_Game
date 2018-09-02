package Entity;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

//import Audio.AudioPlayer;

public class Player extends GameObjects {

	BufferedImage player;
	BufferedImage player1;
	
	//private AudioPlayer running;
	
	private int health = 150;
	
	private boolean moveLeft;
	private boolean moveRight;
	private boolean moveUp;
	private boolean moveDown;
	private boolean strafing;
	private boolean flinching;
	private boolean dead = false;
	private long flinchTimer;
	private String lastFacing = "rt";
	
	
	public Player(double x, double y) {
	
		super(x, y);
		
		//running = new AudioPlayer("/SFX/running.wav");
	
		try {
			
			player = ImageIO.read(getClass().getResourceAsStream("/Characters/player.png"));
			player1 = ImageIO.read(getClass().getResourceAsStream("/Characters/player1.png"));
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void moveLeft(boolean b) { moveLeft = b; }
	public void moveRight(boolean b) { moveRight = b; }
	public void moveUp(boolean b) { moveUp = b; }
	public void moveDown(boolean b) { moveDown = b; }
	public void strafing(boolean b) { strafing = b; }
	public boolean isDead() { return dead; }
	public boolean isFlinching() { return flinching; }

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public String getLastFacing() {
		return lastFacing;
	}
	
	public Rectangle getBounds() {
		//40x40
		return new Rectangle((int)x, (int)y, 40, 40);
	}
	
	public int getHealth() {
		return health;
	}
	
	public void hit(int damage) {
		if(flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	public void update() {
		
		if(!strafing) {
			if(moveLeft) {
				lastFacing = "lt";
				x -= 5;
			}
			if(moveRight) {
				lastFacing = "rt";
				x += 5;
			}
			if(moveUp) {
				lastFacing = "up";
				y -= 5;
			}
			if(moveDown) {
				lastFacing = "dn";
				y += 5;
			}
			
		} else {
			if(moveLeft) {
				x -= 5;
			}
			if(moveRight) {
				x += 5;
			}
			if(moveUp) {
				y -= 5;
			}
			if(moveDown) {
				y += 5;
			}
		}
		
		// check done flinching
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
				if(elapsed > 1000) flinching = false;
		}
		
	}
	 
	public void draw(Graphics g) {
		
		// draw player
		if(!isDead()) {
			if(flinching) {
				long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
					if(elapsed / 100 % 2 == 0) return;
			}
		}
		
		if(lastFacing.equals("lt")) {
			g.drawImage(player, (int)x + player.getWidth() - 20 , (int)y, -player.getWidth(), player.getHeight(), null);
		}
		else if(lastFacing.equals("up")) {
			g.drawImage(player1, (int)x, (int)y - 20, player1.getWidth(), player1.getHeight(), null);
		}
		else if(lastFacing.equals("dn")) {
			g.drawImage(player1, (int)x, (int)y + player1.getHeight(), player1.getWidth(), -player1.getHeight(), null);
		}
		else {
			g.drawImage(player, (int)x, (int)y, null);
			
		}
		
	}
}
