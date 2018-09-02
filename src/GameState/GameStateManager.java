package GameState;


public class GameStateManager {

	private GameState[] gameStates;
	private int currentState;
	
	private int score;
	
	public static final int MENUSTATE = 0;
	public static final int MAPSTATE = 1;
	public static final int GAMEOVERSTATE = 2;
	
	public GameStateManager() {
		
		gameStates = new GameState[3];
		
		currentState = MENUSTATE;
		loadState(currentState);
		
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return this.score;
	}
	
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == MAPSTATE)
			gameStates[state] = new MapState(this);
		if(state == GAMEOVERSTATE)
			gameStates[state] = new GameOverState(this);
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);

	}
	
	public void update() {
		
		if(gameStates[currentState] != null) 
		
			gameStates[currentState].update();
		
	}
	
	public void draw(java.awt.Graphics2D g) {
		
		if(gameStates[currentState] != null) 
			
			gameStates[currentState].draw(g);
	
	}
	
	public void keyPressed(int k) {
		
		gameStates[currentState].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		
		gameStates[currentState].keyReleased(k);
	}
}
