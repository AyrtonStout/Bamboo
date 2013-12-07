package GUI;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import javax.swing.ImageIcon;

import GUI.Enums.ACTION;

/**
 * @author mobius
 * 
 * Defines behavior and variables mutually used by the player and NPCs
 */

public abstract class Character implements Serializable {

	private static final long serialVersionUID = 1965396667319876293L;
	protected int charX, charY, coordX, coordY;
	protected Map map;                                                 //Current map passed in by the Board

	protected ACTION action = ACTION.STAND;                            //The action the character is currently performing
	protected ACTION queuedAction = ACTION.STAND;                      //The action the character will perform after the current action completes
	protected ACTION facing;                                           //Direction the character is facing. Also the last performed action
	protected boolean moving = false;                                  //Is the character currently performing an action
	protected boolean queuedMove = false;                              //Does the character have an action ready for when the current action completes
	protected final int STEP_SIZE = 40;                                //The number of pixels in a "grid square"
	protected int remainingSteps = 0;                                  //The number of pixels remaining in a character's move until it completes
	protected int speed = 2;                                           //The number of pixels traveled each time the character is updated
	protected String name;                                             //Name of the character
	
	protected ImageIcon currentImage;                                  //The image that the game draws when update() is called
	protected ImageIcon left, up, right, down;                         //The image for facing a certain direction
	protected ImageIcon walkLeft, walkUp, walkRight, walkDown;         //The image for walking in a certain direction
	
	protected final int OFFSET_X = 4;
	protected final int OFFSET_Y = -10;


