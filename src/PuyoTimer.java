/***************************************************************************
                         PuyoTimer.java -  description
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


public class PuyoTimer
{
	protected static long startTime = System.currentTimeMillis();
	protected static long lastTime = 0;

	static void start()
	{
		startTime = System.currentTimeMillis();
		lastTime = 0;
	}

	static long getDelta()
	{
		long elapsedTime;
		long currentTime;

		currentTime = getTime();
		elapsedTime =  currentTime - lastTime;
		lastTime = currentTime ;

		return elapsedTime;
	}

	static long getTime()
	{
		return System.currentTimeMillis() - startTime;
	}

	static void waitTime(long ms)
	{
		long currentTime;
		long endTime;

		currentTime = getTime();
		endTime = currentTime + ms;
		try
		{
			Thread.sleep(ms);
		} catch(Exception ex)
		{
			/*while (getTime() <endTime)
			{
				Thread.yield();
			}*/
		}
	}
	
	static int getCurrentFrame()
	{
		int t = (int) (System.currentTimeMillis()>>6);
		return t & 0x03;
	}
}
