

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;

public class Tile /*extends MovingImage*/ {

	public static final int MARIO_WIDTH = 90;
	public static final int MARIO_HEIGHT = 90;

	private boolean player;
	private double xVelocity, yVelocity;
	private boolean onASurface;
	private double friction;
	private double gravity;
	private double jumpStrength;

	/**
	 * Creates a new Tile object.
	 * @param player The player that this Tile object belongs to (whether it is a red or black tile).
	 */
	public Tile(boolean player) {
		//super("mario.png", x, y, MARIO_WIDTH, MARIO_HEIGHT);
		/*xVelocity = 0;
		yVelocity = 0;
		onASurface = false;
		gravity = 0.7;
		friction = .85;
		jumpStrength = 15;*/
		this.player = player;
	}

	// METHODS
	/*public void walk(int dir) {
		if (xVelocity <= 10 && xVelocity >= -10)
			xVelocity += dir;
	}

	public void jump() {
		if (onASurface)
			yVelocity -= jumpStrength;
	}

	public void act(Tile[][] tiles) {
		double xCoord = getX();
		double yCoord = getY();
		double width = getWidth();
		double height = getHeight();

		// ***********Y AXIS***********

		yVelocity += gravity; // GRAVITY
		double yCoord2 = yCoord + yVelocity;

		Rectangle2D.Double strechY = new Rectangle2D.Double(xCoord,Math.min(yCoord,yCoord2),width,height+Math.abs(yVelocity));

		onASurface = false;

		if (yVelocity > 0) {
			Shape standingSurface = null;
			for (Tile s : tiles) {
				if (s.intersects(strechY)) {
					onASurface = true;
					standingSurface = s;
					yVelocity = 0;
				}
			}
			if (standingSurface != null) {
				Rectangle r = standingSurface.getBounds();
				yCoord2 = r.getY()-height;
			}
		} else if (yVelocity < 0) {
			Shape headSurface = null;
			for (Shape s : obstacles) {
				if (s.intersects(strechY)) {
					headSurface = s;
					yVelocity = 0;
				}
			}
			if (headSurface != null) {
				Rectangle r = headSurface.getBounds();
				yCoord2 = r.getY()+r.getHeight();
			}
		}

		if (Math.abs(yVelocity) < .2)
			yVelocity = 0;

		// ***********X AXIS***********


		xVelocity *= friction;

		double xCoord2 = xCoord + xVelocity;

		Rectangle2D.Double strechX = new Rectangle2D.Double(Math.min(xCoord,xCoord2),yCoord2,width+Math.abs(xVelocity),height);

		if (xVelocity > 0) {
			Shape rightSurface = null;
			for (Shape s : obstacles) {
				if (s.intersects(strechX)) {
					rightSurface = s;
					xVelocity = 0;
				}
			}
			if (rightSurface != null) {
				Rectangle r = rightSurface.getBounds();
				xCoord2 = r.getX()-width;
			}
		} else if (xVelocity < 0) {
			Shape leftSurface = null;
			for (Shape s : obstacles) {
				if (s.intersects(strechX)) {
					leftSurface = s;
					xVelocity = 0;
				}
			}
			if (leftSurface != null) {
				Rectangle r = leftSurface.getBounds();
				xCoord2 = r.getX()+r.getWidth();
			}
		}


		if (Math.abs(xVelocity) < .2)
			xVelocity = 0;

		moveToLocation(xCoord2,yCoord2);

	}*/

	/**
	 * Returns the player that this Tile object belongs to.
	 * @return player The player that this tiles belongs to.
	 */
	public boolean getPlayer() {
		return player;
	}
	
	/**
	 * Returns the string representation of the tile.
	 * @return the string representation of the tile.
	 */
	public String toString()
	{
		String result= "";
		if(player)
		{
			result = "Player 2 and Black";
		}
		else
			result = "Player 1 and Red";
		return result;
				
	}

}
