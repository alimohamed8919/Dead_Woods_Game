package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Audio.AudioPlayer;
import Main.Background;
import Entity.Bullet;
import Entity.BulletController;
import Entity.HordeController;
import Entity.Player;
import Entity.Zombie;

public class MapState extends GameState {
	
	private Graphics2D g2;
	//private AudioPlayer bgMusic;
	private AudioPlayer gunShot;
	private AudioPlayer playerhurt;
	private AudioPlayer playerdeath;
	private AudioPlayer zombiehurt;
	private AudioPlayer[] zn = {new AudioPlayer("/SFX/zombie1.wav"), 
			new AudioPlayer("/SFX/zombie2.wav"),new AudioPlayer("/SFX/zombie3.wav")};
	
	private Background bg;
	private Background bg1;
	
	private long Timer = -1;
	private long noiseTimer = -1;
	
	private Player player;
	//private Zombie zombie;
	private BulletController bullets;
	private HordeController horde;
	private int kills = 0;
	
	public MapState(GameStateManager gsm) {
		
		this.gsm = gsm;
		init();
	}

	@Override
	public void init() {
			
		bg = new Background("/Background/grassbg.gif", 1);
		bg1 = new Background("/Background/treesbg.gif", 1);
		
		
		bg.setPosition(-850, -650);
		//bg.setVector(-0.1 , -0.1);
		bg1.setPosition(-850, -650);
		//bg1.setVector(-0.1, -0.1);
		
		
		player = new Player(400,300);
		//zombie = new Zombie(500, 200);
		horde = new HordeController();
		bullets = new BulletController();
		
		zombiehurt = new AudioPlayer("/SFX/zombiehurt.wav");
		playerdeath = new AudioPlayer("/SFX/death.wav");
		playerdeath.volumeControl(6.0206f);
		playerhurt = new AudioPlayer("/SFX/injured.wav");
		//bgMusic = new AudioPlayer("/SFX/forest.wav");
		//bgMusic.play();
		gunShot = new AudioPlayer("/SFX/gunshot.wav");
		zn[0].play();
		
	}
	
	public void randomZombieNosies() {
		
		for(int i = 0; i < zn.length; i++) {
			if(zn[i].isClipRunning()) {
				return;
			}
				
		}
		
		if(noiseTimer <= -1) {
			noiseTimer = System.nanoTime();
		}else {
			long elapsed = (System.nanoTime() - noiseTimer) / 1000000;
			if(elapsed > 10000) {
				int v = (int) (0 + Math.random() * 3);
				zn[v].play();
				noiseTimer = -1;
			}
		}
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void update() {
		
		if(player.x <= -320) {
			player.setPosition(-320, player.y);
		}if(player.x >= 1580) {
			player.setPosition(1580, player.y);
		}if(player.y >= 1280) {
			player.setPosition(player.x, 1280);
		}if(player.y <= -115) {
			player.setPosition(player.x, -115);
		}
		
		
		
		randomZombieNosies();
		
		
		
		player.update();
		//zombie.update();
		//zombie.moveTowards(player);
		
		// Assign spawning zombies to chase player
		for(int i = 0; i < horde.z.size(); i++) {
			horde.z.get(i).moveTowards(player);
		}
		
		horde.update();
		bullets.update();
		
		// Collision Detection (If bullet hits Zombies)
		for(int i = 0; i < horde.z.size(); i++) {
			for(int k = 0; k < bullets.b.size(); k++) {
				Bullet TempBullet = bullets.b.get(k);
				Zombie TempZombie = horde.z.get(i);
				if(bullets.b.get(k).getBounds().intersects(horde.z.get(i).getBounds())) {
					//System.out.println("COLLISION DETECTED");
					zombiehurt.play();
					bullets.removeBullet(TempBullet);
					TempZombie.setHealth(0);
					kills++;
				}
			}
		}
		
		// Collision Detection (Zombies hit player)
		for(int i = 0; i < horde.z.size(); i++) {
			if(horde.z.get(i).getBounds().intersects(player.getBounds())) {
				if(!player.isFlinching() && !player.isDead())
					playerhurt.play();
					player.hit(50);
				
			}
		}
		
		if(player.isDead()) {
			if(Timer <= -1) {
				playerdeath.play();
				Timer = System.nanoTime();
			}else {
				long elapsed = (System.nanoTime() - Timer) / 1000000;
				if(elapsed > 4000) {
					//bgMusic.close();
					horde.t.stop();
					gsm.setScore(kills);
					gsm.setState(GameStateManager.GAMEOVERSTATE);
				}
			}
			
		}
		
		//System.out.println("X = " + player.x + "  Y = " + player.y);
		//System.out.println("Angle = " + zombie.A);
		
	}

	@Override
	public void draw(Graphics g) {
		g2 = (Graphics2D) g;
		
		g2.translate(-player.x + 400, -player.y + 300);
		
		bg.draw(g);
		player.draw(g2);
		//zombie.draw(g2);
		horde.draw(g);
		bullets.draw(g);
		bg1.draw(g);
		
		g.setColor(Color.RED);
		g.setFont(new Font("Comic Sans", Font.BOLD, 20));
		g.drawString("Life:", (int)player.x - 375, (int)player.y - 262);
		
		g.setColor(Color.BLACK);
		g.fillRect((int)player.x - 330, (int)player.y - 275, 150, 15);
		g.setColor(Color.RED);
		g.fillRect((int)player.x - 330, (int)player.y - 275, player.getHealth(), 15);
		g.setColor(Color.GRAY);
		g.drawRect((int)player.x - 330, (int)player.y - 275, 150, 15);
		
		g.setColor(Color.RED);
		g.drawString("Kills: " + kills, (int)player.x + 300, (int)player.y - 262);
		
		g2.translate(player.x - 400, player.y - 300);
		
	}

	@Override
	public void keyPressed(int k) {
		
		if(!player.isDead()) {
			if(k == KeyEvent.VK_LEFT)  player.moveLeft(true);
			if(k == KeyEvent.VK_RIGHT) player.moveRight(true);
			if(k == KeyEvent.VK_UP) player.moveUp(true);
			if(k == KeyEvent.VK_DOWN) player.moveDown(true);
			if(k == KeyEvent.VK_SHIFT) player.strafing(true);
		}

	}

	@Override
	public void keyReleased(int k) {
		
		if(k == KeyEvent.VK_LEFT) player.moveLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.moveRight(false);
		if(k == KeyEvent.VK_UP) player.moveUp(false);
		if(k == KeyEvent.VK_DOWN) player.moveDown(false);
		if(k == KeyEvent.VK_SHIFT) player.strafing(false);
		
		if(!player.isDead()) {
			if(k == KeyEvent.VK_SPACE) {
				gunShot.stop();
				bullets.addBullet(new Bullet(player.x, player.y, player.getLastFacing()));
				gunShot.play();
				
			}
		}
	
	}

}
