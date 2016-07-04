package Main;
import javax.swing.JPanel;
import java.awt.event.*;
import GameState.GameStateManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener
{
	//Dimensions
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	
	private Thread thread;
	private boolean running;
	private BufferedImage image;
	private Graphics2D g;
	
	//frames per second and time interval to display each frame to achieve the fps
	private int fps = 60;
	private int targetTime = 1000/fps;
	
	private GameStateManager gsm = new GameStateManager();
	
	public GamePanel()
	{
		super();
		setPreferredSize(new Dimension(WIDTH*2,HEIGHT*2));
		setFocusable(true);
		requestFocus();
		running = true;
		addKeyListener(this);
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
	}
	
	public void addNotify()
	{
		super.addNotify();
		if (thread == null)
		{
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void run()
	{
		
		
		long startTime;
		long elapsedTime;
		long waitTime;
		
		while (running)
		{
			startTime = System.currentTimeMillis();
			
			gameUpdate();
			gameRender();
			gameDraw();
			//System.out.println("hi");
			elapsedTime = System.currentTimeMillis() - startTime;
			waitTime = targetTime - elapsedTime;
			// need to meet target time to achieve fps rate, if loop finishes early, 
			// wait to display next frame
			if (waitTime > 0)
			{
				try
				{
					Thread.sleep(waitTime);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void gameUpdate()
	{
		gsm.gameUpdate();
	}
	
	// draws next image to be placed in panel
	public void gameRender()
	{
		gsm.gameRender(g);
	}
	
	// draws image to panel
	public void gameDraw()
	{
		Graphics g2 = this.getGraphics();
		g2.drawImage(image,0,0,WIDTH*2,HEIGHT*2,null);
		g2.dispose();
	}
	
	public void keyPressed(KeyEvent e)
	{
		gsm.keyPressed(e.getKeyCode());
	}
	
	public void keyReleased(KeyEvent e)
	{
		gsm.keyReleased(e.getKeyCode());
	}
	
	public void keyTyped(KeyEvent e)
	{
		
	}
}
