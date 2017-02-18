/***************************************************************************
                     PuyoListener.java -  description
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


import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

class PuyoListener implements KeyListener, WindowListener
{
	/**
	 * Invoked when a key has been pressed.
	 */
	public void keyPressed(KeyEvent e)
	{
	        int keyCode = e.getKeyCode();
		PuyoGameControls.setKeyPressed(keyCode);

  		//System.out.println("keyPressed");
		PuyoGameControls.print();
	}



	/**
	 * Invoked when a key has been released.
	 */
	public void keyReleased(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		PuyoGameControls.setKeyReleased(keyCode);

		//System.out.println("keyReleased");
				PuyoGameControls.print();
	}


	/**
	 * Invoked when a key has been typed.
	 */
	public void keyTyped(KeyEvent e) {}


	/**
	 * Invoked when a window has been closed as the result of calling dispose on the window.
	 */
	public void windowClosed(WindowEvent e)
       	{
		PuyoGameState.setGameState(PuyoGameState.FINISHED);
		e.getWindow().dispose();
	}


	/**
	 * Invoked when the Window is set to be the active Window.
	 */
	public void windowActivated(WindowEvent e) {}


	/**
	 * Invoked when a Window is no longer the active Window.
	 */
	public void windowDeactivated(WindowEvent e) {}


	/**
	 * Invoked when a window is changed from a normal to a minimized state.
	 */
	public void windowDeiconified(WindowEvent e) {}


	/**
	 * Invoked the first time a window is made visible.
	 */
	public void windowOpened(WindowEvent e){}


	/**
	 * Invoked when a window is changed from a normal to a minimized state.
	 */
	public void windowIconified(WindowEvent e){}


	/**
	 *  Invoked when the user attempts to close the window from the window's system menu.
	 */
	public void windowClosing(WindowEvent e) {}


}
