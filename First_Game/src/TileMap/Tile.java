package TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Tile 
{
	private BufferedImage img;
	private int type;
	public static final int BLOCKED = 1;
	public static final int NORMAL = 0;
	
	public void setTileImg(BufferedImage img)
	{
		this.img = img;
	}
	
	public void setTileType(int t)
	{
		this.type = t;
	}
	
	public BufferedImage getImage()
	{
		return img;
	}
	
	public int getType()
	{
		return this.type;
	}
}
