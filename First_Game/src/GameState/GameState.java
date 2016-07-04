package GameState;


public abstract class GameState 
{
	protected GameStateManager gsm;
	
	public abstract void gameUpdate();
	public abstract void gameRender(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
}
