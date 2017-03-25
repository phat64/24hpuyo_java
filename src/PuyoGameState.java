/***************************************************************************
                    PuyoGameState.java -  description
                             -------------------
    begin                : Fri Apr 15 2005
    copyright            : (C) 2005 by Tachouct Mustapha
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

public class PuyoGameState
{
	// the states informations
	private static int gs = PuyoGameState.STARTED;
	private static int level = 0;
	private static int score = 0;

	// the game states
	final static int STARTED = 10;
	final static int FINISHED = -1;
	final static int TITLE = 11;
	final static int INTRO = 12;
	final static int LEVEL_STARTED = 20;
	final static int LEVEL_RUN = 21;
	final static int LEVEL_SCORE_UPDATE = 23;
	final static int LEVEL_FINISHED = 22;

	final static int LEVEL_COUNT = 24;
	final static int CREDIT = 30;
	final static int MAIN_MENU =40;
	final static int GAME_OVER = 50;


	// difficulty type (add some puyos, shift up, ...)
	final static byte DIFFICULTY_TYPE_EMPTY = -1;
	final static byte DIFFICULTY_TYPE_ADD_PUYOS = 0;
	final static byte DIFFICULTY_TYPE_SHIFT_UP = 1;
	final static int DIFFICULTY_TYPE_COUNT = 2;

	// difficulty value (nb puyos added or nb lines shift up)
	final static byte DIFFICULTY_VALUE_NULL = 0;
	final static byte DIFFICULTY_VALUE_1 = 1;
	final static byte DIFFICULTY_VALUE_2 = 2;
	final static byte DIFFICULTY_VALUE_3 = 3;
	final static byte DIFFICULTY_VALUE_4 = 4;
	final static byte DIFFICULTY_VALUE_5 = 5;
	final static byte DIFFICULTY_VALUE_6 = 6;
	final static int DIFFICULTY_COUNT = 6;

	// the current difficulty
	protected static byte difficultyType = DIFFICULTY_TYPE_EMPTY;
	protected static byte difficultyValue = DIFFICULTY_VALUE_NULL;

	// diffculty by level
	protected static byte difficultyLevelArray [][] =
	{
		{DIFFICULTY_TYPE_EMPTY,	 	DIFFICULTY_VALUE_NULL}, // level 00
		{DIFFICULTY_TYPE_SHIFT_UP,	DIFFICULTY_VALUE_1}, 	// level 01
		{DIFFICULTY_TYPE_EMPTY, 	DIFFICULTY_VALUE_NULL}, // level 02
		{DIFFICULTY_TYPE_ADD_PUYOS, 	DIFFICULTY_VALUE_1}, 	// level 03
		{DIFFICULTY_TYPE_EMPTY, 	DIFFICULTY_VALUE_NULL}, // level 04
		{DIFFICULTY_TYPE_ADD_PUYOS, 	DIFFICULTY_VALUE_3}, 	// level 05
		{DIFFICULTY_TYPE_EMPTY, 	DIFFICULTY_VALUE_NULL}, // level 06
		{DIFFICULTY_TYPE_SHIFT_UP, 	DIFFICULTY_VALUE_2}, 	// level 07

		{DIFFICULTY_TYPE_ADD_PUYOS, 	DIFFICULTY_VALUE_3}, 	// level 08
		{DIFFICULTY_TYPE_EMPTY, 	DIFFICULTY_VALUE_NULL}, // level 09
		{DIFFICULTY_TYPE_SHIFT_UP, 	DIFFICULTY_VALUE_1}, 	// level 10
		{DIFFICULTY_TYPE_SHIFT_UP, 	DIFFICULTY_VALUE_1}, 	// level 11
		{DIFFICULTY_TYPE_SHIFT_UP, 	DIFFICULTY_VALUE_1}, 	// level 12
		{DIFFICULTY_TYPE_EMPTY, 	DIFFICULTY_VALUE_NULL}, // level 13
		{DIFFICULTY_TYPE_ADD_PUYOS, 	DIFFICULTY_VALUE_3}, 	// level 14
		{DIFFICULTY_TYPE_EMPTY, 	DIFFICULTY_VALUE_NULL}, // level 15

		{DIFFICULTY_TYPE_SHIFT_UP, 	DIFFICULTY_VALUE_3}, 	// level 16
		{DIFFICULTY_TYPE_EMPTY, 	DIFFICULTY_VALUE_NULL}, // level 17
		{DIFFICULTY_TYPE_ADD_PUYOS, 	DIFFICULTY_VALUE_4},	// level 18
		{DIFFICULTY_TYPE_SHIFT_UP, 	DIFFICULTY_VALUE_1},	// level 19
		{DIFFICULTY_TYPE_ADD_PUYOS, 	DIFFICULTY_VALUE_5},	// level 20
		{DIFFICULTY_TYPE_EMPTY, 	DIFFICULTY_VALUE_NULL}, // level 21
		{DIFFICULTY_TYPE_SHIFT_UP, 	DIFFICULTY_VALUE_4},	// level 22
		{DIFFICULTY_TYPE_ADD_PUYOS, 	DIFFICULTY_VALUE_6},	// level 23
		{DIFFICULTY_TYPE_EMPTY, 	DIFFICULTY_VALUE_NULL}  // level 24
	};


	static /*synchronized*/ void setGameState(int gamestate)
	{
		switch(gs = gamestate)
		{
			case STARTED:
			{
				// init the ressources
				PuyoRessources.init();
				PuyoGameScreen.start();
				PuyoGameControls.start();
				break;
			}
			case TITLE:
			{
				// play the title animation effect
				PuyoGfx.drawTitleAnimation();
				break;
			}
			case INTRO:
			{
				// play the intro animation effect
				PuyoGfx.drawIntroAnimation();
				break;
			}

			case FINISHED:
			{
				// Stops Threads, free the ressources (and exit)
				PuyoGameScreen.stop();
				PuyoGameControls.stop();
				PuyoLevel.stop();
				PuyoScore.stop();
				PuyoTime.stop();

				try {
					Thread.sleep(400);
				} catch(Exception e){}
				
				PuyoRessources.free();
				System.exit(0);
				break;
			}

			// ----- level -----
			case LEVEL_STARTED:
			{
				System.out.print("level start :");
				System.out.println(level);

				if (level == 0)
				{
					PuyoTimer.start();
					PuyoTime.start();
					PuyoScore.start();
					PuyoLevel.start();
				}

				// start a new level
				PuyoGfx.drawDifficultyAnimation();
				do {
					PuyoGameScreen.drawUpdatePuyoTable();
					PuyoRessources.updateHeightMap();
				} while (PuyoGfx.drawExplosionAnimation());
				break;
			}
			case LEVEL_RUN:
			{
				// run the current level
				mainLoop();
				//PuyoGameScreen.drawUpdatePuyoTable();
				//PuyoGfx.drawExplosionAnimation();
				//PuyoGameScreen.drawPuyoTable();
				break;
			}
			case LEVEL_FINISHED:
			{
				// finish the current level
				// next level ???
				//nextLevel();
				if (level == LEVEL_COUNT - 1)
				{
					PuyoTime.stop();
					PuyoScore.stop();
					PuyoLevel.stop();
					System.out.println("stop !!!!");
				}
				System.out.print(" finshed :");
				System.out.println(level);
				break;
			}

			// ----- end ---------
			case GAME_OVER:
			{

				PuyoTime.stop();
				PuyoScore.stop();
				PuyoLevel.stop();
				level = -1;
				PuyoGfx.drawGameOverAnimation();
				break;
			}

			// ------ not used ------
			case CREDIT:
			{
				break;
			}
			case MAIN_MENU:
			{
				break;
			}
		}
	}

	static synchronized int getGameState()
	{
		return gs;
	}

	static synchronized int nextGameState()
	{ 
		int state = getGameState();
		switch(state)
		{
			case STARTED:
			{
				setGameState(TITLE);
				break;
			}
			case TITLE:
			{
				setGameState(INTRO);
				break;
			}
			case INTRO:
			{
				setGameState(MAIN_MENU);
				break;
			}
			case MAIN_MENU:
			{
				resetLevel();
				resetScore();
				resetDifficulty();
				resetTime();
				PuyoRessources.setPuyoArrayAndHeightMap();
				setGameState(LEVEL_STARTED);
				PuyoGfx.DrawNextPuyos(next1, next2);
				break;
			}
			case FINISHED:
			{
				// ??? exit
				System.exit(0);
				break;
			}
			case LEVEL_STARTED:
			{
				if (level < LEVEL_COUNT)
				{
					setGameState(LEVEL_RUN);
				}
				else
				{
					setGameState(GAME_OVER);
				}
				break;
			}
			case LEVEL_RUN:
			{
				setGameState(LEVEL_FINISHED);
				break;
			}
			case LEVEL_FINISHED:
			{
				setGameState(LEVEL_STARTED);
				break;
			}
			case GAME_OVER:
			{
				if (PuyoGameControls.getPressure() > 0)
				{
					setGameState(TITLE);
				}
				break;
			}
			case CREDIT:
			{
				setGameState(TITLE);
				break;
			}
		}
		return gs;
	}

	static int getLevel()
	{
		return level;
	}

	static int getScore()
	{
		return score;
	}


	static void incScore(int nbPoints)
	{
			score += nbPoints;
	}


	protected static void nextLevel()
	{
		level ++;

		if (level < LEVEL_COUNT)
		{
			difficultyType = difficultyLevelArray[level][0];
			difficultyValue = difficultyLevelArray[level][1];
		}
		else
		{
			resetDifficulty();
		}
		//setGameState(LEVEL_STARTED);
	}

	static int getDifficultyType()
	{
		return difficultyType;
	}

	static int getDifficultyValue()
	{
		return difficultyValue;
	}

	static synchronized void resetDifficulty()
	{
		difficultyType = DIFFICULTY_TYPE_EMPTY;
		difficultyValue = DIFFICULTY_VALUE_NULL;
	}

	static void resetScore()
	{
		score = 0;
		PuyoScore.stop();
	}

	static void resetLevel()
	{
		level = 0;
		PuyoLevel.stop();
	}

	private static long time;

	static void resetTime()
	{
		time = System.currentTimeMillis();
		PuyoTime.stop();
	}


