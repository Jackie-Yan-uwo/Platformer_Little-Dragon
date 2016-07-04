package Entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.GamePanel;
import TileMap.Tile;
import TileMap.TileMap;

public abstract class Entity 
{
	//sprites
	protected int spriteSize;
	protected int sheetCutSize;
	protected BufferedImage spriteSheet;
	protected ArrayList<Sprite[]> spriteMap;
	protected Animation animation;
	
	//position
	protected double x,y;
	protected double dx,dy;
	private int currRow, currCol;
	protected double xTemp,yTemp;
	private double xDest,yDest;
	protected boolean falling;
	protected boolean jumping;
	
	//movement
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed,maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	protected boolean facingRight;
	
	//tileMap
	protected TileMap tileMap;
	protected int tileSize;
	
	//Collision
	private Rectangle hitBox;
	private boolean topLeft,topRight,bottomLeft,bottomRight;
	
	public void setTileMap(TileMap tm)
	{
		tileMap = tm;
		tileSize = tm.getTileSize();
	}
	
	public void setHitBox()
	{
		hitBox = new Rectangle((int)x,(int)y,spriteSize,spriteSize);
	}
	
	public Rectangle getHitBox()
	{
		return this.hitBox;
	}
	
	public boolean intersects(Entity e)
	{
		return hitBox.intersects(e.getHitBox());
	}
	
	public void checkTileCollision()
	{
		currRow = (int)y/tileSize;
		currCol = (int)x/tileSize;
		xDest = x + dx;
		yDest = y + dy;
		
		xTemp = x;
		yTemp = y;
		
		try
		{
			calculateCorners(x,yDest);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			this.setPosition(100, 100);
			tileMap.updatePosition(0);
			currRow = (int)y/tileSize;
			currCol = (int)x/tileSize;
			xTemp = x;
			yTemp = y;
		}
		if (dy < 0)
			if (topLeft || topRight)
			{
				dy = 0;
				yTemp = currRow * tileSize + 1;
			}
			else
				yTemp += dy;
		
		else if (dy > 0)
			if (bottomLeft || bottomRight)
			{
				dy = 0;
				falling = false;
				yTemp = (currRow+1)*tileSize; // y coord directly above ground
			}									
			else
				yTemp += dy;
		
		calculateCorners(xDest,y);
		if (dx < 0)
			if (topLeft && (bottomLeft||bottomRight))
			{
				dx = 0;
				xTemp = (currCol) * tileSize + 15;
			}
			else
				xTemp += dx;
		
		else if (dx > 0)
			if (topRight && (bottomRight||bottomLeft))
			{
				dx = 0;
				xTemp = (int)(x);
			}
			else
				xTemp += dx;
		
		if (!falling)
		{
			calculateCorners(x,yDest + 1);
			if (!bottomLeft && !bottomRight)
			{
				falling = true;
			}
		}
	}
	
	public void calculateCorners(double x, double y)
	{
		//bug: when jumping on short wall, doesnt recognize top corners as blocked, passes through wall
		int leftCol = (int)(x - spriteSize + 5)/tileSize;
		int rightCol = (int)(x -5)/tileSize;
		if (facingRight)
		{
			rightCol = (int)(x + spriteSize - 5)/tileSize;
			leftCol = (int)(x + 5)/tileSize;
		}
		int bottomRow = (int)(y + sheetCutSize)/tileSize;
		int topRow = (int)y/tileSize;

		int tl = tileMap.getTileType(topRow, leftCol);
		int tr = tileMap.getTileType(topRow, rightCol);
		int bl = tileMap.getTileType(bottomRow, leftCol);
		int br = tileMap.getTileType(bottomRow, rightCol);
		topLeft = (tl == Tile.BLOCKED);
		topRight = (tr == Tile.BLOCKED);
		bottomLeft = (bl == Tile.BLOCKED);
		bottomRight = (br == Tile.BLOCKED);
	}
	
	public void setSpriteSize(int s)
	{
		spriteSize = s;
	}
	
	public void setPosition(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public void setVector(double dx, double dy)
	{
		this.dx = dx;
		this.dy = dy;
	}
	
	public boolean onScreen()
	{
		return x >= tileMap.getX() && x <= tileMap.getX() + GamePanel.WIDTH &&
				y >= tileMap.getY() && y <= tileMap.getY() + GamePanel.HEIGHT;
	}
}
