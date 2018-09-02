package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Audio.AudioPlayer;
import Main.Background;

public class MenuState extends GameState {
	
	private Background bg;
	private AudioPlayer bgMusic;
	private AudioPlayer gunShot;
	
	private int currentChoice = 0;
	private String[] options = {"START", "QUIT"};
	
	private Color titleColor;
	private Font titleFont;
	private Font font;

	public MenuState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		try {
			
			bg = new Background("/Background/menubg.jpg", 1);
			
			titleColor = new Color(255, 0, 0);
			titleFont = new Font("TimesRoman", Font.BOLD, 60);
			font = new Font("Arial", Font.BOLD, 25);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		init();
		
	}

	@Override
	public void init() {

		gunShot = new AudioPlayer("/SFX/gunshot.wav");
		bgMusic = new AudioPlayer("/Music/theme.wav");
		bgMusic.play();
		
	}

	@Override
	public void update() {
		
		//bg.update();
	}

	@Override
	public void draw(Graphics g) {
		
		// draw bg
		bg.draw(g);
		
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("D A R K  W O O D S", 130, 200);
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.WHITE);
			}
			else {
				g.setColor(Color.DARK_GRAY);
			}
			g.drawString(options[i], 350 + i * 10, 400 + i * 30);
		}
	}
	
	public void select() {
		
		if(currentChoice == 0) {
			// start
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
