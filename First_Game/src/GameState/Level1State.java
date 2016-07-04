package GameState;

import java.awt.Graphics2D;
import Entity.*;

import java.awt.event.KeyEvent;

import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level1State extends GameState
{
	private TileMap tileMap;
	private Background bg;
	private Player player;
	
	public Level1State(GameStateManager gsm)
	{
		this.gsm = gsm;
		tileMap = new TileMap(30);
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		bg = new Background("/Backgrounds/grassbg1.gif");
		
		player = new Player();
		player.setTileMap(tileMap);
		player.setPosition(100, 100);
		player.loadSprites();
	}
	
	@Override
	public void gameUpdate() 
	{
		player.update();
		tileMap.updatePosition(player.getX() - GamePanel.WIDTH/2);
	}

	@Override
	public void gameRender(Graphics2D g) 
	{
		bg.gameRender(g);
		tileMap.draw(g);
		player.draw(g);
	}

	@Override
	public void keyPressed(int k)
	{
		if (k == KeyEvent.VK_LEFT)
			player.setLeft(true);
		if (k == KeyEvent.VK_RIGHT)
			player.setRight(true);
		if (k == KeyEvent.VK_SPACE)
			player.setJumping(true);
		if (k == KeyEvent.VK_X)
			player.setGliding(true);
		if (k == KeyEvent.VK_C)
			player.setScratching(true);
	}

	@Override
	public void keyReleased(int k) 
	{
		if (k == KeyEvent.VK_LEFT)
			player.setLeft(false);
		if (k == KeyEvent.VK_RIGHT)
			player.setRight(false);
		if (k == KeyEvent.VK_SPACE)
			player.setJumping(false);
		if (k == KeyEvent.VK_X)
			player.setGliding(false);
		if (k == KeyEvent.VK_C)
			player.setScratching(false);
	}
	
}
