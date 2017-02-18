/***************************************************************************
                    PuyoGameScreen.java -  description
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

import java.awt.*;

public class PuyoGameScreen extends PuyoRessources implements Runnable
{
	protected static Thread currentThread;

	private PuyoGameScreen()
	{}

	public static synchronized void start()
	{
		currentThread = new Thread(new PuyoGameScreen());
		currentThread.start();
	}

	public static synchronized void stop()
	{
		currentThread = null;
	}
	
	public void run()
	{
		Thread thisThread = Thread.currentThread();
		
		while (thisThread == currentThread)
		{
			PuyoGameCanvas.refresh();
			thisThread.yield();
			thisThread.yield();
			thisThread.yield();
			thisThread.yield();
			thisThread.yield();
			PuyoTimer.waitTime(1);
		}
	}
	

	static void clear()
	{
		clearArea(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
	
	static void clearArea(int x, int y, int w, int h)
	{
		g.setClip(x, y, w, h);
		setBackgroundColor();
		g.fillRect(x,y,w,h);
	}

	static void clearAreaDebug(int x, int y, int w, int h)
	{
		g.setClip(x, y, w, h);
		g.setColor(Color.RED);
		g.fillRect(x,y,w,h);
	}

	static void drawUpdatePuyoTable()
	{
		PuyoGfx.drawUpdatePuyoTable();
	}
	
	static void drawPuyoTable()
	{
		int [][] puyoArray;
		int [] puyoHeightMap;

		setBackgroundColor();


		PuyoRessources.updateHeightMap();
		puyoHeightMap = PuyoRessources.getHeightMap();
		puyoArray= PuyoRessources.getPuyoArray();


		setBackgroundColor();

		
		for (int i = 0; i <NB_PUYO_ARRAY_WIDTH; i++)
		{
			for (int j = 0; j < NB_PUYO_ARRAY_HEIGHT; j++)
			{
				Image puyoImage;
				int colorID;
				int puyoX;
				int puyoY;

				colorID = puyoArray[i][j];
				puyoX = PUYO_TABLE_X + i * PUYO_WIDTH;
				puyoY = PUYO_TABLE_Y + PUYO_TABLE_HEIGHT - (j+1) * PUYO_HEIGHT;

				g.setClip(puyoX, puyoY,	PUYO_WIDTH, PUYO_HEIGHT);

				if (colorID != PuyoRessources.COLOR_EMPTY && (colorID&EXPLOSION_MASK) == 0)
				{
					puyoImage = PuyoRessources.getPuyoImage(colorID, PuyoTimer.getCurrentFrame());
					g.drawImage(puyoImage, puyoX, puyoY, null);
				} 
				else
			      {
					setBackgroundColor();
					g.fillRect(puyoX, puyoY, PUYO_WIDTH, PUYO_HEIGHT);
				}							  	 	
			}
	 
		}
	}


	static void drawPuyoNext(Puyo p1, Puyo p2)
	{
		Image image1;
		Image image2;
		int frameID;
		
		frameID = PuyoTimer.getCurrentFrame();
		image1 = PuyoRessources.getPuyoImage(p1.getColorID(), frameID);
		image2 = PuyoRessources.getPuyoImage(p2.getColorID(), frameID);

		g.setClip(PUYO_NEXT_X, PUYO_NEXT_Y, PUYO_NEXT_WIDTH, PUYO_NEXT_HEIGHT);
		g.drawImage(image1, PUYO_NEXT_X,PUYO_NEXT_Y,PUYO_WIDTH, PUYO_HEIGHT,null);
		g.drawImage(image2, PUYO_NEXT_X + PUYO_WIDTH, PUYO_NEXT_Y, PUYO_WIDTH, PUYO_HEIGHT,null);
	}


	protected static final java.awt.Color lineColor = new java.awt.Color(224, 160, 0);
	private static void setLineColor()
	{
		g.setColor(lineColor);
	}

	static void setBackgroundColor()
	{
		g.setColor(Color.BLACK);
	}

	static void setDebugColor()
	{
		g.setColor(Color.RED);
	}

	private static void drawPuyoTableBorder()
	{
		setLineColor();
		drawBox(PUYO_TABLE_X - 1, PUYO_TABLE_Y - 1,
			 PUYO_TABLE_HEIGHT+2 , PUYO_TABLE_WIDTH+2);
	}

	private static void drawBox(int x,int y, int w, int h)
	{
		g.drawLine(x + 1, y, x + w - 1, y);
		g.drawLine(x + 1, y+h , x + w - 1, y+h);
		g.drawLine(x, y + 1, x , y + h -2);
		g.drawLine(x + w , y + 1,     x + w, y + h -2);
	}

	static void waitForVsync()
	{
		PuyoTimer.waitTime(50);
	}	
}