static void switchPuyos()
{
	System.out.println("switchPuyos!!!");

	Puyo tmp1 = p1;
	Puyo tmp2 = p2;

	p1 = next1;
	p2 = next2;

	next1 = tmp1;
	next2 = tmp2;


}

static Puyo p1 = new Puyo();
static Puyo p2 = new Puyo(p1);
static Puyo next1 = new Puyo();
static Puyo next2 = new Puyo(next1);


	private static void mainLoop()
	{
		int currentLevel;
		long MOVE_DOWN_EVERY_X_MS = 500 + (1500 *  (LEVEL_COUNT - level))/LEVEL_COUNT;

		long moveDownTime = MOVE_DOWN_EVERY_X_MS;
		long lastTime = System.currentTimeMillis();
		long elapsedTime = 0;

		
				

		PuyoGfx.drawExplosionAnimation();
		PuyoGameScreen.drawUpdatePuyoTable();
		PuyoRessources.updateHeightMap();


		currentLevel = getLevel();
		while (currentLevel == getLevel())
		{
			boolean end = false;
			
			PuyoGfx.drawUpdateNextPuyos(p1, p2,next1,next2);
			PuyoGameControls.reset();
			PuyoRessources.updateHeightMap();


			while (!end)
			{
				if (PuyoGameControls.isUpKey())
				{
					PuyoAction.rotatePuyos(p1,p2);
					PuyoGameControls.reset();
					//PuyoTimer.waitTime(50);
				}
				if (PuyoGameControls.isLeftKey())
				{
					PuyoAction.moveLeftPuyos(p1,p2);
				}
				if (PuyoGameControls.isRightKey())
				{
					PuyoAction.moveRightPuyos(p1,p2);
				}



				if (PuyoGameControls.isDownKey() || moveDownTime < 0)
				{
					PuyoAction.moveDownPuyos(p1,p2);
					moveDownTime = MOVE_DOWN_EVERY_X_MS;
					//PuyoGameControls.reset();
					//PuyoTimer.waitTime(50);
				}


				p1.anime();
				p2.anime();
				
				p1.draw();
				p2.draw();

				elapsedTime = System.currentTimeMillis() - lastTime;
				lastTime = System.currentTimeMillis();
				if (elapsedTime > 500) elapsedTime  = 0;

				moveDownTime -= elapsedTime;
				p1.updatePosition();
				p2.updatePosition();

				if (moveDownTime < 0 && (p1.isTouched() || p2.isTouched()))
				{
					end = true;
					p1.writeOnPuyoTable();
					p2.writeOnPuyoTable();
					p1.reset(false);
					p2.reset(true);

				}
				
				if (getGameState() == FINISHED)
					end = true;
			}

			PuyoGfx.drawExplosionAnimation();
			PuyoGameScreen.drawUpdatePuyoTable();
			PuyoRessources.updateHeightMap();

			do {
				PuyoGameScreen.drawUpdatePuyoTable();
				PuyoRessources.updateHeightMap();
			} while (PuyoGfx.drawExplosionAnimation());
			if (PuyoRessources.isFilledPuyoArray())
			{
				setGameState(GAME_OVER);
			}



		}
		//PuyoGfx.printTable();

	}

}

