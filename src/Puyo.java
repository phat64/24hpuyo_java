/***************************************************************************
                       Puyo.java -  description
                             -------------------
    begin                : Fri Apr 15 2005
    copyright            : (C) 2005 by Mustapha Tachouct
    email                : tachoucm@yahoo.fr
 ***************************************************************************/

/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/


import java.util.Random;

public class Puyo extends PuyoRessources implements Movable, Drawable, Animable 
{
	protected int centerX;
	protected int centerY;
	protected int colorID;
	protected int frameID;
	protected int prevCenterX;
	protected int prevCenterY;

	private final int RADIUS = PuyoRessources.PUYO_RADIUS;
	private final int RADIUS2 = RADIUS * RADIUS;
	private final int DX = PuyoRessources.PUYO_DX;
	private final int DY = PuyoRessources.PUYO_DY;
	private final int NB_PUYO_WIDTH = PuyoRessources.NB_PUYO_ARRAY_WIDTH;
	private final int UPDATE_MOVE_DOWN = PuyoRessources.PUYO_UPDATE_MOVE_DOWN;	

	//private static long time;
	
	
	private static final Random random = new Random();

	protected Puyo other;
	

	Puyo()
	{
		setPosition(PUYO_TABLE_WIDTH/2 - RADIUS, RADIUS);
		setRandomColor();
	
	}

	Puyo(Puyo other)
	{
		setPosition(PUYO_TABLE_WIDTH/2 + RADIUS, RADIUS);
		setRandomColor();
		setOtherPuyo(other);
	}
	
		
	Puyo(int centerX, int centerY)
	{
		setPosition(centerX,centerY);
		setRandomColor();
	}


	public String toString()
	{
		String [] colors = {"R","G","B","Y"};
		return "Puyo (" + colors[colorID] + ") " + centerX + "," + centerY;
	}
	
	void setPosition(int centerX, int centerY)
	{
		this.centerX = centerX;
		this.centerY = centerY;
	}

	public void move(int dx, int dy)
	{
		centerX += dx;
		centerY += dy;
	}

	void setOtherPuyo(Puyo other)
	{
		if (this.other != other)
		{
			this.other = other;
			other.setOtherPuyo(this);
		}
	}



	Puyo getOtherPuyo()
	{
		return other;
	}
	//void setColor(	
	public void setRandomColor()
	{
		int newColorID;
		do
		{
			newColorID = randInt(PuyoRessources.COLOR_COUNT);
		}
		while (newColorID == colorID);		
		colorID = newColorID;
	}
	
	int getCenterX()
	{return centerX;}
	
	int getCenterY()
	{return centerY;}

	int getColorID()
	{return colorID;}

	void setColorID(int colorID) {this.colorID = colorID;}

	void update()
	{
		centerY += UPDATE_MOVE_DOWN;	
	}

	boolean isCollideWith(Puyo other)
	{
		int distanceX;
		int distanceY;

		distanceX = this.centerX - other.centerX;
		distanceY = this.centerY - other.centerY;
		return distanceX * distanceX < RADIUS2 && distanceY * distanceY < RADIUS2;
	}

	boolean canMoveLeft()
	{
		int x;
		
		x = getIndexPositionX();

		return x > 0 && PuyoRessources.getHeightMap()[x-1]<=RADIUS;
	}

	boolean canMoveRight()
	{
		int x;
		
		x = getIndexPositionX();

		return x < NB_PUYO_WIDTH - 1 && PuyoRessources.getHeightMap()[x+1]<=RADIUS;
	}

	private int getIndexPositionX()
	{
	       int x; // 0 <= X < NB_PUYO_WIDTH
	       
		x = (centerX - RADIUS)/ (RADIUS*2);

		return x;
	}

	public int getHeightMapIndex()
	{
		int idx;

		idx = (centerX - RADIUS)/ PUYO_WIDTH;
		idx = Math.max(idx, 0);
		idx = Math.min(idx, NB_PUYO_ARRAY_WIDTH);
		if ((centerX - RADIUS) % PUYO_WIDTH != 0)
		{
			int idxLeft;
			int idxRight;
			int [] heightMap;

			idxLeft = idx;
			idxRight = Math.min(idx + 1, NB_PUYO_ARRAY_WIDTH);
			heightMap = getHeightMap();
			synchronized (heightMap)
			{
				idx = heightMap[idxLeft] < heightMap[idxRight] ?		      								idxLeft : idxRight;
			}
		}
		
		return idx;
	}
	
