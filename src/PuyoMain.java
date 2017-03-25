/***************************************************************************
                       PuyoMain.java -  description
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

import java.awt.*;
import java.awt.Frame;
import java.awt.Canvas;
import java.awt.event.KeyListener;

public class PuyoMain // implements WindowAdapter
{
	public static void main(String [] args)
	{
		PuyoGameState.setGameState(PuyoGameState.STARTED);
		
		while (PuyoGameState.getGameState() >= 0)
		{
			PuyoGameState.nextGameState();
		}
	}
}

