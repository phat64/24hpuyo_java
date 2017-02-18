/***************************************************************************
                     PuyoRessources.java -  description
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


//import java.awt.*;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics;


public class PuyoRessources
{
	// debugging infos
	static final boolean IS_DEBUG = false;

	// puyo colors
	static final byte COLOR_RED    = 0;
	static final byte COLOR_GREEN  = 1;
	static final byte COLOR_BLUE   = 2;
	static final byte COLOR_YELLOW = 3;
	static final byte COLOR_EMPTY  = -1;
	static final int COLOR_COUNT  = 4;
	static final int FRAME_COUNT  = 4;

	// puyo physics
	static final int PUYO_WIDTH = 32;
	static final int PUYO_HEIGHT = PUYO_WIDTH;
	static final int PUYO_RADIUS = PUYO_WIDTH/2;
	static final int PUYO_DX = PUYO_RADIUS * 2;
	static final int PUYO_DY = PUYO_RADIUS / 4;
	static final int PUYO_UPDATE_MOVE_DOWN = PUYO_RADIUS / 8;
	static final int PUYO_SPEED_Y = 200;
	static final int PUYO_ACCELERATION_Y = 200;

	// puyo explosion
	static final byte EXPLOSION_MASK   = 1 << 4;
	static final byte EXPLOSION_RED    = EXPLOSION_MASK | COLOR_RED;
	static final byte EXPLOSION_GREEN  = EXPLOSION_MASK | COLOR_GREEN;
	static final byte EXPLOSION_BLUE   = EXPLOSION_MASK | COLOR_BLUE;
	static final byte EXPLOSION_YELLOW = EXPLOSION_MASK | COLOR_YELLOW;
	static final int EXPLOSION_WIDTH = PUYO_WIDTH + 1;
	static final int EXPLOSION_HEIGHT = PUYO_HEIGHT + 1;
	static final int EXPLOSION_FRAME_COUNT = 4;
	static final long EXPLOSION_DURATION = 800;

	// puyo 2d array
	static final int NB_PUYO_ARRAY_WIDTH = 6;
	static final int NB_PUYO_ARRAY_HEIGHT = 12;
	static int puyoHeightMap [] = new int [NB_PUYO_ARRAY_WIDTH];
	static int puyoArray [][] =
		new int [NB_PUYO_ARRAY_WIDTH][NB_PUYO_ARRAY_HEIGHT];

	// puyo filenames
	protected static final String IMAGES_FILENAMES []=
	{"puyo_red_00.gif", "puyo_red_01.gif", "puyo_red_02.gif", "puyo_red_03.gif",
	 "puyo_green_00.gif", "puyo_green_01.gif", "puyo_green_02.gif", "puyo_green_03.gif",
	 "puyo_blue_00.gif", "puyo_blue_01.gif", "puyo_blue_02.gif", "puyo_blue_03.gif",
	 "puyo_yellow_00.gif", "puyo_yellow_01.gif", "puyo_yellow_02.gif", "puyo_yellow_03.gif"
	};

	// puyo key controls
	static final int KEY_LEFT = java.awt.event.KeyEvent.VK_LEFT;
 	static final int KEY_RIGHT = java.awt.event.KeyEvent.VK_RIGHT;
	static final int KEY_UP = java.awt.event.KeyEvent.VK_UP;
	static final int KEY_DOWN = java.awt.event.KeyEvent.VK_DOWN;
	static final int KEY_EXIT = java.awt.event.KeyEvent.VK_ESCAPE;

	// puyo gfx (intro, level, score)
	protected static final String TITLE_IMAGE_FILENAME = "puyo_24h_title.gif";
	protected static final String LEVEL_IMAGE_FILENAME = "puyo_24h_level.gif";
	protected static final String SCORE_IMAGE_FILENAME = "puyo_24h_score.gif";
	protected static final String GAMEOVER_IMAGE_FILENAME = "puyo_24h_gameover.gif";
	protected static final String EXPLOSION_IMAGE_FILENAME = "puyo_24h_explosion.gif";
	protected static Image titleImage;
	protected static Image levelImage;
	protected static Image scoreImage;
	protected static Image gameoverImage;
	protected static Image explosionImage;


	// puyo gfx misc.
	protected static final String VERTICAL_LINE_IMAGE_FILENAME = "1.gif";
	protected static final String HORIZONTAL_LINE_IMAGE_FILENAME = "2.gif";
	static final int VERTICAL_LINE_WIDTH = 7;
	static final int VERTICAL_LINE_HEIGHT = PUYO_HEIGHT;
	static final int HORIZONTAL_LINE_WIDTH = VERTICAL_LINE_HEIGHT;
	static final int HORIZONTAL_LINE_HEIGHT = VERTICAL_LINE_WIDTH;
	protected static Image verticalLineImage;
	protected static Image horizontalLineImage;


	// puyo fonts (small & big)
	static final String SMALL_FONT_IMAGE_FILENAME = "puyo_24h_small_digits.gif";
	static final String BIG_FONT_IMAGE_FILENAME = "puyo_24h_big_digits.gif";
	static final int SMALL_FONT_WIDTH = 16;
	static final int SMALL_FONT_HEIGHT = 32;
	static final int BIG_FONT_WIDTH = 32;
	static final int BIG_FONT_HEIGHT = 64;
	protected static PuyoFont smallFont;
	protected static PuyoFont bigFont;
	protected static PuyoFont bigTimeFont;
	protected static PuyoFont smallTimeFont;
	protected static PuyoFont scoreFont;
	protected static PuyoFont levelFont;
	protected static PuyoFont fpsFont;



	// puyo screen info
	static final int SCREEN_WIDTH = (7 + NB_PUYO_ARRAY_WIDTH) * PUYO_WIDTH;
	static final int SCREEN_HEIGHT = (2 + NB_PUYO_ARRAY_HEIGHT) * PUYO_HEIGHT;
	static Image screenImage;
	static Graphics g;

	// puyo table info (in screen)
	static final int PUYO_TABLE_X = PUYO_WIDTH;
	static final int PUYO_TABLE_Y = PUYO_HEIGHT;
	static final int PUYO_TABLE_WIDTH = NB_PUYO_ARRAY_WIDTH * PUYO_WIDTH;
	static final int PUYO_TABLE_HEIGHT = NB_PUYO_ARRAY_HEIGHT * PUYO_HEIGHT;

	// puyo screen info (level, score, next puyos)
 	static final int PUYO_NEXT_WIDTH  = PUYO_WIDTH * 2;
	static final int PUYO_NEXT_HEIGHT = PUYO_HEIGHT;
	static final int PUYO_NEXT_X = (PUYO_TABLE_X + PUYO_TABLE_WIDTH) + 2 * PUYO_WIDTH/* + PUYO_NEXT_WIDTH/2*/;
	static final int PUYO_NEXT_Y = (PUYO_TABLE_Y + PUYO_TABLE_HEIGHT) - PUYO_NEXT_HEIGHT - 2 * PUYO_HEIGHT;

	// level image
	static final int PUYO_LEVEL_X = PUYO_NEXT_X - SMALL_FONT_WIDTH;
	static final int PUYO_LEVEL_Y = (PUYO_TABLE_Y + PUYO_HEIGHT) ;
	static final int PUYO_LEVEL_WIDTH = 6 * SMALL_FONT_WIDTH;
	static final int PUYO_LEVEL_HEIGHT = 2 *SMALL_FONT_HEIGHT;


	// score image
	static final int PUYO_SCORE_X = PUYO_LEVEL_X;
	static final int PUYO_SCORE_Y = PUYO_LEVEL_Y + PUYO_LEVEL_HEIGHT +  2*PUYO_HEIGHT;
	static final int PUYO_SCORE_WIDTH = PUYO_LEVEL_WIDTH;
	static final int PUYO_SCORE_HEIGHT = PUYO_LEVEL_HEIGHT;

	// gameover image
	static final int GAMEOVER_WIDTH = 4 * BIG_FONT_WIDTH;
	static final int GAMEOVER_HEIGHT =  2 * BIG_FONT_HEIGHT;
	//static final int GAMEOVER_X = PUYO_TABLE_X + PUYO_TABLE_WIDTH/2 - GAMEOVER_WIDTH/2;
	//static final int GAMEOVER_Y = PUYO_TABLE_Y + PUYO_TABLE_HEIGHT/2 - GAMEOVER_HEIGHT/2;
	static final int GAMEOVER_X = SCREEN_WIDTH/2 - GAMEOVER_WIDTH/2;
	static final int GAMEOVER_Y = SCREEN_HEIGHT/2 - GAMEOVER_HEIGHT/2;


	// time
	static final int PUYO_TIME_X = PUYO_NEXT_X - SMALL_FONT_WIDTH/2;
	static final int PUYO_TIME_Y = PUYO_NEXT_Y + PUYO_NEXT_HEIGHT + PUYO_WIDTH;
	static final int PUYO_TIME_WIDTH = 5 * SMALL_FONT_WIDTH;
	static final int PUYO_TIME_HEIGHT = SMALL_FONT_HEIGHT;


	// puyo title animation info (...)
	static final int TITLE_ANIMATION_DURATION = 7 * 1000;
	static final int TITLE_ANIMATION_DURATION_WAIT = 2500;
	static final int TITLE_IMAGE_WIDTH = 3 * BIG_FONT_WIDTH;
	static final int TITLE_IMAGE_HEIGHT =  BIG_FONT_HEIGHT +  SMALL_FONT_HEIGHT;
	static final int TITLE_IMAGE_X = SCREEN_WIDTH/2 - TITLE_IMAGE_WIDTH/2;
	static final int TITLE_IMAGE_Y = SCREEN_HEIGHT/2 - TITLE_IMAGE_HEIGHT/2;

	// puyo intro animation info (...)
	static final int INTRO_ANIMATION_DURATION = 2000;
	static final int INTRO_ANIMATION_DURATION_WAIT = 500;

	// puyo animations durations (...)
 	static final long UPDATE_LEVEL_DURATION = 2000;
	static final long UPDATE_SCORE_DURATION = 2000;
	static final long UPDATE_SCORE_STEP_DURATION = 100;
	static final long UPDATE_NEXT_DURATION = 1000;



	// puyo Frames Per Second
	static final int FPS = 30;

	// puyo images (4 colors)
	static Image puyoImages [];

	/*static ()
	{
		init();
	}*/



	/* ---------------------------- RESSOURCES INIT (screen, image, array) ---------------------------------- */
	static boolean init()
	{
		//System.out.println(PuyoRessources.getImage("lmkdsfml.bmp"));
		setScreen();
		setPuyoArrayAndHeightMap();
		for (int i = 1; i < 12; i+=2)
		{
			//puyoArray[2][i] = 3;
			//puyoArray[4][i-1] = 2;
		}
		/*puyoArray[2][11] = 3;
		puyoArray[2][2] = 3;
		puyoArray[2][0] = 3;*/
		/*return */
		boolean ret = setPuyoImages();
		return ret;
	}

	private static boolean setPuyoImages()
	{
		boolean ret = true;
		int imageID;
		Image bigFontImage;
		Image smallFontImage;


		// fonts init (big, small, ...)
		{
			bigFontImage = getImage(BIG_FONT_IMAGE_FILENAME);
			if (bigFontImage != null)
			{
				bigFont = new PuyoFont(bigFontImage,
							BIG_FONT_WIDTH,
							BIG_FONT_HEIGHT);

				bigFont.setGraphics(getScreenGraphics());
				bigTimeFont = (PuyoFont) bigFont.clone();
			}

			smallFontImage = getImage(SMALL_FONT_IMAGE_FILENAME);
			if (smallFontImage != null)
			{
				smallFont = new PuyoFont(smallFontImage,
							SMALL_FONT_WIDTH,
							SMALL_FONT_HEIGHT);

				smallFont.setGraphics(getScreenGraphics());
				smallTimeFont = (PuyoFont) smallFont.clone();
				scoreFont = (PuyoFont) smallFont.clone();
				levelFont = (PuyoFont) smallFont.clone();
				fpsFont   = (PuyoFont) smallFont.clone();
			}
			ret = ret && bigFontImage!=null && smallFontImage!=null;
		}


		// images init (intro, score, level)
		{
			titleImage = getImage(TITLE_IMAGE_FILENAME);
			scoreImage = getImage(SCORE_IMAGE_FILENAME);
			levelImage = getImage(LEVEL_IMAGE_FILENAME);
			gameoverImage = getImage(GAMEOVER_IMAGE_FILENAME);
			ret = ret && titleImage!=null && scoreImage!=null;
			ret = ret && levelImage!=null && gameoverImage!=null;
		}

		// images misc. (line, ...)
		{
			horizontalLineImage =getImage(HORIZONTAL_LINE_IMAGE_FILENAME);
			verticalLineImage = getImage(VERTICAL_LINE_IMAGE_FILENAME);

			ret = ret && verticalLineImage!=null && horizontalLineImage!=null;
		}

		if (puyoImages == null)
		{
			puyoImages = new Image [COLOR_COUNT * FRAME_COUNT];
		}

		// puyo colors init
		imageID =0;
		for (int colorID = 0; colorID < COLOR_COUNT; colorID++)
		{
			for (int frameID = 0; frameID < FRAME_COUNT; frameID++)
			{
				String filename;
				Image image;

				filename = IMAGES_FILENAMES[imageID];
				image = getImage(filename);
				puyoImages[imageID] = image;
				ret = ret && image!=null;
				imageID ++;
			}
		}

		// explosion fx image
		explosionImage = getImage(EXPLOSION_IMAGE_FILENAME);
		ret = ret && explosionImage != null;


		return ret;
	}

	static void free()
	{
		// the datas
		puyoArray = null;
		puyoHeightMap = null;

		// the images
		titleImage = null;
		levelImage = null;
		scoreImage = null;
		gameoverImage = null;
		explosionImage = null;

		// the fonts
		bigFont = null;
		bigTimeFont = null;
		smallFont = null;
		smallTimeFont = null;
		scoreFont = null;
		levelFont = null;
		fpsFont = null;

		screenImage = null;
		//g = null

		System.gc();
	}

	/* ----------------------------- FONTS (small, big, time, level, score, fps) ---------------*/
	static PuyoFont getBigFont()
	{	return bigFont;}

	static PuyoFont getSmallFont()
	{	return smallFont;}

	static PuyoFont getSmallTimeFont()
	{	return smallTimeFont;}

	static PuyoFont getBigTimeFont()
	{	return bigTimeFont;}

	static PuyoFont getLevelFont()
	{	return levelFont;}

	static PuyoFont getScoreFont()
	{	return scoreFont;}

	static PuyoFont getFpsFont()
	{	return fpsFont;}


	/* ------------------------------- ARRAY (update, height map, ...)-------------------------------*/

	static int [][] getPuyoArray()
	{	return puyoArray;}

	static int [] getHeightMap()
	{	return puyoHeightMap;}

	static void updateHeightMap()
	{
		int nbPuyos;
		for (int i = 0; i < NB_PUYO_ARRAY_WIDTH; i++)
		{
			nbPuyos = 0;
			puyoHeightMap [i] = 0;

			for (int j = NB_PUYO_ARRAY_HEIGHT - 1 ; j>=0; j--)
			{
				if (puyoArray[i][j] != COLOR_EMPTY)
				{
					nbPuyos = j + 1;
					break;
				}
			}

			puyoHeightMap [i] = (NB_PUYO_ARRAY_HEIGHT - nbPuyos) * PUYO_HEIGHT;
			//System.out.print(i);
			//System.out.print(" : height(");
			//System.out.print(puyoHeightMap [i]);
			//System.out.print(", ");
			//System.out.print(nbPuyos);
			//System.out.println(")");
		}
	}

	/*private*/ static void setPuyoArrayAndHeightMap()
	{
		for (int i=0; i < NB_PUYO_ARRAY_WIDTH; i++)
		{
			//puyoHeightMap [i] = 0;

			for (int j =0; j <NB_PUYO_ARRAY_HEIGHT; j++)
			{
				puyoArray[i][j] = COLOR_EMPTY;
			}
		}
		updateHeightMap();
	}

	static boolean isFilledPuyoArray()
	{
		return 	puyoArray[NB_PUYO_ARRAY_WIDTH/2 -1][NB_PUYO_ARRAY_HEIGHT-1] != COLOR_EMPTY
			|| puyoArray[NB_PUYO_ARRAY_WIDTH/2][NB_PUYO_ARRAY_HEIGHT-1] != COLOR_EMPTY;
	}


	/* ------------------------------- SCREEN (setting, Graphics, ...)-------------------------------*/
	static void setScreen()
	{
		screenImage = new java.awt.image.BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT,
			       				java.awt.image.BufferedImage.TYPE_INT_RGB);
		g = screenImage.getGraphics();
	}

	static void setGraphics(Graphics g)
	{	PuyoRessources.g = g;}

	static Graphics getScreenGraphics()
	{	return g;}

	static Image getScreenImage()
	{	return screenImage;}


	/* ------------------------------- IMAGES (puyo, title, level, score, ...) ------------------------------*/
	static Image getPuyoImage(int colorID, int frameID)
	{	try
		{
			return puyoImages[colorID * 4 + frameID];
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.err.print("colorID =");
			System.err.println(colorID);
			System.err.print("frameID =");
			System.err.println(frameID);
			return null;
		}
	}

	static Image getExplosionImage()
	{	return explosionImage;}

	static Image getTitleImage()
	{	return titleImage;}

	static Image getLevelImage()
	{	return levelImage;}

	static Image getScoreImage()
	{	return scoreImage;}

	static Image getVerticalLineImage()
	{	return verticalLineImage;}

	static Image getHorizontalLineImage()
	{	return horizontalLineImage;}

	static Image getGameOverImage()
	{	return gameoverImage;}

	private static Image getImage(String filename)
	{
		Toolkit toolkit;
		Image img;
		java.awt.MediaTracker m;

		m = new java.awt.MediaTracker(PuyoGameCanvas.canvas);
		toolkit = Toolkit.getDefaultToolkit();
		img = toolkit.getImage("res/"+filename);

		
		m.addImage(img,1);
		m.checkAll(true);
		
		return img;
	}

	/* ----------------------------------- MISC (randInt, ...) -------------------------------- */
	static int randInt(int max)
	{
		return (int)(Math.random()*max);
	}
}