	public void draw()
	{
		java.awt.Image img;
		img = PuyoRessources.getPuyoImage(colorID, frameID);

		setClipPuyoTable();
		synchronized(g){synchronized(img) {
			g.drawImage(img, PUYO_TABLE_X + centerX-RADIUS, PUYO_TABLE_Y+centerY-RADIUS,null);
		}}
	}

	public void clear()
	{
		PuyoGameScreen.setBackgroundColor();

		PuyoGameScreen.clearArea/*Debug*/(PUYO_TABLE_X+centerX-RADIUS,
			       	PUYO_TABLE_Y+centerY-RADIUS, PUYO_WIDTH, PUYO_HEIGHT);
	}
	
	public void anime()
	{
		frameID = 0x03 & (int)(System.currentTimeMillis()>>8);	
	}

	private void setClip()
	{
		if (isOnPuyoTable())
		{
			setClipPuyoTable();
		}
		else if (isOnPuyoNext())
		{
			setClipPuyoNext();
		}	
	}

	private final boolean isOnPuyoTable()
	{
		final int W = 2 * RADIUS;
		final int H = 2 * RADIUS;
		int x;
		int y;

		x =  centerX - RADIUS;
		y =  centerY - RADIUS;
		
		return (x >= PUYO_TABLE_X && x + W < PUYO_TABLE_WIDTH)
			&& (y >= PUYO_TABLE_Y && y + H < PUYO_TABLE_HEIGHT);
	}

	private final boolean isOnPuyoNext()
	{
		final int W = 2 * RADIUS;
		final int H = 2 * RADIUS;
		int x;
		int y;

		x =  centerX - RADIUS;
		y =  centerY - RADIUS;
		
		return (x >= PUYO_NEXT_X && x + W < PUYO_NEXT_WIDTH)
			&& (y >= PUYO_NEXT_Y && y + H < PUYO_NEXT_HEIGHT);
	}

	private final void setClipPuyoTable()
	{
		int w = 2 * RADIUS;
		int h = 2 * RADIUS;
		int x;
		int y;

		x =  PUYO_TABLE_X + centerX - RADIUS;
		y =  PUYO_TABLE_Y + centerY - RADIUS;
		
		if (x < PUYO_TABLE_X)
		{
			w = PUYO_TABLE_X - x;
			x = PUYO_TABLE_X;
		}
		else if (x >= PUYO_TABLE_X + PUYO_TABLE_WIDTH)
		{
			w = x - PUYO_TABLE_X - PUYO_TABLE_WIDTH;
			x = PUYO_TABLE_X + PUYO_TABLE_WIDTH - w;
		}

		if (y < PUYO_TABLE_Y)
		{
			h = PUYO_TABLE_Y - y;
			y = PUYO_TABLE_Y;
		}
		else if (y >= PUYO_TABLE_Y + PUYO_TABLE_HEIGHT)
		{
			h = y - PUYO_TABLE_Y - PUYO_TABLE_HEIGHT;
			y = PUYO_TABLE_Y + PUYO_TABLE_HEIGHT - h;
		}

		g.setClip(x,y,w,h);
	}

	private final void setClipPuyoNext()
	{
		int w = 2 * RADIUS;
		int h = 2 * RADIUS;
		int x;
		int y;

		x =  centerX - RADIUS;
		y =  centerY - RADIUS;
		
		if (x < PUYO_NEXT_X)
		{
			w = PUYO_NEXT_X - x;
			x = PUYO_NEXT_X;
		}
		else if (x >= PUYO_NEXT_X + PUYO_NEXT_WIDTH)
		{
			w = x - PUYO_NEXT_X - PUYO_NEXT_WIDTH;
			x = PUYO_NEXT_X + PUYO_NEXT_WIDTH - w;
		}

		if (y < PUYO_NEXT_Y)
		{
			h = PUYO_NEXT_Y - y;
			y = PUYO_NEXT_Y;
		}
		else if (y >= PUYO_NEXT_Y + PUYO_NEXT_HEIGHT)
		{
			h = y - PUYO_NEXT_Y - PUYO_NEXT_HEIGHT;
			y = PUYO_NEXT_Y + PUYO_NEXT_HEIGHT - h;
		}

		g.setClip(x,y,w,h);
	}