	/**
	 * @param e KeyEvent passed in from the Board. Can only be an arrow key input
	 * 
	 * The KeyEvent causes the character to queue up a move to make when its current move
	 * has expired. This method does not start the action
	 */
	public void move(KeyEvent e) {
		queuedMove = true;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)        {
			queuedAction = ACTION.RIGHT;        
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)        {
			queuedAction = ACTION.LEFT;        
		}
		else if (e.getKeyCode() == KeyEvent.VK_UP)        {
			queuedAction = ACTION.UP;        
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)        {
			queuedAction = ACTION.DOWN;        
		}
	}


	/**
	 * Updates the grid coordinate of the character based on the action being performed
	 * 
	 * If the character is moving when this method is called, it will turn the tile in
	 * front of them into a moveblock. As such, call this method after checking if the
	 * tile is valid or movement will become impossible 
	 * 
	 * If the character is no longer moving, this will update the character's coordinates
	 * 
	 * @param action The facing of the player performing the action
	 * @param moving Whether or not the player is moving
	 */
	public void updateCoordinate(ACTION action, boolean moving) {
		if (moving)	{
			if (action == ACTION.LEFT && coordX > 0)
				map.getMoveblocks()[coordX - 1][coordY] = true;
			else if (action == ACTION.UP && coordY > 0)
				map.getMoveblocks()[coordX][coordY - 1] = true;
			else if (action == ACTION.RIGHT && coordX < map.getWidth() - 1)
				map.getMoveblocks()[coordX + 1][coordY] = true;
			else if (action == ACTION.DOWN && coordY < map.getHeight() - 1)
				map.getMoveblocks()[coordX][coordY + 1] = true;
		}
		else	{
			map.getMoveblocks()[coordX][coordY] = false;
			if (action == ACTION.LEFT)
				coordX--;
			else if (action == ACTION.UP)
				coordY--;
			else if (action == ACTION.RIGHT)
				coordX++;
			else 
				coordY++;
		}
	}

	/**
	 * @param action Action the player is attempting to start
	 * @return whether or not the attempted move is a legal move
	 * 
	 * This will return false if the player is attempting to walk off the map or if the tile the player is moving onto
	 * is registered as a moveblock in the moveblocks array contained in the map class
	 * 
	 * Even if the move is false, the player will turn to face the pressed direction
	 */
	protected boolean validMoveEh (ACTION action)        {
		boolean[][] moveCheck = map.getMoveblocks();
		if (action == ACTION.LEFT)        {
			if (coordX == 0 || moveCheck[coordX-1][coordY] == true)        {
				currentImage = left;
				return false;
			}
		}
		else if (action == ACTION.UP)        {
			if (coordY == 0 || moveCheck[coordX][coordY-1] == true)        {
				currentImage = up;
				return false;
			}
		}
		else if (action == ACTION.RIGHT)        {
			if (coordX == map.getWidth() - 1 || moveCheck[coordX+1][coordY] == true)        {
				currentImage = right;
				return false;
			}
		}
		else if (action == ACTION.DOWN)        {
			if (coordY == map.getHeight() - 1 || moveCheck[coordX][coordY+1] == true)        {
				currentImage = down;
				return false;
			}
		}
		return true;
	}

	/**
	 * @param action Action that has just come to an end
	 * @return the non animated version of the ending action
	 */
	protected ImageIcon stopAnimation(ACTION action)        {
		if (action == ACTION.LEFT)
			return left;
		else if (action == ACTION.UP)
			return up;
		else if (action == ACTION.RIGHT)
			return right;
		else 
			return down;
	}
	/**
	 * @param action Action to be started
	 * @return The animated version of that action
	 */
	protected ImageIcon startAnimation(ACTION action)        {
		if (action == ACTION.LEFT)
			return walkLeft;
		else if (action == ACTION.UP)
			return walkUp;
		else if (action == ACTION.RIGHT)
			return walkRight;
		else 
			return walkDown;
	}
	
	/**
	 * A shorthand method for changing a facing. Changes the facing variable
	 * and the current image to go with that facing at once
	 * 
	 * @param newFacing Facing the character is intended to now face
	 */
	@SuppressWarnings("incomplete-switch")
	protected void changeFacing(ACTION newFacing)	{
		switch (newFacing)	{
		case LEFT:
			facing = ACTION.LEFT;
			currentImage = left;
			break;
		case DOWN:
			facing = ACTION.DOWN;
			currentImage = down;
			break;
		case RIGHT:
			facing = ACTION.RIGHT;
			currentImage = right;
			break;
		case UP:
			facing = ACTION.UP;
			currentImage = up;
			break;
		}
	}
	
	/**
	 * Uses the name of the NPC to load their appropriate image files
	 * This is necessary to happen after NPCs are loaded from the ObjectStream
	 * as gifs will lose their animation in the process (and I'm too lazy to
	 * split the gifs into separate image files and animate them myself in code) 
	 */
	public void initializeImages()	{
		
		left = new ImageIcon("GUI/Resources/Characters/" + name + " (Left).gif");
		up= new ImageIcon("GUI/Resources/Characters/" + name + " (Up).gif");
		right= new ImageIcon("GUI/Resources/Characters/" + name + " (Right).gif");
		down= new ImageIcon("GUI/Resources/Characters/" + name + " (Down).gif");

		
		walkLeft = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Left).gif");
		walkUp = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Up).gif");
		walkRight = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Right).gif");
		walkDown = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Down).gif");
		
		changeFacing(facing);
		
	}
	
	/**
	 * How the character updates its on-screen pixel representation
	 * Player class's implementation should also handle screen scrolling
	 * 
	 * @param action The currently performed action
	 */
	protected abstract void updatePixels(ACTION action);
	/**
	 * @return Character's X coordinate on the visible screen. Left hand side is always 0 regardless of how far the map has scrolled
	 */
	public int getCharX()        {
		return charX;
	}
	/**
	 * @return Character's Y coordinate on the visible screen. Top side is always 0 regardless of how far the map has scrolled
	 */
	public int getCharY()        {
		return charY;
	}
	/**
	 * @return The character's grid X coordinate on the map
	 */
	public int getCoordX()        {
		return coordX;
	}
	/**
	 * @return The character's grid Y coordinate on the map
	 */
	public int getCoordY()        {
		return coordY;
	}
	/**
	 * @return Returns the image of the character's currently performed action
	 */
	public Image getImage() {
		return currentImage.getImage();
	}
	/**
	 * @return The player's current action
	 */
	public ACTION getFacing()        {
		return facing;
	}
	/**
	 * @param map Sets the map the character uses to determine the outcome of collision-based decisions
	 */
	public void setMap(Map map)        {
		this.map = map;
	}
	/**
	 * @return The currently used map
	 */
	public Map getMap()	{
		return map;
	}	
	/**
	 * @return Whether or not the character is currently moving
	 */
	public boolean movingEh()	{
		return moving;
	}
	/**
	 * @param x The new X coordinate for the player
	 * 
	 * This method also updates the player's charX value (where the player is drawn on the screen)
	 */
	public abstract void setCoordX(int x);
	/**
	 * @param y The new Y coordinate for the player
	 * 
	 * This method also updates the player's charY value (where the player is drawn on the screen)
	 */
	public abstract void setCoordY(int y);	
}
