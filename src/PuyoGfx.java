/***************************************************************************
                         PuyoGfx.java -  description
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

import java.awt.Image;
//import java.awt.Graphics;



public class PuyoGfx extends PuyoRessources
{
	private static boolean IS_DEBUG = PuyoRessources.IS_DEBUG && false;
	
	// Puyo physic (acceleration, speed, ...)
	private final static int puyoAccelerationY = PUYO_ACCELERATION_Y; // 100


	
	private static final boolean LINE_MOTIF_9 [] = 
	{
		true, true, false, false, true, true, true, false, false
	};
	
	private static final boolean LINE_MOTIF_5 [] =
	{
		true, true, false, false, true
	};

	private static final boolean LINE_MOTIF_6 [] =
	{
		true, true, true, false, false, true
	};


	private static boolean lineMotif [] = LINE_MOTIF_9;
	private static boolean isStrippedLine = false;
	private static int lineDrawableIdx = 0;
	private static int linePuyoWidth [] = new int [NB_PUYO_ARRAY_WIDTH];
	

	static boolean init()
	{
		return true;
	}


	/* ----------------------------------- ANIMATIONS (title, intro, game over, ...) -------------------------------*/
	static void drawTitleAnimation()
	{
		final int BIG_DIGITS_X = 0;
		final int BIG_DIGITS_Y = 0;
		final int NB_BIG_DIGITS = 3;
		
		final int SMALL_DIGITS_X = 0 * PuyoRessources.SMALL_FONT_WIDTH;
		final int SMALL_DIGITS_Y = PuyoRessources.BIG_FONT_WIDTH;
		final int NB_SMALL_DIGITS = 4  + 2;

		final int NB_TOTAL_DIGITS = NB_BIG_DIGITS + NB_SMALL_DIGITS;	
	
		long currentTime;
		
		Image title;
		int titleX;
		int titleY;
		int w;
		int h;

		PuyoFont bigFont;
		PuyoFont smallFont;

		bigFont = PuyoRessources.getBigFont();
		smallFont = PuyoRessources.getSmallFont();
		
		titleX = TITLE_IMAGE_X;
		titleY = 0;
		title = PuyoRessources.getTitleImage();
		
		PuyoGameScreen.clear();
		PuyoTimer.start();

		while ((currentTime = PuyoTimer.getTime()) < TITLE_ANIMATION_DURATION)
		{
			int digitIdx;

			titleX = TITLE_IMAGE_X;
			titleY = TITLE_IMAGE_Y;
			digitIdx =  (int) (currentTime * NB_TOTAL_DIGITS) / TITLE_ANIMATION_DURATION;

			if (digitIdx == NB_TOTAL_DIGITS - 1 && currentTime + 500 > TITLE_ANIMATION_DURATION)
			{
				digitIdx = 0;
			}
			
			drawImage(title,titleX, titleY, TITLE_IMAGE_WIDTH, TITLE_IMAGE_HEIGHT);
			for (;digitIdx < NB_TOTAL_DIGITS ; digitIdx ++)
			{
				int randDigit;
				int digitPosX;
				int digitPosY;

				randDigit = PuyoRessources.randInt(10);
				
				// draw big digits
				if (digitIdx < NB_BIG_DIGITS)
				{
					digitPosX = titleX + digitIdx * PuyoRessources.BIG_FONT_WIDTH;
					digitPosY = titleY;
					bigFont.setPosition(digitPosX , digitPosY);
					bigFont.drawDigit(randDigit);	
				}
				
			       	// draw small digits
			       	else 
				{
					digitPosX = titleX + (digitIdx - NB_BIG_DIGITS /*+ 1*/) * PuyoRessources.SMALL_FONT_WIDTH;
					digitPosY = titleY + PuyoRessources.BIG_FONT_HEIGHT;
					smallFont.setPosition(digitPosX , digitPosY);
					smallFont.drawDigit(randDigit);	
				}	
				
		
			}	
			PuyoGameScreen.waitForVsync();
		}

		
		drawImage(title, TITLE_IMAGE_X,TITLE_IMAGE_Y, TITLE_IMAGE_WIDTH, TITLE_IMAGE_HEIGHT);
		PuyoTimer.waitTime(TITLE_ANIMATION_DURATION_WAIT);
	}

	

	static void drawIntroAnimation()
	{
		long currentTime;
		long timeLeft;
	
		int tableX;
		int tableY;
		int levelX;
		int levelY;
		int scoreX;
		int scoreY;
		int nextX;
		int nextY;
		int transX;
		int transY;
		Image score;
		Image level;
			
		score = getScoreImage();
		level = getLevelImage();

		PuyoTimer.start(); 
		
		while ((currentTime = PuyoTimer.getTime()) < INTRO_ANIMATION_DURATION)
		{
			timeLeft = INTRO_ANIMATION_DURATION - currentTime;
			transX = (int) - (timeLeft*PUYO_TABLE_WIDTH)/INTRO_ANIMATION_DURATION;
			transY = (int) - (timeLeft*PUYO_TABLE_HEIGHT)/INTRO_ANIMATION_DURATION;

			PuyoGameScreen.clear();

			// drawPuyoTableBorder
			{
				tableX = PUYO_TABLE_X + transX;
				tableY = PUYO_TABLE_Y;
				drawRectangleBorder(tableX, tableY, PUYO_TABLE_WIDTH, PUYO_TABLE_HEIGHT);
			}
	

			// drawLevelBorder
			{
				levelX = PUYO_LEVEL_X;	
				levelY = PUYO_LEVEL_Y + transY; 
				drawRectangleBorder(levelX, levelY, PUYO_LEVEL_WIDTH, PUYO_LEVEL_HEIGHT);
				drawImage(level, levelX, levelY, PUYO_LEVEL_WIDTH, PUYO_LEVEL_HEIGHT);
			}
	

			// drawScoreBorder
			{
				scoreX = PUYO_SCORE_X;	
				scoreY = PUYO_SCORE_Y + transY;	
				drawRectangleBorder(scoreX, scoreY, PUYO_SCORE_WIDTH, PUYO_SCORE_HEIGHT);
				drawImage(score,scoreX, scoreY, PUYO_SCORE_WIDTH, PUYO_SCORE_HEIGHT);
			}
	

			// drawNextPuyosBorder
			{
				nextX = PUYO_NEXT_X;
				nextY = PUYO_NEXT_Y + transY;
				drawRectangleBorder(nextX, nextY, PUYO_NEXT_WIDTH, PUYO_NEXT_HEIGHT);
			}

			PuyoGameScreen.waitForVsync();
		}

		PuyoGameScreen.clear();
		drawPuyoTableBorder();
		drawLevelBorder();
		drawScoreBorder();
		drawNextPuyosBorder();
		drawLevel();
		drawScore();
		//PuyoTimer.waitTime(1000 / FPS);		
		PuyoGameScreen.waitForVsync();
	}

	static void drawGameOverAnimation()
	{
		PuyoFont bigfont = getBigFont();
		Image gameover = getGameOverImage();

		long startTime; 
		long currentTime;

		PuyoGameScreen.clear();

		PuyoTimer.start();
		startTime = (23 * 60 + 56) * 1000;
		while ((currentTime = PuyoTimer.getTime() + startTime) < 24 * 60 * 1000)
		{
			drawBigTime((currentTime*60));
			PuyoGameScreen.waitForVsync();
		}

		drawBigTime(24 * 60 * 1000 * 60);
		PuyoTimer.waitTime(1000);
		
		PuyoGameScreen.clear();
		PuyoTimer.start();
		while (PuyoTimer.getTime()  < 3000)
		{
			drawImage(gameover, GAMEOVER_X, GAMEOVER_Y, GAMEOVER_WIDTH, GAMEOVER_HEIGHT);
			PuyoGameScreen.waitForVsync();
		}
	}
	
	
	static void drawUpdateScoreAnimation()
	{
		long start;
		long currentTime;

		start = System.currentTimeMillis();
		while ((currentTime = System.currentTimeMillis()) - start < UPDATE_SCORE_DURATION
				&& PuyoScore.isStopped())
		{
			setStrippedLine(true);
			drawScoreBorder();
			PuyoGameScreen.waitForVsync();
		}
		setStrippedLine(false);
		drawScoreBorder();
	}

	static void drawUpdateLevelAnimation()
	{
		long start;
		long currentTime;

		start = System.currentTimeMillis();
		while ((currentTime = System.currentTimeMillis()) - start < UPDATE_LEVEL_DURATION
				&& !PuyoLevel.isStopped())
		{
			setStrippedLine(true);
			drawLevelBorder();
			PuyoGameScreen.waitForVsync();
		}
		setStrippedLine(false);
		drawLevelBorder();
	}

	static void drawDifficultyAnimation()
	{
		int type = PuyoGameState.getDifficultyType();
		int value = PuyoGameState.getDifficultyValue();

		switch (type)
		{
			case PuyoGameState.DIFFICULTY_TYPE_ADD_PUYOS:
			{
				// drawAddpuyoLine ...
				int nbLines = value;
				drawAddLinesOnPuyoTable(nbLines);
				break;
			}
			case PuyoGameState.DIFFICULTY_TYPE_SHIFT_UP:
			{
				// shift up ...
				int nbPuyos = value;
				drawAddPuyos(nbPuyos);
				break;
			}
		}
		PuyoGameState.resetDifficulty();
	}
	
	
	static void drawUpdatePuyoTable()
	{
		int [] puyoTableY;
		int [][] puyoArray;
		boolean hasChange = true;
		int puyoDy;
		int puyoSpeedY;

		long startTime = System.currentTimeMillis();
		long currentTime;
		long elapsedTime;


		//updateHeightMap();
		puyoTableY = linePuyoWidth;
		puyoArray = getPuyoArray();
		
		puyoSpeedY = PUYO_SPEED_Y;
	
		while (hasChange)
		{
				
			hasChange = false;
			for (int puyoX = 0; puyoX < NB_PUYO_ARRAY_WIDTH; puyoX++)
			{
				puyoTableY[puyoX] = NB_PUYO_ARRAY_HEIGHT;
				for(int puyoY = 0; puyoY < NB_PUYO_ARRAY_HEIGHT; puyoY++)
				{
					if (puyoArray[puyoX][puyoY] == COLOR_EMPTY)
					{
						puyoTableY[puyoX] = puyoY;
						for(puyoY = puyoY + 1; puyoY < NB_PUYO_ARRAY_HEIGHT; puyoY++)
						{
							if (puyoArray[puyoX][puyoY] != COLOR_EMPTY)	
							{
								hasChange = true;
								break;
							}
						}
						break;
					}
				}
			}

			puyoDy = 0;	
			while (hasChange && puyoDy < PUYO_HEIGHT)
			{
				int frameID;
	
				frameID = PuyoTimer.getCurrentFrame();

				for (int puyoX =0; puyoX < NB_PUYO_ARRAY_WIDTH; puyoX++)
				{
					Image puyoImage;
					int colorID;
					int puyoPosX;
					int puyoPosY;
						
					puyoPosX = PUYO_TABLE_X + puyoX * PUYO_WIDTH;
				
					PuyoGameScreen.clearArea(puyoPosX,PUYO_TABLE_Y,PUYO_WIDTH, PUYO_TABLE_HEIGHT);

					// avant le trou :
					// on dessine normalement 
					for (int puyoY = 0; puyoY < puyoTableY[puyoX]; puyoY++)
					{

						colorID = puyoArray[puyoX][puyoY];
						puyoPosY = PUYO_TABLE_Y + PUYO_TABLE_HEIGHT - (puyoY+1) * PUYO_HEIGHT;
						g.setClip(puyoPosX,puyoPosY,PUYO_WIDTH, PUYO_HEIGHT);
						if (colorID != PuyoRessources.COLOR_EMPTY)
						{
							puyoImage = PuyoRessources.getPuyoImage(colorID, frameID);
							g.drawImage(puyoImage, puyoPosX, puyoPosY, null);
						}	
					       	else
					       	{
							PuyoGameScreen.setBackgroundColor();
							g.fillRect(puyoPosX, puyoPosY, PUYO_WIDTH, PUYO_HEIGHT);	
						}
					}
				
				
					// apres le trou :
					// on dessine avec puyoDy pixels de decalage
					for (int puyoY = puyoTableY[puyoX] + 1;
							puyoY < NB_PUYO_ARRAY_HEIGHT; puyoY++)
					{
						colorID = puyoArray[puyoX][puyoY];
						puyoPosY = PUYO_TABLE_Y + PUYO_TABLE_HEIGHT - (puyoY+1) * PUYO_HEIGHT + puyoDy;
						g.setClip(puyoPosX,puyoPosY,PUYO_WIDTH, PUYO_HEIGHT);
						if (colorID != PuyoRessources.COLOR_EMPTY)
						{
							puyoImage = PuyoRessources.getPuyoImage(colorID, PuyoTimer.getCurrentFrame());
							g.drawImage(puyoImage, puyoPosX, puyoPosY, null);
						}	 
						else
					       	{
							PuyoGameScreen.setBackgroundColor();
							g.fillRect(puyoPosX, puyoPosY, PUYO_WIDTH, PUYO_HEIGHT);	
						}	

					}

				}
			
				currentTime = System.currentTimeMillis();
				elapsedTime = currentTime - startTime;
	
				puyoSpeedY += puyoAccelerationY * elapsedTime;
				puyoDy +=(int)  ((puyoAccelerationY/2* Math.sqrt(elapsedTime) + puyoSpeedY* elapsedTime)/1000000000);

				//puyoDy =  ((int)elapsedTime * PUYO_HEIGHT) /400;
				PuyoGameScreen.waitForVsync();
			}
		
			if (hasChange)
			{		
				for (int puyoX =0; puyoX < NB_PUYO_ARRAY_WIDTH; puyoX++)
				{
					for (int puyoY = puyoTableY[puyoX]; puyoY <NB_PUYO_ARRAY_HEIGHT-1; puyoY ++)
					{
						puyoArray[puyoX][puyoY] = puyoArray[puyoX][puyoY+1];
					}
					if (puyoTableY[puyoX]!= NB_PUYO_ARRAY_HEIGHT)
					{
						puyoArray[puyoX][NB_PUYO_ARRAY_HEIGHT-1] = COLOR_EMPTY;
					}
				}
			}
		
		}// ------------ end of while --------	


		updateHeightMap();
		PuyoGameScreen.drawPuyoTable();
	}	


	/* ------------------------- STRIPPED LINES FX (rectangle,vertical, horizontal, ...) ---------------------*/
	static void drawRectangleBorder(int x, int y, int w, int h)
	{
		drawHorizontalLine(x, y - HORIZONTAL_LINE_HEIGHT, w);
		drawVerticalLine(x + VERTICAL_LINE_WIDTH+w, y, h);
		drawHorizontalLineInv(x, y + HORIZONTAL_LINE_HEIGHT+h, w);
		drawVerticalLineInv(x - VERTICAL_LINE_WIDTH, y, h);
	}

	
	private static void drawVerticalLine(int x, int y, int lineHeight)
	{
		Image vertical;
		
		vertical = PuyoRessources.getVerticalLineImage();
			
		for (int lineY = y; lineY <y +lineHeight; lineY+= VERTICAL_LINE_HEIGHT)
		{
			if (isLineDrawable())
			{
				drawImage(vertical, x- VERTICAL_LINE_WIDTH/2, lineY,
					VERTICAL_LINE_WIDTH, VERTICAL_LINE_HEIGHT);
			}
			else
		       	{
				clearArea( x- VERTICAL_LINE_WIDTH/2, lineY,
					VERTICAL_LINE_WIDTH, VERTICAL_LINE_HEIGHT);
			}
			nextStrippedLine();	
		}
	}

	private static void drawVerticalLineInv(int x, int y, int lineHeight)
	{
		Image vertical;

		vertical = PuyoRessources.getVerticalLineImage();
			
		for (int lineY = y + lineHeight - VERTICAL_LINE_HEIGHT; lineY >=y; lineY -= VERTICAL_LINE_HEIGHT)
		{
			if (isLineDrawable())
			{
				drawImage(vertical, x- VERTICAL_LINE_WIDTH/2, lineY,
					VERTICAL_LINE_WIDTH, VERTICAL_LINE_HEIGHT);
			}
			else
		       	{
				clearArea( x- VERTICAL_LINE_WIDTH/2, lineY,
					VERTICAL_LINE_WIDTH, VERTICAL_LINE_HEIGHT);
			}
			nextStrippedLine();	
		}
	}


	private static void drawHorizontalLine(int x, int y, int lineWidth)
	{
		Image horizontal;

		horizontal = PuyoRessources.getHorizontalLineImage();

		for (int lineX = x; lineX < x +lineWidth; lineX+=HORIZONTAL_LINE_WIDTH)
		{
			if (isLineDrawable())
			{
				drawImage(horizontal, lineX , y - HORIZONTAL_LINE_HEIGHT/2,
					HORIZONTAL_LINE_WIDTH,HORIZONTAL_LINE_HEIGHT);
			}
			else
		       	{
				clearArea( lineX , y - HORIZONTAL_LINE_HEIGHT/2,
					HORIZONTAL_LINE_WIDTH,HORIZONTAL_LINE_HEIGHT);
			}
			nextStrippedLine();	
		}
	}
	

	private static void drawHorizontalLineInv(int x, int y, int lineWidth)
	{
		Image horizontal;

		horizontal = PuyoRessources.getHorizontalLineImage();

		for (int lineX = x + lineWidth - HORIZONTAL_LINE_WIDTH; lineX >= x; lineX -= HORIZONTAL_LINE_WIDTH)
		{
			if (isLineDrawable())
			{
				drawImage(horizontal, lineX , y - HORIZONTAL_LINE_HEIGHT/2,
					HORIZONTAL_LINE_WIDTH,HORIZONTAL_LINE_HEIGHT);
			}
			else
			{
				clearArea( lineX , y - HORIZONTAL_LINE_HEIGHT/2,
					HORIZONTAL_LINE_WIDTH,HORIZONTAL_LINE_HEIGHT);
			}
			nextStrippedLine();		
		}
	}
	

	static void setLineMotif(boolean [] lineMotif)
	{
		synchronized (lineMotif)
		{
			synchronized (PuyoGfx.lineMotif)
			{
				PuyoGfx.lineMotif = lineMotif;
					
				lineDrawableIdx = (int) (System.currentTimeMillis()>>7) % lineMotif.length;
				if (lineDrawableIdx < 0)
				{
					lineDrawableIdx += lineMotif.length;
				}
			}
		}
	}
	static void setStrippedLine(boolean isStrippedLine)
	{
		PuyoGfx.isStrippedLine = isStrippedLine;
		if (!isStrippedLine)
		{
			drawPuyoTableBorder();
			drawLevelBorder();
			drawScoreBorder();
			drawNextPuyosBorder();
		}

	}
	
	private static boolean isLineDrawable()
	{	
		boolean ret;
		
		
		ret = lineMotif[lineDrawableIdx];
		

		return 	!isStrippedLine || ret;
	}

	private static void nextStrippedLine()
	{
		lineDrawableIdx ++;
		if (lineDrawableIdx >= lineMotif.length)
		{
			lineDrawableIdx = 0;
		}	
	}
	

	/* ---------------------------- GAME INFO (level,  score, next, time) ---------------------------------------*/
	static void drawLevel()
	{
		Image level;
		PuyoFont smallFont;

		level = getLevelImage();
		drawImage(level, PUYO_LEVEL_X, PUYO_LEVEL_Y, PUYO_LEVEL_WIDTH, PUYO_LEVEL_HEIGHT);

		smallFont = getSmallFont();
		synchronized (smallFont)
		{
			smallFont.setPosition(PUYO_LEVEL_X + PUYO_LEVEL_WIDTH - 4 * SMALL_FONT_WIDTH, 
				PUYO_LEVEL_Y + PUYO_LEVEL_HEIGHT - SMALL_FONT_HEIGHT);
			smallFont.draw2Digits(PuyoGameState.getLevel());
		}
	}
	
	static void drawScore()
	{
		Image score;
		PuyoFont smallFont;

		score = getScoreImage();
		drawImage(score, PUYO_SCORE_X, PUYO_SCORE_Y, PUYO_SCORE_WIDTH, PUYO_SCORE_HEIGHT);
		
		smallFont = getScoreFont();
		synchronized (scoreFont)
		{
			smallFont.setPosition(PUYO_SCORE_X + PUYO_SCORE_WIDTH - 5 * SMALL_FONT_WIDTH, 
				PUYO_SCORE_Y + PUYO_SCORE_HEIGHT - SMALL_FONT_HEIGHT);
			smallFont.draw4Digits(PuyoGameState.getScore());
		}
	}

		
	static void drawUpdateNextPuyos(Puyo p1, Puyo p2, Puyo next1, Puyo next2)
	{
		int currentColor1;
		int currentColor2;
		int nextColor1;
		int nextColor2;
		int transY;

		long start;
		long currentTime;


		currentColor1 = next1.getColorID();
		currentColor2 = next2.getColorID();		

		p1.setColorID(currentColor1);
		p2.setColorID(currentColor2);

		next1.setRandomColor();
		next2.setRandomColor();
		
		nextColor1 = next1.getColorID();
		nextColor2 = next2.getColorID();
		
		start = System.currentTimeMillis();
		while ( (currentTime = System.currentTimeMillis())  - start < UPDATE_NEXT_DURATION)
		{
			setStrippedLine(true);	
			drawNextPuyosBorder();
			PuyoGameScreen.waitForVsync();
		}
		setStrippedLine(false);	
		drawNextPuyosBorder();

		
		start = System.currentTimeMillis();
		while ( (currentTime = System.currentTimeMillis())  - start < UPDATE_NEXT_DURATION)
		{
			transY = (int)((currentTime - start) * PUYO_HEIGHT) / 800;
						
			g.setClip(PUYO_NEXT_X,PUYO_NEXT_Y,PUYO_NEXT_WIDTH, PUYO_NEXT_HEIGHT);
			g.drawImage(PuyoRessources.getPuyoImage(currentColor1, 0),
					PUYO_NEXT_X,
				       	PUYO_NEXT_Y - transY,
					null);
			g.drawImage(PuyoRessources.getPuyoImage(currentColor2, 0),
					PUYO_NEXT_X + PUYO_WIDTH,
					PUYO_NEXT_Y - transY,
					null);
			g.drawImage(PuyoRessources.getPuyoImage(nextColor1, 0),
					PUYO_NEXT_X,
					PUYO_NEXT_Y + PUYO_HEIGHT -transY,
					null);
			g.drawImage(PuyoRessources.getPuyoImage(nextColor2, 0),
					PUYO_NEXT_X + PUYO_WIDTH,
					PUYO_NEXT_Y + PUYO_HEIGHT - transY,
					null);

			g.setClip(PUYO_TABLE_X, PUYO_TABLE_Y, PUYO_TABLE_WIDTH, PUYO_HEIGHT);
			g.drawImage(PuyoRessources.getPuyoImage(currentColor1, 0),
					PUYO_TABLE_X - PUYO_WIDTH +  NB_PUYO_ARRAY_WIDTH/2 * PUYO_WIDTH,
					PUYO_TABLE_Y - PUYO_HEIGHT + transY,
					null);
			g.drawImage(PuyoRessources.getPuyoImage(currentColor2, 0),
					PUYO_TABLE_X + NB_PUYO_ARRAY_WIDTH/2 * PUYO_WIDTH,
					PUYO_TABLE_Y - PUYO_HEIGHT + transY,
					null);
			PuyoGfx.setStrippedLine(true);
			PuyoGfx.drawPuyoTableBorder();
			PuyoGameScreen.waitForVsync();		
		}
		
		
		g.setClip(PUYO_NEXT_X,PUYO_NEXT_Y,PUYO_NEXT_WIDTH, PUYO_NEXT_HEIGHT);
		g.drawImage(PuyoRessources.getPuyoImage(nextColor1, 0),
					PUYO_NEXT_X,
					PUYO_NEXT_Y,
					null);
		g.drawImage(PuyoRessources.getPuyoImage(nextColor2, 0),
					PUYO_NEXT_X + PUYO_WIDTH,
					PUYO_NEXT_Y,
					null);
		g.setClip(PUYO_TABLE_X, PUYO_TABLE_Y, PUYO_TABLE_WIDTH, PUYO_HEIGHT);

		g.drawImage(PuyoRessources.getPuyoImage(currentColor1, 0),
					PUYO_TABLE_X - PUYO_WIDTH +  NB_PUYO_ARRAY_WIDTH/2 * PUYO_WIDTH,
					PUYO_TABLE_Y,
					null);
		g.drawImage(PuyoRessources.getPuyoImage(currentColor2, 0),
					PUYO_TABLE_X + NB_PUYO_ARRAY_WIDTH/2 * PUYO_WIDTH,
					PUYO_TABLE_Y,
					null);
		PuyoGfx.setStrippedLine(false);
		PuyoGfx.drawPuyoTableBorder();		
	}

	static void DrawNextPuyos(Puyo next1, Puyo next2)
	{
		int	nextColor1 = next1.getColorID();
		int nextColor2 = next2.getColorID();

		g.setClip(PUYO_NEXT_X,PUYO_NEXT_Y,PUYO_NEXT_WIDTH, PUYO_NEXT_HEIGHT);
		g.drawImage(PuyoRessources.getPuyoImage(nextColor1, 0),
					PUYO_NEXT_X,
					PUYO_NEXT_Y,
					null);
		g.drawImage(PuyoRessources.getPuyoImage(nextColor2, 0),
					PUYO_NEXT_X + PUYO_WIDTH,
					PUYO_NEXT_Y,
					null);
	
	}

	static void drawAddPuyos(int nbPuyos)
	{
		int [][] puyoArray;
		int nbEmptySpaces;
		
		long start;
		long currentTime;
	

		puyoArray = getPuyoArray();
		nbEmptySpaces = 0;
		for (int puyoX = 0; puyoX < NB_PUYO_ARRAY_WIDTH; puyoX++)
		{
			
			if (puyoArray[puyoX][NB_PUYO_ARRAY_HEIGHT - 1] == COLOR_EMPTY)
			{
				nbEmptySpaces ++; 
			}	
			linePuyoWidth[puyoX] = COLOR_EMPTY;
		}

		nbPuyos = Math.min(nbPuyos, nbEmptySpaces);
		for (int i = 0; i < nbPuyos; i++)
		{
			int puyoX;
			int newColorID;

			newColorID = randInt(COLOR_COUNT);
			do 
			{
				puyoX = randInt(NB_PUYO_ARRAY_WIDTH);
			}
			while (puyoArray[puyoX][NB_PUYO_ARRAY_HEIGHT - 1] != COLOR_EMPTY);
				
			linePuyoWidth[puyoX] = newColorID;
		}

		start = System.currentTimeMillis();
		while ( (currentTime = System.currentTimeMillis())  - start <800)
		{
			int transY;
			
			transY = ((int)(currentTime - start) * PUYO_HEIGHT) / 800;
			
			PuyoGameScreen.drawPuyoTable();

			for (int puyoX = 0; puyoX < NB_PUYO_ARRAY_WIDTH; puyoX++)
			{
				if (linePuyoWidth[puyoX] != COLOR_EMPTY)
				{
					java.awt.Image puyoImage;
					int puyoPosX;
					int puyoPosY;

					puyoPosX  = PUYO_TABLE_X + puyoX * PUYO_WIDTH;
					puyoPosY  = PUYO_TABLE_Y - PUYO_HEIGHT + transY;
					
					puyoImage = getPuyoImage(linePuyoWidth[puyoX],0);
					synchronized (g)
					{
						g.setClip(puyoPosX, PUYO_TABLE_Y, PUYO_WIDTH, transY);
						g.drawImage(puyoImage, puyoPosX, puyoPosY, null);
					}
				}
			}
			//PuyoTimer.waitTime(50);		
			PuyoGameScreen.waitForVsync();
		}

		for (int puyoX = 0; puyoX < NB_PUYO_ARRAY_WIDTH; puyoX++)
		{
			if (linePuyoWidth[puyoX] != COLOR_EMPTY)
			{
				puyoArray[puyoX][NB_PUYO_ARRAY_HEIGHT - 1] = linePuyoWidth[puyoX];
			}
		}
	}

	static void drawAddLinesOnPuyoTable(int nbLines)
	{
		int [][] puyoArray;
		int nextColorID;
		int previousColorID;

		int transY;
		int moveY;
		long start;
		long currentTime;
	
	
		puyoArray = PuyoRessources.getPuyoArray();


		/*if (IS_DEBUG && nbLines == 1)
		{
			System.out.println("avant:");
			for (int i = 0; i < NB_PUYO_ARRAY_WIDTH; i++)
			{
				System.out.print('\t');
				System.out.print(puyoArray[i][0]);
			}
		}*/	

		
		
		//
		// -- G A M E  O V E R --
		//

		// check if the first line is empty
		for (int i = 0; i < NB_PUYO_ARRAY_WIDTH; i++)
		{
			if (puyoArray[i][NB_PUYO_ARRAY_HEIGHT - 1] != COLOR_EMPTY)
			{
				PuyoGameState.setGameState(PuyoGameState.GAME_OVER);
				return;
			}
		}	

		// shift up the puyo array
		for (int i = 0; i < NB_PUYO_ARRAY_WIDTH; i++)
		{
			for (int j = NB_PUYO_ARRAY_HEIGHT - 1; j>0; j--)
			{
				puyoArray[i][j] = puyoArray[i][j - 1];
			}
			puyoArray[i][0] = PuyoRessources.randInt(PuyoRessources.COLOR_COUNT);
		}
		//puyoArray[NB_PUYO_ARRAY_WIDTH-1][NB_PUYO_ARRAY_HEIGHT - 1] = COLOR_RED;
		
		
		PuyoGameScreen.clearArea(PUYO_TABLE_X, PUYO_TABLE_Y, PUYO_TABLE_WIDTH, PUYO_HEIGHT);
		transY = 0;
		moveY = 0;
		
		// draw the animation vertical scrolling
		start = System.currentTimeMillis();
		while ((currentTime = System.currentTimeMillis()) - start < 1000)
		{
			moveY = transY;
			transY =  (int)((currentTime - start) * PUYO_HEIGHT) / 1000;
			moveY = transY - moveY;
			
			// update the height map
			if (moveY != 0)
			{
				synchronized(getHeightMap())
				{
					int [] puyoHeightMap;

					puyoHeightMap = getHeightMap();
								
					for (int puyoX = 0; puyoX < NB_PUYO_ARRAY_WIDTH; puyoX++)
					{
						puyoHeightMap[puyoX] -= moveY;
					}
				}
			}
			
			// draw the vertical scrolling effect
			for (int puyoX = 0; puyoX < NB_PUYO_ARRAY_WIDTH; puyoX++)
			{
				Image puyoImage;
				int colorID;
				int puyoPosX;
				int puyoPosY;
				
					
				puyoPosX = PUYO_TABLE_X + puyoX * PUYO_WIDTH;

				for (int puyoY = 0; puyoY < NB_PUYO_ARRAY_HEIGHT; puyoY++)
				{
					colorID = puyoArray[puyoX][puyoY];
					puyoPosY = PUYO_TABLE_Y + PUYO_TABLE_HEIGHT - (puyoY) * PUYO_HEIGHT - transY;
					
					if (puyoPosY > PUYO_TABLE_Y )
					{
						g.setClip(puyoPosX,puyoPosY + transY,PUYO_WIDTH, PUYO_HEIGHT);
					}
					if (puyoPosY + PUYO_HEIGHT < PUYO_TABLE_Y + PUYO_TABLE_HEIGHT)
					{
						g.setClip(puyoPosX,puyoPosY,PUYO_WIDTH, PUYO_HEIGHT);
					}
					else
					{
						g.setClip(puyoPosX,puyoPosY,PUYO_WIDTH, transY);
					}
					if (colorID != PuyoRessources.COLOR_EMPTY)
					{
						puyoImage = PuyoRessources.getPuyoImage(colorID, PuyoTimer.getCurrentFrame());
						g.drawImage(puyoImage, puyoPosX, puyoPosY, null);
					}
				       	else
				       	{
						PuyoGameScreen.setBackgroundColor();
						g.fillRect(puyoPosX, puyoPosY, PUYO_WIDTH, PUYO_HEIGHT);	
					}					
				}
			}

			PuyoGameScreen.waitForVsync();
		}

		synchronized(getHeightMap())
		{
			updateHeightMap();	
		}
		
		if (nbLines > 1)
		{
			drawAddLinesOnPuyoTable(nbLines - 1);
		}

			
		if (IS_DEBUG)
		{
			/****************************************************************/
			//System.out.println("après:");
			//for (int i = 0; i < NB_PUYO_ARRAY_WIDTH; i++)
			//{
			//	System.out.print('\t');
			//	System.out.print(puyoArray[i][0]);
			//}
			//System.out.println();
			/********************************************************************/
		}
	}

	static boolean drawExplosionAnimation()
	{
		int [][] puyoArray;
		int colorID;
		int nbColorHeight;
		int nbColorWidth;
		
		long start;
		long currentTime;
		int count = 0;


		// search the explosion puyos 
		puyoArray = getPuyoArray();
		synchronized (puyoArray)
		{
	
			for (int puyoX = 0; puyoX <NB_PUYO_ARRAY_WIDTH; puyoX++)
			{
				for (int puyoY = 0; puyoY <NB_PUYO_ARRAY_HEIGHT; puyoY++)
				{
					colorID = puyoArray[puyoX][puyoY];

					if (colorID != COLOR_EMPTY && (colorID & EXPLOSION_MASK) == 0)
					{
						nbColorWidth = getNbColorWidth(puyoX, puyoY);
						nbColorHeight = getNbColorHeight(puyoX, puyoY);
					
						if (nbColorWidth >= 4 || nbColorHeight >= 4)
						{
							count += markExplosion(puyoX, puyoY);
						}
					}				
				}
			}
		}

		
		//	+---------------+---------------+
		//  	|     time     	|    frameID	|
		//  	|---------------+---------------|	
		// 	|   000 - 200	|	0	|
		// 	|   200 - 400  	|	1	|
		// 	|   400 - 600  	|	2	|
		// 	|   600 - 800  	|	3	|
		// 	+---------------+---------------+
		
		start = System.currentTimeMillis();
		while ((currentTime = System.currentTimeMillis()) - start < EXPLOSION_DURATION)
		{
			int frameID;
				
			frameID = (int) (((currentTime - start) * EXPLOSION_FRAME_COUNT) / EXPLOSION_DURATION);
			
			for (int puyoX = 0; puyoX < NB_PUYO_ARRAY_WIDTH; puyoX++)
			{
				for (int puyoY = 0; puyoY <NB_PUYO_ARRAY_HEIGHT; puyoY++)
				{
					int puyoPosX;
					int puyoPosY;
				
					colorID = puyoArray[puyoX][puyoY];
					puyoPosX = PUYO_TABLE_X + puyoX * PUYO_WIDTH;
					puyoPosY = PUYO_TABLE_Y + PUYO_TABLE_HEIGHT - (puyoY+1) * PUYO_HEIGHT;

					// set clipping
					g.setClip(puyoPosX, puyoPosY, PUYO_WIDTH, PUYO_HEIGHT);
	
					if (colorID != COLOR_EMPTY && (colorID & EXPLOSION_MASK) != 0)
					{
						boolean left;
						boolean right;
						boolean up;
						boolean down;
						int imageID;

						{
							down = checkArrayIndexY(puyoY - 1) 
								&& puyoArray[puyoX][puyoY - 1] == colorID;

							up = checkArrayIndexY(puyoY + 1)
								&& puyoArray[puyoX][puyoY + 1] == colorID;
								
							left = checkArrayIndexX(puyoX - 1)
								&& puyoArray[puyoX - 1][puyoY] == colorID;

							right = checkArrayIndexX(puyoX + 1) 
								&& puyoArray[puyoX + 1][puyoY] == colorID;
						}
						imageID = 0;
						{
							if (right)
							{
								imageID |= (1 << 0);
							}
							if (up)
							{
								imageID |= (1 << 1);
							}
							if (left)
							{
								imageID |= (1 << 2);
							}
							if (down)
							{
								imageID |= (1 << 3);
							}
						}
					
						//System.out.println(imageID);
						
						drawExplosionImageAnimation((byte) imageID, (byte) frameID,
							       			puyoPosX, puyoPosY);
					}
				}
			}
			PuyoGameScreen.waitForVsync();
		}


		if (count > 0)
		{
			//drawUpdateScoreAnimation(); // :-)
			PuyoScore.updateScore((count-2)*5);
		}


		// remove the explosion puyos
		synchronized (puyoArray)
		{
			for (int puyoX = 0; puyoX <NB_PUYO_ARRAY_WIDTH; puyoX++)
			{
				for (int puyoY = 0; puyoY <NB_PUYO_ARRAY_HEIGHT; puyoY++)
				{
					colorID = puyoArray[puyoX][puyoY];

					if (colorID != COLOR_EMPTY && (colorID & EXPLOSION_MASK) != 0)
					{
						puyoArray[puyoX][puyoY] = COLOR_EMPTY;
					}				
				}
			}
		}

		
		return count > 0;		
	}

	/*private*/ static void drawExplosionImageAnimation(byte imageID, byte frameID, int gfxPosX, int gfxPosY)
	{
		Image explosion;
		int w;
		int h;
		int clipPosX;
		int clipPosY;

		w = PUYO_WIDTH;
		h = PUYO_HEIGHT;
		clipPosX = gfxPosX;
		clipPosY = gfxPosY;

		if (gfxPosX + PUYO_WIDTH > PUYO_TABLE_X + PUYO_TABLE_WIDTH)
		{
			w = PUYO_TABLE_X + PUYO_TABLE_WIDTH - gfxPosX;
			w = Math.max(w, 0);
		}
		else if (gfxPosX < PUYO_TABLE_X)
		{
			w = PUYO_TABLE_X - gfxPosX;
			w = Math.max(w, 0);
			clipPosX += PUYO_WIDTH - w;
		}

		if (gfxPosY + PUYO_HEIGHT > PUYO_TABLE_Y + PUYO_TABLE_HEIGHT)
		{
			h = PUYO_TABLE_Y + PUYO_TABLE_HEIGHT - gfxPosY;
			h = Math.max(h, 0);
		}
		else if (gfxPosY < PUYO_TABLE_Y)
		{
			h = PUYO_TABLE_Y - gfxPosY;
			h = Math.max(h, 0);
			clipPosY += PUYO_HEIGHT - h;
		}

		explosion = getExplosionImage();
		
		g.setClip(clipPosX, clipPosY, w, h);
		g.drawImage(explosion,
				gfxPosX - frameID * EXPLOSION_WIDTH,
				gfxPosY - imageID * EXPLOSION_HEIGHT,null);
	}
	
	
	private static final boolean checkArrayIndices(int xIdx, int yIdx)
	{
		return xIdx >= 0 && xIdx < NB_PUYO_ARRAY_WIDTH
			&& yIdx >= 0 && yIdx < NB_PUYO_ARRAY_HEIGHT; 
	}
	
	private static final int getNbColorWidth(int x, int y)
	{
		int [][] puyoArray;
		int colorID;
		int count;

		count = 0;
		
		if (checkArrayIndices(x,y))
		{
			puyoArray = getPuyoArray();
			colorID = puyoArray[x][y];
		
			for (; x < NB_PUYO_ARRAY_WIDTH; x++)
			{
				if (colorID == puyoArray[x][y])
				{
					count ++; 	
				}
				else
				{
					break;
				}
			}
		}
		return count;	
	}
	
	private static final int getNbColorHeight(int x, int y)
	{
		int [][] puyoArray;
		int colorID;
		int count;

		count = 0;
		
		if (checkArrayIndices(x,y))
		{
			puyoArray = getPuyoArray();
			colorID = puyoArray[x][y];
		
			for (; y < NB_PUYO_ARRAY_HEIGHT; y++)
			{
				if (colorID == puyoArray[x][y])
				{
					count ++; 	
				}
				else
				{
					break;
				}
			}
		}
		return count;	
	}
	
	private static final boolean equalColorID(int colorID, int colorID2)
	{
		return (colorID|EXPLOSION_MASK) == (colorID2|EXPLOSION_MASK);
	}

	private static final int markExplosionHorizontal(int colorID, int x, int y)
	{
		int [][] puyoArray;
		int count;
		int first;
		int end;

		count = 0;
		puyoArray = getPuyoArray();

		if (checkArrayIndices(x, y) && equalColorID(colorID, puyoArray[x][y]))
		{
			for (first = x; checkArrayIndexX(first); first --)
			{
				if (!equalColorID(colorID, puyoArray[first][y]))
				{
					first ++;
					break;
				}
			}
			if (!checkArrayIndexX(first))
			{
				first ++;
			}
			
			for (end = x; checkArrayIndexX(end); end ++)
			{
				if (!equalColorID(colorID, puyoArray[end][y]))
				{
					end --;
					break;
				}
			}
			if (!checkArrayIndexX(end))
			{
				end --;
			}
			
			count = end - first + 1;
			if (count >= 4)
			{
				for (int i = first; i <= end; i++)
				{
					puyoArray[i][y] |= EXPLOSION_MASK;
					if (i != x)
					{
						count += markExplosionVertical(colorID, i, y);
					}
				}
			} 
			else
			{
				count = 0;
			}

			puyoArray[x][y] |= EXPLOSION_MASK;		
		}
		return count;
	}
	
	private static final boolean checkArrayIndexX(int x)
	{
		return x >= 0 && x < NB_PUYO_ARRAY_WIDTH;
	}
		
	private static final boolean checkArrayIndexY(int y)
	{
		return y >= 0 && y < NB_PUYO_ARRAY_HEIGHT;
	}

	private static final int markExplosion(int x, int y)
	{
		int [][] puyoArray;
		int colorID;
		int count;

		count = 0;
		puyoArray = getPuyoArray();
		
		if (checkArrayIndices(x, y))
		{
			colorID = puyoArray[x][y];
			if (colorID != COLOR_EMPTY)
			{
				count += markExplosionVertical(colorID, x, y);
				count += markExplosionHorizontal(colorID, x, y);
			}
		}
		return count;
	}	

	private static final int markExplosionVertical(int colorID, int x, int y)
	{
		int [][] puyoArray;
		int count;
		int first;
		int end;


		count = 0;
		puyoArray = getPuyoArray();

		if (checkArrayIndices(x, y) && equalColorID(colorID, puyoArray[x][y]))
		{
			for (first = y; checkArrayIndexY(first); first--)
			{
				if (!equalColorID(colorID, puyoArray[x][first]))
				{
					first ++;
					break;
				}
			}
			if (!checkArrayIndexY(first))
			{
				first ++;
			}
			
			for (end = y; checkArrayIndexY(end); end ++)
			{
				if (!equalColorID(colorID, puyoArray[x][end]))
				{
					end --;
					break;
				}
			}
			if (!checkArrayIndexY(end))
			{
				end --;
			}
			
			count = end - first + 1;
			if (count >= 4)
			{
				for (int i = first; i <= end; i++)
				{
					puyoArray[x][i] |= EXPLOSION_MASK;
					if (i != y)
					{
						count += markExplosionHorizontal(colorID, x, i);
					}
				}
			} 
			else
			{
				count = 0;
			}
			puyoArray[x][y] |= EXPLOSION_MASK;		
		}
		return count;
	}

	static void drawTime()
	{
		int t = (int) PuyoTimer.getTime();
		int hrs = t / (60 * 1000);
		int min = (t / 1000) % 60;
		
		PuyoFont f = PuyoRessources.getSmallTimeFont();
		synchronized (f)
		{
			f.setPosition(PUYO_TIME_X, PUYO_TIME_Y);
			f.draw2Digits(hrs);
			f.drawDigit(-1);
			f.draw2Digits(min);
		}
	}

	static void drawPuyoTableBorder()
	{
		setLineMotif(LINE_MOTIF_9);
		drawRectangleBorder(PUYO_TABLE_X, PUYO_TABLE_Y, 
				PUYO_TABLE_WIDTH, PUYO_TABLE_HEIGHT);
	}

	static void drawLevelBorder()
	{
		setLineMotif(LINE_MOTIF_5);
		drawRectangleBorder(PUYO_LEVEL_X, PUYO_LEVEL_Y, 
				PUYO_LEVEL_WIDTH, PUYO_LEVEL_HEIGHT);
	}


	static void drawScoreBorder()
	{
		setLineMotif(LINE_MOTIF_5);
		drawRectangleBorder(PUYO_SCORE_X, PUYO_SCORE_Y, 
				PUYO_SCORE_WIDTH, PUYO_SCORE_HEIGHT);
	}

	static void drawNextPuyosBorder()
	{
		setLineMotif(LINE_MOTIF_6);
		drawRectangleBorder(PUYO_NEXT_X, PUYO_NEXT_Y, 
				PUYO_NEXT_WIDTH, PUYO_NEXT_HEIGHT);
	}



	/* --------------------------------------------- TIME DISPLAY FX ----------------------------------------- */
	static void drawBigTime(long ms)
	{
		PuyoFont bigFont;
		PuyoFont smallFont;
		int x;
		int y;
		int w;
		int h;
		int hrs;
		int min;
		int sec;

		bigFont = getBigTimeFont();
		smallFont = getSmallTimeFont();
		w = 5 * BIG_FONT_WIDTH + 3 * SMALL_FONT_WIDTH;
		h = BIG_FONT_HEIGHT;
		

		x = SCREEN_WIDTH / 2 - w / 2;
		y = SCREEN_HEIGHT / 2 - h / 2;	

		hrs = getBigTimeHours(ms);
		min = getBigTimeMinutes(ms);
		sec = getBigTimeSeconds(ms);
		
		synchronized (bigFont)
		{
			bigFont.setPosition(x, y);
			bigFont.draw2Digits(hrs);
			bigFont.drawDigit(-1);
			bigFont.draw2Digits(min);
		}
		
		synchronized (smallFont)
		{
			smallFont.setPosition(x + 5 * BIG_FONT_WIDTH, y + BIG_FONT_HEIGHT/2); 
			smallFont.drawDigit(-1);
			smallFont.draw2Digits(sec);
		}
	}
	

	private static final int getBigTimeHours(long ms)
	{
		int hrs = (int) (ms / (60 * 60 * 1000));
		hrs = hrs % 24;
		
		if (hrs < 0)
		{
			hrs += 24;
		}
		return hrs;
	}

	private static final int getBigTimeMinutes(long ms)
	{
		int min = (int) (ms / (60 * 1000));
		
		min = min % 60;
		
		if (min < 0)
		{
			min += 60;
		}
		return min;
	}

	private static final int getBigTimeSeconds(long ms)
	{
		int sec = ((int) ms / 1000) % 60;
		
		if (sec < 0) 
		{
			sec += 60;
		}
		return sec;
	}

	/* ---------------------------------- GFX (drawImage, clearArea, ...) ---------------------------- */	
	static void drawImage(Image img, int x,int y, int w, int h)
	{
		g.setClip(x,y,w,h);
		g.drawImage(img,x,y,null);
	}	

	static void clearArea(int x, int y, int w,int h)
	{
		PuyoGameScreen.clearArea(x,y,w,h);
	}

	static void printTable()
	{
		if (!IS_DEBUG) return;
		for (int j = NB_PUYO_ARRAY_HEIGHT - 1 ; j >=0; j--)
		{
			for (int i = 0;  i < NB_PUYO_ARRAY_WIDTH; i++)
			{
				char c = '?';
				switch (puyoArray[i][j])
				{
					case COLOR_RED:
					{
						c = 'R';
						break;
					}
					case COLOR_GREEN:
					{
						c = 'G';
						break;
					}	
					case COLOR_YELLOW:
					{
						c = 'Y';
						break;
					}
					case COLOR_BLUE:
					{
						c = 'B';
						break;
					}
					case COLOR_EMPTY:
					{
						c = '_';
					}
				}

						
				System.out.print(c);
			}
			System.out.println();	
		}

	}
}



