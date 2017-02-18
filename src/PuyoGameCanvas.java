/***************************************************************************
                   PuyoGameCanvas.java -  description
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


import java.awt.Frame;
import java.awt.Color;
import java.awt.Canvas;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Dimension;

import java.awt.event.KeyListener;
import java.awt.event.WindowListener;



public class PuyoGameCanvas extends Canvas
{
	protected static int fps = 0;
	protected static long start = 0;
	protected static int nbFrames = 0;

	protected static final Dimension preferredSize = 
		new Dimension(PuyoRessources.SCREEN_WIDTH, PuyoRessources.SCREEN_HEIGHT);

	private static final Frame f = new Frame("24h Puyo");
	private static final KeyListener keyListener = new PuyoListener();
	private static final WindowListener windowListener = new PuyoListener();
	/*private */ static final Canvas canvas = new PuyoGameCanvas();


	
	private PuyoGameCanvas()
	{	
		super();
		setSize(PuyoRessources.SCREEN_WIDTH,
			PuyoRessources.SCREEN_HEIGHT);
		setFrame();
	}

	private void setFrame()
	{
		f.setBackground(Color.black);
		f.addWindowListener(windowListener);
		this.addKeyListener(keyListener);
		f.add(this);
		f.pack();
		f.setResizable(false);
		f.show();
	}

	public void update(Graphics g)
	{
		if (start== 0) start = System.currentTimeMillis();

		paint(g);	
	}

	public static void refresh()
	{
		if (canvas != null)
		{
			canvas.repaint();
		}
	}

	public void paint(Graphics g)
	{	
		g.setClip(0,0,PuyoRessources.SCREEN_WIDTH, PuyoRessources.SCREEN_HEIGHT);
		g.drawImage(PuyoRessources.getScreenImage(), 0,0,null);
		if (PuyoRessources.IS_DEBUG)
		{
			nextFrame();
			showFps();
		}
	}

	private static void showFps()
	{
		PuyoFont fpsFont; 
		long time;
		
		fpsFont =  PuyoRessources.getFpsFont();
		if (fpsFont != null)//synchronized (fpsFont)
		{
			fpsFont.setPosition(0, 0);
			fpsFont.draw4Digits(fps);	
		}
	
		time = System.currentTimeMillis() - start;
		if (time > 1000)
		{
			fps = (int) ((nbFrames*1000)/time);
			start = System.currentTimeMillis();
			nbFrames = 0;			
		}
	}
	
	private static void nextFrame()
	{
		nbFrames ++;
	}

	public Dimension getPreferredSize()
	{	
		return preferredSize;
	}

	protected void finalize() throws Throwable
	{
		super.finalize();
		f.hide();
		f.dispose();
	}
}

