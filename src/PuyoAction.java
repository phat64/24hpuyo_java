/***************************************************************************
                       PuyoAction.java -  description
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

public class PuyoAction
{
	protected static final int PUYO_RADIUS = PuyoRessources.PUYO_RADIUS;
	protected static final int PUYO_WIDTH = PuyoRessources.PUYO_WIDTH;
	protected static final int PUYO_HEIGHT = PuyoRessources.PUYO_HEIGHT;
	protected static final int PUYO_TABLE_WIDTH = PuyoRessources.PUYO_TABLE_WIDTH;

	static void rotatePuyos(Puyo p1, Puyo p2)
	{
		final int Z =  2 * PUYO_RADIUS;
		int x1;
		int y1;
		int x2;
		int y2;

		p1.clear();
		p2.clear();


		synchronized (p1)
		{
			synchronized (p2)
			{
				// save puyos position
				p1.pushPosition();
				p2.pushPosition();
				x1 = p1.getCenterX();
				y1 = p1.getCenterY();
				x2 = p2.getCenterX();
				y2 = p2.getCenterY();

			}
		}

		if (x1 == x2)
		{
			if (y1 < y2)
			{
				p1.setPosition(x1, y1 +2*PUYO_RADIUS);
				p2.setPosition(x2+2*PUYO_RADIUS, y2);
			}else {
				p1.setPosition(x1,y1);
				p2.setPosition(x2 - 2*PUYO_RADIUS,y1);
			}

		}
	       	else if (y1 == y2)
		{
			if (x1 < x2)
			{
				p1.setPosition(x2,y1);
				p2.setPosition(x2,y1 - 2 * PUYO_RADIUS);
			}else {
				p1.setPosition(x2,y1 - 2 * PUYO_RADIUS);
				p2.setPosition(x2,y2);
			}
		}
		else
		{
			System.out.println("errrrrrr!!!!!!!!!!!!!!");
			System.err.println("errrrrrr!!!!!!!!!!!!!!");
			System.out.println(" pos 1 = "+x1+" "+y1);
			System.out.println(" pos 2 = "+x2+" "+y2);
			System.exit(-1);
		}

		if (!isValidRotation(p1, p2))
		{
			// load puyos position
			p1.popPosition();
			p2.popPosition();
			System.out.println("position not valid");
		}

		p1.draw();
		p2.draw();


	}

	protected static boolean isValidRotation(Puyo p1, Puyo p2)
	{
		int [] puyoHeightMap;

		puyoHeightMap = PuyoRessources.getHeightMap();

		if (isVerticalPosition(p1, p2))
		{
			Puyo down;
			Puyo up;
			int height;

			down = getDownPuyo(p1, p2);

			up = getUpPuyo(p1, p2);


			Puyo left;
			Puyo right;

			left = getLeftPuyo(p1, p2);
			right = getRightPuyo(p1, p2);


			if (left.getCenterX() - PUYO_RADIUS < 0)
			{
			       return false;
			}
			if (right.getCenterX() + PUYO_RADIUS > PUYO_TABLE_WIDTH)
			{
				return false;
			}

			if (up.getCenterY() - PUYO_RADIUS < 0)
			{
				return false;
			}
			

/*


			if (down.getCenterX() - PUYO_RADIUS < 0)
			{
				return false;
			}
			if (down.getCenterX() + PUYO_RADIUS >= PUYO_TABLE_WIDTH)
			{
				return false;
			}
*/
			
			height = puyoHeightMap[down.getHeightMapIndex()];

			return down.getCenterY() + PUYO_RADIUS <= height;
		}
		else if(isHorizontalPosition(p1, p2))
		{
			Puyo left;
			Puyo right;
			int height;
			int heightMapIndexLeft;
			int heightMapIndexRight;

			left = getLeftPuyo(p1, p2);
			right = getRightPuyo(p1, p2);


			if (left.getCenterX() - PUYO_RADIUS < 0)
			{
			       return false;
			}
			if (right.getCenterX() + PUYO_RADIUS > PUYO_TABLE_WIDTH)
			{
				return false;
			}


			heightMapIndexLeft = left.getHeightMapIndex();
		       	heightMapIndexRight = right.getHeightMapIndex();
			height = Math.min(puyoHeightMap[heightMapIndexLeft], puyoHeightMap[heightMapIndexRight]);

			boolean ret =left.getCenterY() + PUYO_RADIUS <= height && right.getCenterY() + PUYO_RADIUS <= height;
			return ret;
		}
		else
		{
			System.err.println("Error PuyoAction.isValidRotation(p1,p2)");
			System.out.println(p1);
			System.out.println(p2);
			System.exit(-1);
		}
		return false;
	}

	static boolean movePuyos (Puyo p1, Puyo p2, int dx, int dy)
	{
		long start;
		long currentTime;
		int transX;
		int transY;


		synchronized (p1)
		{
			synchronized (p2)
			{
				p1.pushPosition();
				p2.pushPosition();
				p1.move(dx, dy);
				p2.move(dx, dy);
				if (!isValidRotation(p1, p2))
				{
					p1.popPosition();
					p2.popPosition();
					return false;
				}
				p1.popPosition();
				p2.popPosition();
//				p1.move(dx, dy);
//				p2.move(dx, dy);





				transX = 0;
				transY = 0;
				start = System.currentTimeMillis();
				int invPressure = 256 - PuyoGameControls.getPressure();
				final int PUYO_MOVE_TIME = 80 + (120 * invPressure)/256;

				while ((currentTime = (System.currentTimeMillis() - start)) < PUYO_MOVE_TIME)
				{
   					p1.move(-transX, -transY);
					p2.move(-transX, -transY);

					p1.clear();
					p2.clear();


					transX = ((dx * (int)currentTime) / PUYO_MOVE_TIME) /*- transX*/;
					transY = ((dy * (int)currentTime) / PUYO_MOVE_TIME) /*- transY*/;

					p1.move(transX, transY);
					p2.move(transX, transY);


					//p1.anime();
					//p2.anime();

					//p1.clear();
					//p2.clear();


					p1.draw();
					p2.draw();
					PuyoGameScreen.waitForVsync();
				}

				p1.move(-transX, -transY);
				p2.move(-transX, -transY);
				p1.clear();
				p2.clear();



				p1.move(dx, dy);
				p2.move(dx, dy);
				p1.clear();
				p2.clear();




				p1.anime();
				p2.anime();
//				p1.clear();
//				p2.clear();


				p1.draw();
				p2.draw();
			}
		}
		return true;
	}

	static boolean moveLeftPuyos(Puyo p1, Puyo p2)
	{
		return movePuyos(p1, p2, -PUYO_WIDTH, 0);
	}

	static boolean moveRightPuyos(Puyo p1, Puyo p2)
	{
		return movePuyos(p1, p2, PUYO_WIDTH, 0);
	}

	static boolean moveDownLeftPuyos(Puyo p1, Puyo p2)
	{
		return movePuyos(p1, p2, -PUYO_WIDTH, PUYO_HEIGHT);
	}

	static boolean moveDownRightPuyos(Puyo p1, Puyo p2)
	{
		return movePuyos(p1, p2, PUYO_WIDTH, PUYO_HEIGHT);
	}

	static boolean moveDownPuyos(Puyo p1, Puyo p2)
	{
		return movePuyos(p1, p2, 0, PUYO_HEIGHT);
	}



	private static final boolean isVerticalPosition(Puyo p1, Puyo p2)
	{
		return p1.getCenterX() == p2.getCenterX();
	}

	private static final boolean isHorizontalPosition(Puyo p1, Puyo p2)
	{
		return p1.getCenterY() == p2.getCenterY();
	}

	private static final Puyo getLeftPuyo(Puyo p1, Puyo p2)
	{
		return p1.getCenterX() < p2.getCenterX()? p1: p2;
	}

	private static final Puyo getRightPuyo(Puyo p1, Puyo p2)
	{
		return p1.getCenterX() > p2.getCenterX()? p1: p2;
	}

	private static final Puyo getUpPuyo(Puyo p1, Puyo p2)
	{
		return p1.getCenterY() < p2.getCenterY()? p1: p2;
	}

	private static final Puyo getDownPuyo(Puyo p1, Puyo p2)
	{
		return p1.getCenterY() > p2.getCenterY()? p1: p2;
	}

	static boolean checkPuyos(Puyo p1, Puyo p2)
	{
		int distanceX;
		int distanceY;

		/*distanceX2 = (p1.getCenterX() - p2.getCenterX());
		distanceX2 *= distanceX2;

		distanceY2 = (p1.getCenterY() - p2.getCenterY());
		distanceY2 *= distanceY2;

		distance = (int) (0.5 + Math.sprt(distanceX2 + distanceY2));*/

		distanceX = (p1.getCenterX() - p2.getCenterX());
		if (distanceX < 0)
		{
			distanceX = - distanceX;
		}

		distanceY = (p1.getCenterY() - p2.getCenterY());
		if (distanceY < 0)
		{
			distanceY = - distanceY;
		}


		return (p1.getCenterX() == p2.getCenterX() || p1.getCenterY() == p2.getCenterY())
			&& (distanceX == 2 * PUYO_RADIUS && distanceY == 0 || distanceX == 0 && distanceY == 2 * PUYO_RADIUS);
	}

	static boolean moveDownPuyos3212(Puyo p1, Puyo p2)
	{
		int puyoIndexX;
		int [] heightMap;

		synchronized (p1)
		{
			synchronized (p2)
			{
				p1.move(0, PUYO_HEIGHT );
				p2.move(0, PUYO_HEIGHT );
			}
		}

/*		heightMap = PuyoRessources.getHeightMap();
		if (isVerticalPosition(p1,p2))
		{
			Puyo down;

			down = getDownPuyo(p1, p2);

			puyoIndexX = down.getHeightMapIndex();

			if (down.getCenterY() + PUYO_RADIUS > heightMap[puyoIndexX])
			{
				int transY;

				synchronized (p1)
				{
					synchronized (p2)
					{
						transY = down.getCenterY() + PUYO_RADIUS - heightMap[puyoIndexX];
						p1.move(0,  - transY);
						p2.move(0,  - transY);
					}
				}
				return false;
			}
		}
		else if (isHorizontalPosition(p1,p2))
		{
			Puyo left;
			Puyo right;
			int puyoIndexLeft;
			int puyoIndexRight;
			int height;
			int centerY;

			left = getLeftPuyo(p1, p2);
			right = getRightPuyo(p1, p2);

			puyoIndexLeft = left.getHeightMapIndex();
			puyoIndexRight = right.getHeightMapIndex();

			if (heightMap[puyoIndexLeft] < heightMap[puyoIndexRight])
			{
				height = heightMap[puyoIndexLeft];
				centerY = left.getCenterY();
			}
			else
			{
				height = heightMap[puyoIndexRight];
				centerY = right.getCenterY();
			}

			if (centerY + PUYO_RADIUS > height)
			{
				int transY;

				synchronized (p1)
				{
					synchronized (p2)
					{
						transY = centerY + PUYO_RADIUS - height;
						p1.move(0,  - transY);
						p2.move(0,  - transY);
					}
				}
				return false;
			}
		}
		else
		{System.err.println("Error PuyoAction.moveDownPuyos()");System.exit(-1);}*/
		return true;

	}

}


