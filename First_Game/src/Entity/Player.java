package Entity;

import java.util.ArrayList;
import javax.imageio.ImageIO;

import Main.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity
{
	// player things
	private int health,maxHealth;
	private int fire,maxFire;
	private boolean flinching;
	private long flinchTime;
	private int currentAction;
	private boolean left,right;
	
	// fireball
	private boolean firing;
	private int fireCost;
	private int fireDmg;
	//private ArrayList<FireBall> fireballs;
	
	// scratching
	private boolean scratching;
	private int scratchDmg;
	private int scratchRange;
	
	// gliding
	private boolean gliding;
	
	// animations
	private final int[] numFrames = {2,8,1,2,4,2,5};
	
	// animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int GLIDING = 4;
	private static final int FIREBALL = 5;
	private static final int SCRATCHING = 6;

	public Player()
	{
		spriteSize = 20;
		sheetCutSize = 30;
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		
		facingRight = true;
		
		health = maxHealth = 5;
		fire = maxFire = 20;
		fireCost = 1;
		fireDmg = 5;
		//fireBalls = new ArrayList<FireBall>();
		
		scratchDmg = 8;
		scratchRange = 40;
		
	}
	
	public void loadSprites()
	{
		try
		{
			spriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/playersprites.gif"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		spriteMap = new ArrayList<Sprite[]> ();
		BufferedImage subImage;
		Sprite sprite;
		Sprite[] temp = null;
		
		for (int row = 0; row < 7; row++) // because there are 7 actions
		{
			temp = new Sprite[numFrames[row]];
			for (int col = 0; col < numFrames[row]; col++)
			{
				if (row != 6)
				{
					subImage = spriteSheet.getSubimage(col*sheetCutSize,row*sheetCutSize,sheetCutSize,sheetCutSize);
					sprite = new Sprite(subImage);
					temp[col] = sprite;
				}
				else
				{
					subImage = spriteSheet.getSubimage(col*sheetCutSize*2,row*sheetCutSize,sheetCutSize*2,sheetCutSize);
					sprite = new Sprite(subImage);

					temp[col] = sprite;
				}
			}
			spriteMap.add(temp);
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(spriteMap.get(IDLE));
		animation.setDelay(400);
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public int getMaxHealth()
	{
		return maxHealth;
	}
	
	public int getFire()
	{
		return fire;
	}
	
	public int getMaxFire()
	{
		return maxFire;
	}
	
	public void setFiring()
	{
		firing = true;
	}
	
	public void setScratching(boolean b)
	{
		scratching = b;
	}
	
	public void setLeft(boolean b)
	{
		this.left = b;
	}
	
	public void setRight(boolean b)
	{
		this.right = b;
	}
	
	public void setJumping(boolean b)
	{
		jumping = b;
	}
	
	public void setGliding(boolean b)
	{
		gliding = b;
	}
	
	public void getNextPosition()
	{
		if (left)
		{
			dx -= moveSpeed;
			if (dx < maxSpeed)
				dx = -maxSpeed;
			facingRight = false;
		}
		else if (right)
		{
			dx += moveSpeed;
			if (dx > maxSpeed)
				dx = maxSpeed;
			facingRight = true;
		}
		else
		{
			if (dx > 0)
			{
				dx -= stopSpeed;
				if (dx < 0)
					dx = 0;
			}
			else if (dx < 0)
			{
				dx += stopSpeed;
				if (dx > 0)
					dx = 0;
			}
		}
		
		// cannot move during attack except in air
		if (currentAction == SCRATCHING || currentAction == FIREBALL && !(jumping||falling))
		{
			dx = 0;
		}
		
		// jumping
		if (jumping && !falling)
		{
			dy = jumpStart;
			falling = true;
			jumping = true;
		}
		
		//falling
		if (falling)
		{
			if (dy > 0 && gliding)
				dy += fallSpeed * 0.1;
			else
				dy += fallSpeed;
			
			if (dy > 0)
				jumping = false;
			if (dy < 0 && !jumping)
				dy += stopJumpSpeed;
			if (dy > maxFallSpeed)
				dy = maxFallSpeed;
		}
	}
	
	public void update() 
	{
		getNextPosition();
		checkTileCollision();
		setPosition(xTemp,yTemp);
		if (scratching)
		{
			if (currentAction != SCRATCHING)
			{
				currentAction = SCRATCHING;
				animation.setFrames(spriteMap.get(SCRATCHING));
				animation.setDelay(50);
			}
		}
		
		else if (firing)
		{
			if (currentAction != FIREBALL)
			{
				currentAction = FIREBALL;
				animation.setFrames(spriteMap.get(FIREBALL));
				animation.setDelay(100);
			}
		}
			
		else if (falling)
		{
			if (gliding)
			{
				if (currentAction != GLIDING)
				{
					currentAction = GLIDING;
					animation.setFrames(spriteMap.get(GLIDING));
					animation.setDelay(100);
				}
			}
			else if (currentAction != FALLING)
			{
				currentAction = FALLING;
				animation.setFrames(spriteMap.get(FALLING));
				animation.setDelay(100);
			}
		}
		
		else if (jumping)
		{
			if (currentAction != JUMPING)
			{
				currentAction = JUMPING;
				animation.setFrames(spriteMap.get(JUMPING));
				animation.setDelay(-1);
			}
		}
		
		else if (left || right && !(jumping||falling))
		{
			if (currentAction != WALKING)
			{
				currentAction = WALKING;
				animation.setFrames(spriteMap.get(WALKING));
				animation.setDelay(40);
			}
		}
		
		else
		{
			if (currentAction != IDLE)
			{
				currentAction = IDLE;
				animation.setFrames(spriteMap.get(IDLE));
				animation.setDelay(400);
			}
		}
		
		animation.update();
	}
	
	public void draw(Graphics2D g)
	{
		if (flinching)
		{
			long elapsed = System.currentTimeMillis() - flinchTime;
			if (elapsed/100 % 2 == 0)
				return;
		}
		if ((int)x <= GamePanel.WIDTH/2)
		{
			if (facingRight)
			{
				g.drawImage(animation.getImage(), (int)x - 10, (int)y, null);
			}
			else 
			{
				if (scratching)
					g.drawImage(animation.getImage(), (int)x + 10, (int)y, -60, 30, null);
				else
					g.drawImage(animation.getImage(), (int)x + 10, (int)y, -30, 30, null);

			}
		}
		
		else if ((int)x > (tileMap.getMaxX() - GamePanel.WIDTH/2))
		{
			if (facingRight)
			{
				g.drawImage(animation.getImage(), GamePanel.WIDTH - ((int)tileMap.getMaxX() - (int)x) - 10, (int)y, null);
			}
			else
			{
				if (scratching)
					g.drawImage(animation.getImage(), GamePanel.WIDTH - ((int)tileMap.getMaxX() - (int)x) + 10, (int)y, -60, 30, null);
				else
					g.drawImage(animation.getImage(), GamePanel.WIDTH - ((int)tileMap.getMaxX() - (int)x) + 10, (int)y, -30, 30, null);

			}
		}
		
		else if ((int)x > GamePanel.WIDTH/2)
		{
			if (facingRight)
			{
				g.drawImage(animation.getImage(), GamePanel.WIDTH/2 - 10, (int)y, null);
			}
			else
			{
				if (scratching)
					g.drawImage(animation.getImage(), GamePanel.WIDTH/2 + 10, (int)y, -60, 30, null);
				else
					g.drawImage(animation.getImage(), GamePanel.WIDTH/2 + 10, (int)y, -30, 30, null);
			}
		}
		//g.setColor(Color.RED);
		//g.fillRect((int)x, (int)y, 1, 1);
	}
}
