package Entity;
import java.awt.image.BufferedImage;

public class Animation 
{
	private Sprite[] frames;
	private int currentFrame;
	
	private long startTime;
	private long delay;
	
	private boolean playedOnce;
	
	public Animation()
	{
		playedOnce = false;
	}
	
	public void setFrames(Sprite[] sprites)
	{
		this.frames = sprites;
		currentFrame = 0;
		startTime = System.currentTimeMillis();
		playedOnce = false;
	}
	
	public void setDelay(long d)
	{
		this.delay = d;
	}
	
	public void update()
	{
		if (delay == -1)
			return;
		
		long elapsed = System.currentTimeMillis() - startTime;
		if (elapsed >= delay)
		{
			currentFrame++;
			startTime = System.currentTimeMillis();
		}
		if (currentFrame == frames.length)
		{
			currentFrame = 0;
			playedOnce = true;
		}
	}
	
	public int getFrame()
	{
		return currentFrame;
	}
	
	public BufferedImage getImage()
	{
		return frames[currentFrame].getImage();
	}
	
	public boolean hasPlayedOnce()
	{
		return playedOnce;
	}
}
