/***************************************************************************
                 PuyoGameControls.java -  description
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


public class PuyoGameControls implements Runnable
{
	protected static int previousKeyFrame = 0;
	protected static int currentKeyFrame = 0;
	protected static int nextKeyFrame = 0;

	private static int pressure = 0;

	private final static int KEY_LEFT_MASK = 1<<0;
	private final static int KEY_RIGHT_MASK = 1<<1;
	private final static int KEY_UP_MASK = 1<<2;
	private final static int KEY_DOWN_MASK = 1<<3;
	private final static int KEY_EXIT_MASK = 1<<7;

	private static final boolean IS_DEBUG = true;

	private static Thread currentThread;

	private PuyoGameControls()
	{}
	
	public static synchronized void start()
	{
		currentThread = new Thread(new PuyoGameControls());
		currentThread.setName("PuyoGameControls");
		currentThread.start();
	}

	public static synchronized void stop()
	{
		currentThread = null;
	}

	public void run()
	{
		Thread thisThread = Thread.currentThread();
		PuyoGameControls.reset();
		
		if (IS_DEBUG)
		{
			System.out.println("-- Controls start !!!--");
		}
		
		while (thisThread == currentThread)
		{
			PuyoGameControls.nextFrame();
			PuyoTimer.waitTime(200);
		}
		
		PuyoGameControls.reset();
		if (IS_DEBUG)
		{
			System.out.println("-- Controls end !!!--");
		}
		
	}


	private static int getKeyMask(int keyCode)
	{
		int keyMask;

		keyMask = 0;

		switch(keyCode)
		{
			case PuyoRessources.KEY_LEFT:
			{
				keyMask = KEY_LEFT_MASK;
				break;
			}
			case PuyoRessources.KEY_RIGHT:
			{
				keyMask = KEY_RIGHT_MASK;
				break;
			}
			case PuyoRessources.KEY_DOWN:
			{
				keyMask = KEY_DOWN_MASK;
				break;
			}
			case PuyoRessources.KEY_UP:	
			{
				keyMask = KEY_UP_MASK;
				break;
			}
			case PuyoRessources.KEY_EXIT:
			{
				keyMask = KEY_EXIT_MASK;
				break;
			}
		}
		return keyMask;
	}

	static void setKeyPressed(int keyCode)
	{
		nextKeyFrame |= getKeyMask(keyCode);
	}

	static void setKeyReleased(int keyCode)
	{
		nextKeyFrame &= ~getKeyMask(keyCode);
	}

	static void nextFrame()
	{
		if (previousKeyFrame == currentKeyFrame
			&&	currentKeyFrame == nextKeyFrame)
		{
			pressure *= 2; 
			if (pressure > 256) pressure = 256;
		}
		else if (currentKeyFrame == nextKeyFrame)
		{
			pressure = 8; 
		}
		else	pressure = 0; 
	


		previousKeyFrame = currentKeyFrame;
		currentKeyFrame = nextKeyFrame;
		//nextKeyFrame = 0;
	}	
	
	static void reset()
	{
		previousKeyFrame = 0;
		currentKeyFrame = 0;
		nextKeyFrame = 0;
		pressure = 0;
	}

	static boolean isUpKey()
	{
		return isKeyPressed(KEY_UP_MASK);
	}

	static boolean isDownKey()
	{
		return isKeyPressed(KEY_DOWN_MASK);
	}

	static boolean isLeftKey()
	{
		return isKeyPressed(KEY_LEFT_MASK);
	}

	static boolean isRightKey()
	{
		return isKeyPressed(KEY_RIGHT_MASK);
	}

	static boolean isExitKey()
	{
		return 	isKeyPressed(KEY_EXIT_MASK);
	}

	protected static final boolean isKeyPressed(int keyMask)
	{
		return (keyMask&currentKeyFrame)!=0;
	}

	static int getPressure()
	{
		return pressure;// 0 - 255
	}
	static void print()
	{
		/*if (isRightKey())
			System.out.print("-> ");
		
		if (isLeftKey())
			System.out.print("<- ");

		if (isUpKey())
			System.out.print("! ");
		
		if (isDownKey())
			System.out.print("v ");*/
	}
}
