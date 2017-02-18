/***************************************************************************
                         PuyoFont.java -  description
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
//import java.awt.Color;
import java.awt.Graphics;


public class PuyoFont implements java.lang.Cloneable
{
	protected Image fontImage;
	protected int fontW;
	protected int fontH;
	protected int backgroundColor = 0;
	
	protected Graphics g;
	protected int x;
	protected int y;
	
	PuyoFont(Image fontImage, int fontW, int fontH)
	{
		this.fontImage = fontImage;	
		this.fontW = fontW;
		this.fontH = fontH;
	}	

	void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	void setGraphics(Graphics g)
	{
		
		this.g = g;//g.create();
	}

	synchronized void drawDigit(int digit)
	{
		g.setClip(x,y,fontW, fontH);
		//g.setColor(backgroundColor);

		if (digit>=0 && digit<=9)
		{
			g.drawImage(fontImage, x - digit*fontW, y,null);	
		} else {
			//g.setColor(Color.Black);
			PuyoGameScreen.setBackgroundColor();
			g.fillRect(x,y, fontW, fontH);
		}

		x += fontW;
	}

	void draw4Digits(int integer)
	{
		int digit;

		if (integer>1000) integer = 9999;
		if (integer<0)    integer = 0;

		digit = integer / 1000;		
		integer -= digit * 1000;
		drawDigit(digit);

		digit = integer / 100;		
		integer -= digit * 100;
		drawDigit(digit);

		digit = integer / 10;		
		integer -= digit * 10;
		drawDigit(digit);

		digit = integer;		
		drawDigit(digit);
	}

	void draw2Digits(int integer)
	{
		int digit;

		if (integer>=100) integer = 99;
		if (integer<0)    integer = 0;

		digit = integer / 10;		
		integer -= digit * 10;
		drawDigit(digit);

		digit = integer;
		drawDigit(digit);
	}

	public Object clone()
	{
		PuyoFont newFont;

	       	newFont= new PuyoFont(fontImage, fontW, fontH);
		newFont.setGraphics(g);

		return (Object) newFont;
	}

	protected void finalize() throws Throwable
	{
		fontImage = null;
		g = null;
		super.finalize();
	}

}

