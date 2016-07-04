package TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import Main.GamePanel;

public class Background 
{
	private BufferedImage image;
	//coordinates of background image
	private double x;
	private double y;
	//rate of movement of bg
	private double dx;
	private double dy;
	

	public Background(String s)
	{
		try
		{
			image = ImageIO.read(getClass().getResourceAsStream(s));
			x = 0;
			y = 0;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setVector(double dx, double dy)
	{
		this.dx = dx;
		this.dy = dy;
	}
	
	public void gameUpdate()
	{
		x = (x + dx)%GamePanel.WIDTH;
		y = (y + dy)%GamePanel.HEIGHT;
	}
	
	public void gameRender(Graphics2D g)
	{
		g.drawImage(image,(int)x, (int)y, null);
		
		if(x < 0)
		{
			g.drawImage(image, (int)x + GamePanel.WIDTH, (int)y, null);
		}
		if(x > 0)
		{
			g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);
		}
	}
}