	public void pushPosition()
	{
		prevCenterX = centerX;
		prevCenterY = centerY;
	}

	public void popPosition()
	{
		centerX = prevCenterX;
		centerY = prevCenterY;
	}

	public boolean hasValidPosition()
	{
		return (centerX - RADIUS >= 0 && centerX + RADIUS <= PUYO_TABLE_WIDTH)
			&& centerY + RADIUS <= PUYO_TABLE_HEIGHT && centerY - RADIUS <=0; 
	}

	public int updatePosition()
	{
		int [] puyoHeightMap;
		int height;
		int transY;

		transY = 0;
		puyoHeightMap = getHeightMap();
		if ((centerX + RADIUS) % PUYO_WIDTH != 0)
		{
			int indexLeft;
			int indexRight;

			indexLeft = this.getHeightMapIndex();
			indexRight = indexLeft + 1;

			indexLeft = Math.max(indexLeft, 0);
			indexRight = Math.max(indexRight, 0);
			indexLeft = Math.min(indexLeft, NB_PUYO_ARRAY_WIDTH - 1);
			indexRight = Math.min(indexRight, NB_PUYO_ARRAY_WIDTH - 1);
			height = Math.min(puyoHeightMap[indexLeft], puyoHeightMap[indexRight]);
			if (centerY + RADIUS > height)
			{
				transY = centerY + RADIUS - height;
				centerY = height - RADIUS;
			}
		}
		else
		{
			int index;

			index = this.getHeightMapIndex();
			index = Math.max(index, 0);
			index = Math.min(index, NB_PUYO_ARRAY_WIDTH - 1);
			height = puyoHeightMap[index];
			if (centerY + RADIUS > height)
			{
				transY = centerY + RADIUS - height;
				centerY = height - RADIUS;
			}

		}
		return transY;
	}

	private Puyo getLeftPuyo()
	{
		return this.getCenterX() < other.getCenterX() ? this : other;
	}

	private Puyo getRightPuyo()
	{
		return this.getCenterX() > other.getCenterX() ? this : other;
	}

	private Puyo getUpPuyo()
	{
		return this.getCenterY() < other.getCenterY() ? this : other;
	}

	private Puyo getDownPuyo()
	{
		return this.getCenterY() > other.getCenterY() ? this : other;
	}

	/******************************************/
		
	public boolean isTouched()
	{
		Puyo left;
		Puyo right;
		boolean ret;

		int idxLeft;
		int idxRight;
		int [] heightMap;

		left = this.getLeftPuyo();
		right = this.getRightPuyo();
		idxLeft = left.getHeightMapIndex();
		idxRight = right.getHeightMapIndex();
		heightMap = getHeightMap();
		
		ret = left.getCenterY() + RADIUS == heightMap[idxLeft]
			|| right.getCenterY() + RADIUS == heightMap[idxRight];


		return ret;
	}



	public void reset(boolean isOther)
	{
		if (isOther)
		setPosition(PUYO_TABLE_WIDTH/2 + RADIUS, RADIUS);
		else
		setPosition(PUYO_TABLE_WIDTH/2 - RADIUS, RADIUS);
		setRandomColor();	
	}
	
	public boolean canMoveDown()
	{
		Puyo left;
		Puyo right;
		int idxLeft;
		int idxRight;
		int [] heightMap;

		left = getLeftPuyo();
		right = getRightPuyo();
		idxLeft = left.getHeightMapIndex();
		idxRight = right.getHeightMapIndex();
		heightMap = getHeightMap();
		
		return left.getCenterY() + RADIUS < heightMap[idxLeft]
			&& right.getCenterY() + RADIUS < heightMap[idxLeft];
	}

	public int getXindex()
	{
		return (centerX / PUYO_WIDTH) ;
	}

	public int getYindex()
	{
		return (NB_PUYO_ARRAY_HEIGHT- 1) - (centerY / PUYO_HEIGHT) ;
	}

	private boolean isVertical()
	{
		return this.getCenterX() == other.getCenterX();
	}
	
	private boolean isHorizontal()
	{
		return this.getCenterY() == other.getCenterY();
	}

	public void writeOnPuyoTable()
	{
		int x = getXindex();
		int y = getYindex();

		int [][] array2d = PuyoRessources.getPuyoArray();
		
		array2d[x][y] = colorID;
	}
		
}

