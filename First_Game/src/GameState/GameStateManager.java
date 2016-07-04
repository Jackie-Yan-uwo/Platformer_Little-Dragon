package GameState;
import java.util.ArrayList;

public class GameStateManager 
{
	private ArrayList<GameState> gameStates;
	protected int currentState;
	
	public static final int MENU_STATE = 0;
	public static final int LEVEL1_STATE = 1;
	
	public GameStateManager()
	{
		gameStates = new ArrayList<GameState>();
		currentState = MENU_STATE;
		gameStates.add(new MenuState(this));
	}
	
	public void setState(int state)
	{
		currentState = state;
	}
	
	public void addState(GameState state)
	{
		gameStates.add(state);
	}
	
	public void gameUpdate()
	{
		gameStates.get(currentState).gameUpdate();
	}
	
	public void gameRender(java.awt.Graphics2D g)
	{
		gameStates.get(currentState).gameRender(g);
	}
	
	public void keyPressed(int k)
	{
		gameStates.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k)
	{
		gameStates.get(currentState).keyReleased(k);
	}
}
