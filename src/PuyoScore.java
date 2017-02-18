/***************************************************************************
                      PuyoScore.java -  description
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


public class PuyoScore implements Runnable
{
	private static boolean IS_DEBUG = PuyoRessources.IS_DEBUG;

	private static int newScore;
	private static Thread currentThread;
	

	private PuyoScore()
	{}

	public static synchronized void start()
	{		
		currentThread = new Thread(new PuyoScore());
		currentThread.start();
	}

	public static boolean isStopped()
	{
		return currentThread == null;
	}
	
	public static synchronized void stop()
	{
		currentThread = null;
	}
	
	public void run()
	{
		Thread thisThread = Thread.currentThread();
		int currentScore;
		
		if (IS_DEBUG)
		{
			System.out.println("-- Score start !!! ---");
		}
		
		newScore = 0;
		while (thisThread == currentThread)
		{
			currentScore = PuyoGameState.getScore();
			if (newScore > currentScore)
			{
				PuyoGfx.drawUpdateScoreAnimation();
				currentScore = PuyoGameState.getScore();
					
				while (newScore > currentScore && thisThread == currentThread)
				{					
					PuyoGfx.drawScore();
					PuyoTimer.waitTime(PuyoRessources.UPDATE_SCORE_STEP_DURATION);
					PuyoGameState.incScore(1);
					currentScore = PuyoGameState.getScore();
				}
					
			}
			else
			{	
				PuyoGfx.drawScore();
				PuyoTimer.waitTime(200);
			}			
		}
		if (IS_DEBUG)
		{
			System.out.println("-- Score stop !!! ---");
		}

	}

	static synchronized void updateScore(int count)
	{
		newScore += count;
		if (IS_DEBUG)
		{
			System.out.println("updateScore(count))");
			System.out.print("newScore =");
			System.out.println(newScore);
			System.out.print("currentScore =");
			System.out.println(PuyoGameState.getScore());
		}		
	}
}
