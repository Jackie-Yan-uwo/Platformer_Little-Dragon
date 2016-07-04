package Entity;
import java.awt.image.BufferedImage;

public class Sprite 
{
	private BufferedImage img;
	
	public Sprite(BufferedImage img)
	{
		this.img = img;
	}
	
	public BufferedImage getImage()
	{
		return this.img;
	}
}
