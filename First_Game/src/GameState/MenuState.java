package GameState;
import java.awt.*;

import com.sun.glass.events.KeyEvent;

import TileMap.Background;

public class MenuState extends GameState
{
	Background bg;
	String[] options = {"Start","Help","Quit"};
	int choice = 0;
	
	Color titleColor = new Color(238,201,0);
	Font titleFont = new Font("Times New Roman",Font.BOLD,28);
	
	Font textFont = new Font("Century Gothic", Font.PLAIN,12);
	
	public MenuState(GameStateManager gsm)
	{
		this.gsm = gsm;
		bg = new Background("/Backgrounds/menubg.gif");
		bg.setVector(-0.1, 0);
	}
	
	public void gameUpdate()
	{
		bg.gameUpdate();
	}
	
	public void gameRender(Graphics2D g)
	{
		bg.gameRender(g);
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Game Testerino", 25, 50);
		
		g.setColor(Color.CYAN);
		g.setFont(textFont);
		for (int count = 0; count < options.length; count++)
		{
			if (count == choice)
			{
				g.setColor(Color.GREEN);
				g.drawString(options[count], 150, 100 + 20*count);
				g.setColor(Color.CYAN);
			}
			else
				g.drawString(options[count], 150, 100 + 20*count);
		}
	}
	
	public void keyPressed(int k)
	{
		if (k == KeyEvent.VK_DOWN)
		{
			if (choice == 2)
				choice = 0;
			else
				choice++;
		}
		if (k == KeyEvent.VK_UP)
		{
			if (choice == 0)
				choice = 2;
			else
				choice--;
		}
		if (k == KeyEvent.VK_ENTER)
		{
			if (choice == 0)
			{
				gsm.addState(new Level1State(gsm));
				gsm.setState(GameStateManager.LEVEL1_STATE);
			}
			else if(choice == 2)
				System.exit(0);
		}
	}
	
	public void keyReleased(int k)
	{
		
	}
}
