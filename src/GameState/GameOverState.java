package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Audio.AudioPlayer;
import Main.Background;

public class GameOverState extends GameState {
	
	private Background bg;
	
	private AudioPlayer bgMusic;
	private AudioPlayer gunShot;
	
	private Font font;
	
	private int currentChoice = 0;
	private String[] options = {"RETRY", "QUIT"};

	public GameOverState(GameStateManager gsm) {
		
		this.gsm = gsm;
		init();
		
		try {
			
			bg = new Background("/Background/gobg.jpg", 1);
			
			font = new Font("Arial", Font.BOLD, 25);

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public void init() {
		
		gunShot = new AudioPlayer("/SFX/gunshot.wav");
		bgMusic = new AudioPlayer("/Music/gameover.wav");
		bgMusic.play();
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		
		bg.draw(g);
		

		g.setColor(Color.RED);
		g.setFont(new Font("Comic Sans", Font.BOLD, 30));
		g.drawString("Killed: " + gsm.getScore() + " Zombies", 260, 400);
		
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.WHITE);
			}
			else {
				g.setColor(Color.DARK_GRAY);
			}
			g.drawString(options[i], 350 + i * 10, 450 + i * 30);
		}
		
	}
	
	public void select() {
		
		if(currentChoice == 0) {
			// retry
			gunShot.play();
			bgMusic.close();
			gsm.setState(GameStateManager.MAPSTATE);
			
		}
		if(currentChoice == 1) {
			// quit
			System.exit(0);
		}
		
	}

	@Override
	public void keyPressed(int k) {
		
		if(k == KeyEvent.VK_ENTER) {
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice <= -1) {
				currentChoice = 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice >= 2) {
				currentChoice = 0;
			}
		}
		
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}

}
