package TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class TileMap 
{
	//position
	private double drawx = 0;
	private double drawy = 0;
	private int xTile = 0;
	private int yTile = 0;
	private double maxx,maxy;

	//map
	private int[][] map;
	
	//tileSet
	private int tileSize;
	private Tile[][]tileMap;
	private int numRows;
	private int numCols;
	private BufferedImage tileSet;
	private int numTilesAcross;
	
	//drawing
	private int numRowsDrawn, numColsDrawn;
	
	public TileMap(int tileSize)
	{
		this.tileSize = tileSize;
		numRowsDrawn = GamePanel.HEIGHT/tileSize + 2;
		numColsDrawn = GamePanel.WIDTH/tileSize + 2;
	}
	
	public void loadMap(String mapFile)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(mapFile)));
			numCols = Integer.parseInt(reader.readLine());
			numRows = Integer.parseInt(reader.readLine());
			map = new int[numRows][numCols];
			maxx = numCols*tileSize;
			maxy = numRows*tileSize;
			String[] currentRow;
			
			for (int row = 0; row < numRows; row++)
			{
				currentRow = reader.readLine().split(" ");
				for (int col = 0; col < numCols; col++)
					map[row][col] = Integer.parseInt(currentRow[col]);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadTiles(String tilesFile)
	{
		try
		{
			tileSet = ImageIO.read(this.getClass().getResourceAsStream(tilesFile));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		numTilesAcross = tileSet.getWidth()/tileSize;
		tileMap = new Tile[tileSet.getHeight()/tileSize][tileSet.getWidth()/tileSize];
		BufferedImage subImage;
		Tile tile;

		for (int col = 0; col < tileSet.getWidth()/tileSize; col++)
		{
			tile = new Tile();
			subImage = tileSet.getSubimage(col*tileSize, 0, tileSize, tileSize);
			tile.setTileImg(subImage);
			tile.setTileType(Tile.NORMAL);
			tileMap[0][col] = tile;
			
			tile = new Tile();
			subImage = tileSet.getSubimage(col*tileSize, tileSize, tileSize, tileSize);
			tile.setTileImg(subImage);
			tile.setTileType(Tile.BLOCKED);
			tileMap[1][col] = tile;
		}
	}
	
	public int getTileSize()
	{
		return this.tileSize;
	}
	
	public int getTileType(int row, int col)
	{
		int rc = map[row][col];
		int r = rc/numTilesAcross;
		int c = rc%numTilesAcross;
		return tileMap[r][c].getType();
	}
	
	public double getX()
	{
		return drawx;
	}
	
	public double getY()
	{
		return drawy;
	}
	
	public double getMaxX()
	{
		return this.maxx;
	}
	
	public void updatePosition(double x)
	{
		drawx = x;
		fixBounds();
		xTile = (int)(drawx)/tileSize;
	}
	
	private void fixBounds()
	{
		if (drawx < 0)
			drawx = 0;
		else if (drawx  + GamePanel.WIDTH > maxx)
			drawx = maxx - GamePanel.WIDTH;
		if (drawy < 0)
			drawy = 0;
		else if (drawy + GamePanel.HEIGHT > maxy)
			drawy = maxy - GamePanel.HEIGHT;
	}
	
	public void draw(Graphics2D g)
	{

		for (int row = yTile; row < yTile + numRowsDrawn; row++)
		{
			if(row >= numRows)
				break;
			
			for (int col = xTile; col < xTile + numColsDrawn; col++)
			{
				if (col >= numCols)
					break;
				if (map[row][col] == 0)
					continue;
				int tileNum = map[row][col];
				int rowNum = tileNum/numTilesAcross;
				int colNum = tileNum%numTilesAcross;
				
				g.drawImage(tileMap[rowNum][colNum].getImage(),col*tileSize - (int)drawx,row*tileSize - (int)drawy,null);
			}
		}
	}
}
