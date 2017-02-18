/***************************************************************************
                         PuyoLevel.java -  description
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

public class PuyoLevel implements Runnable//extends Thread
{
	private static boolean IS_DEBUG = PuyoRessources.IS_DEBUG;
	private static Thread currentThread;

	public static synchronized void start()
	{
		currentThread = new Thread(new PuyoLevel());
		currentThread.start();
	}
	
	public static synchronized void stop()
	{
		currentThread = null;
	}

	public static boolean isStopped()
	{
		return currentThread == null;
	}
		
	public void run()
	{
		Thread thisThread = Thread.currentThread();
		int currentLevel;
		
		if (IS_DEBUG)
		{
			System.out.println("-- Level start !!! --");
		}
		
		currentLevel = PuyoGameState.getLevel();
		while (thisThread == currentThread)
		{
			if (currentLevel != PuyoGameState.getLevel())
			{
				PuyoGfx.drawUpdateLevelAnimation();
				currentLevel =  PuyoGameState.getLevel();
			}
			else
			{
				PuyoGfx.drawLevel();
				PuyoTimer.waitTime(500);
			}			
		}
	}

	static synchronized void updateLevel(int level)
	{
		while (level > PuyoGameState.getLevel())
		{
			PuyoGameState.nextLevel();
		}
	}
}
