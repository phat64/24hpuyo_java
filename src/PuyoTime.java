/***************************************************************************
                       PuyoTime.java -  description
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


public class PuyoTime implements Runnable//extends Thread
{
	private static boolean IS_DEBUG = PuyoRessources.IS_DEBUG;
	private static long startTime;
	private static long currentTime;
	private static Thread currentThread;
	private final int SECONDS = 1000;
	private	final int MINUTES = 60 * SECONDS;	
	//private final int HOURS = 60 * MINUTES;
	private final int HOURS_MAX = 24;

	private PuyoTime()
	{}

	public static synchronized void start()
	{
		System.out.println("PuyoTime.start()");
		currentThread = new Thread(new PuyoTime());
		currentThread.start();
		setZero();
	}

	public static synchronized void stop()
	{
		currentThread = null;
	}
	
	
	public void run()
	{
 		Thread thisThread = Thread.currentThread();
		System.out.println("-- Time start ! --");
		while (thisThread == currentThread && getHours() < HOURS_MAX)
		{
			if (getHours() > PuyoGameState.getLevel())
			{
				PuyoLevel.updateLevel(getHours());
			}
			else
			{
				updateTime();
				PuyoGfx.drawTime();
				PuyoTimer.waitTime(125);
			}
		}
	}

	private static void setZero()
	{
		startTime = System.currentTimeMillis();
		currentTime = 0;
	}

	private void updateTime()
	{
		currentTime = System.currentTimeMillis() - startTime;
	}
	
	private long getTime()
	{
		return currentTime;
	}

	private int getHours()
	{
		return (int) (currentTime + 2500) / MINUTES;
	}
}
