package GUI;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import GUI.Enums.*;

/**
 * @author mobius
 * The (player-controlled) character for the game. Has knowledge of its own position as a coordinate that corresponds to the map
 * as well as its pixels for when it is drawn to the screen. The offset for the background is also contained here
 */
public class Character {
	private int backgroundX, backgroundY, charX, charY, coordX, coordY, walkDelay;
	private Map map;                                                 //Current map passed in by the Board
	int windowWidth, windowHeight;

	private ACTION action = ACTION.STAND;                            //The action the character is currently performing
	private ACTION queuedAction = ACTION.STAND;                      //The action the character will perform after the current action completes
	private ACTION facing;                                           //Direction the character is facing. Also the last performed action
	private boolean movingEh = false;                                //Is the character currently performing an action
	private boolean queuedMove = false;                              //Does the character have an action ready for when the current action completes
	private boolean doorTransition = false;                          //Whether or not the character just exited a door
	private final int STEP_SIZE = 40;                                //The number of pixels in a "grid square"
	private int remainingSteps = 0;                                  //The number of pixels remaining in a character's move until it completes
	private final int SPEED = 2;                                     //The number of pixels traveled each time the character is updated
	private final int MOVEMENT_BUFFER = 235;                         /*Minimum number of pixels between the character and the side of the screen
                                                                      for the screen to begin scrolling */
	private final int MOVEMENT_DELAY = 5;                            /*Delay before movement begins when changing directions
	                                                                   Recommended values 1-5 */
	
	private Image currentImage;                                      //The image that the game draws when update() is called

	private ImageIcon left = new ImageIcon("GUI/Resources/Sabin (Left).gif");
	private ImageIcon up= new ImageIcon("GUI/Resources/Sabin (Up).gif");
	private ImageIcon right= new ImageIcon("GUI/Resources/Sabin (Right).gif");
	private ImageIcon down= new ImageIcon("GUI/Resources/Sabin (Down).gif");

	private ImageIcon walkLeft = new ImageIcon("GUI/Resources/Sabin - Walk (Left).gif");
	private ImageIcon walkUp = new ImageIcon("GUI/Resources/Sabin - Walk (Up).gif");
	private ImageIcon walkRight = new ImageIcon("GUI/Resources/Sabin - Walk (Right).gif");
	private ImageIcon walkDown = new ImageIcon("GUI/Resources/Sabin - Walk (Down).gif");


	public Character(int windowWidth, int windowHeight) {
		backgroundX = 200;                        //How far the background has scrolled so far
		backgroundY = 0;
		coordX = 10;                                //Character's grid location
		coordY = 5;
		charX = coordX * 40 + 4 - backgroundX;          //Character's pixel location
		charY = coordY * 40 + -10 - backgroundY;
		currentImage = left.getImage();
		facing = ACTION.LEFT;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;

	}

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
	 * Cancels the queued move the character was going to make after its current move expired
	 * This results from the arrow keys being released
	 */
	public void cancelMove() {
		queuedAction = ACTION.STAND;
		queuedMove = false;
	}

	/**
	 * Actions to be performed when the Board updates the character.
	 * If the character is moving, it will move 'speed' pixels (currently 2) in that direction
	 * Depending on the current arrangement of the map, the character may move or the map may move instead
	 * 
	 * If there is no current action playing, any queued action will become the current action for the next 
	 * (STEP_SIZE / speed) updates (currently 20 updates)
	 */
	public void update()        {
		if (movingEh)        {
			if (action == ACTION.RIGHT)        {
				if (charX + SPEED <= windowWidth - MOVEMENT_BUFFER)        {                //Movement until character reaches the screen scroll buffer
					charX += SPEED;        
				}
				else if (backgroundX + windowWidth < map.getDrawingX())        {                //Movement of screen while character on buffer
					backgroundX += SPEED;
				}
				else if (charX < windowWidth - 35)        {                                                //Movement of character when there is no more screen left to scroll
					charX += SPEED;
				}
			}
			else if (action == ACTION.LEFT)        {
				if (charX - SPEED >= MOVEMENT_BUFFER - 32)        {
					charX -= SPEED;
				}
				else if (backgroundX > 0)        {
					backgroundX -= SPEED;
				}
				else if (charX > 4)        {
					charX -= SPEED;
				}        
			}
			else if (action == ACTION.UP)        {
				if (charY - SPEED >= MOVEMENT_BUFFER - 5)        {
					charY -= SPEED;
				}
				else if (backgroundY > 0)        {
					backgroundY -= SPEED;
				}
				else if (charY > -10)        {
					charY -= SPEED;
				}        
			}
			else if (action == ACTION.DOWN)        {
				if (charY + SPEED <= windowHeight - MOVEMENT_BUFFER - 15)        {        
					charY += SPEED;
				}
				else if (backgroundY + windowHeight < map.getDrawingY())        {                
					backgroundY += SPEED;
				}
				else if (charY < windowHeight - 40)        {                                                
					charY += SPEED;
				}
			}

			remainingSteps -= SPEED;        

			//When current move finishes, keep moving or stop movement
			if (remainingSteps == 0) {
				updateCoordinate(action);
				if (doorTransition = true)
					doorTransition = false;
				if (queuedMove & validMoveEh(queuedAction))	{
					action = queuedAction;
					facing = action;
					remainingSteps = STEP_SIZE;
					currentImage = startAnimation(action).getImage();
				}
				else	{
					currentImage = stopAnimation(action).getImage();
					movingEh = false;
					action = ACTION.STAND;
				}
			}
		}

		/*
		 * If player isn't moving, test and see if the move is a valid move. If the move is valid and in the same
		 * direction the player is facing, do the move. If the player is facing a different direction than the direction
		 * to be moved, turn the player and wait a slight amount before beginning the move.
		 * 
		 * This allows the player to change the direction they are facing while standing still
		 */
		else if (!movingEh)	{
			if (walkDelay > -1)	{
				walkDelay--;
			}
			if (queuedMove && validMoveEh(queuedAction))	{
				if (walkDelay == 0)	{
					action = queuedAction;
					remainingSteps = STEP_SIZE;
					movingEh = true;
					currentImage = startAnimation(queuedAction).getImage();
				}
				else if (walkDelay < 1)	{
					facingDelay(queuedAction);
				}
			}
			else if (queuedMove)	{
				facing = queuedAction;
			}	
		}
		
		
		if (transitionEh())	{
			Door enteredDoor = map.findDoor(getCoordX(), getCoordY());
			map = enteredDoor.getLink().getParentMap();
			setMap(map);
			if (enteredDoor.getLink().getX() * 40 + 20 - (windowWidth / 2) < 0)	{
				setBackgroundX(0);
			}
			else if (enteredDoor.getLink().getX() * 40 + 20 - (windowWidth / 2) > map.getWidth() * 40 - windowWidth)	{
				setBackgroundX(map.getWidth() * 40 - windowWidth);
			}
			else	{
				setBackgroundX(enteredDoor.getLink().getX() * 40 + 20 - (windowWidth / 2));
			}
			if (enteredDoor.getLink().getY() * 40 + 20 - (windowHeight / 2) < 0)	{
				setBackgroundY(0);
			}
			else if (enteredDoor.getLink().getY() * 40 + 20 - (windowHeight / 2) > map.getHeight() * 40 - windowHeight)	{
				setBackgroundY(map.getHeight() * 40 - windowHeight);
			}
			else	{
				setBackgroundY(enteredDoor.getLink().getY() * 40 + 20 - (windowHeight / 2));
			}
			setCoordX(enteredDoor.getLink().getX());
			setCoordY(enteredDoor.getLink().getY());
		}
	}

	/**
	 * @param action The player's queued move
	 * 
	 * Delays the next move of the player if it is facing a new direction
	 */
	private void facingDelay(ACTION action)	{
		if (facing == action)	{
			currentImage = stopAnimation(action).getImage();
			walkDelay = 1;
		}
		else if (action != ACTION.STAND)	{
			currentImage = stopAnimation(action).getImage();
			facing = action;
			walkDelay = MOVEMENT_DELAY;
		}
	}

	/**
	 * Updates the grid coordinate of the character based on the action being performed
	 * This is called once the current action completes, meaning that the game thinks the character is
	 * still at its previous location until the action has entirely finished
	 * 
	 * ^Note that this will probably create bugs with another NPC moving onto the same tile as the player
	 */
	private void updateCoordinate(ACTION action) {
		if (action == ACTION.LEFT)
			coordX--;
		else if (action == ACTION.UP)
			coordY--;
		else if (action == ACTION.RIGHT)
			coordX++;
		else 
			coordY++;
	}

	/**
	 * @param action Action the player is attempting to start
	 * @return whether or not the attempted move is a legal move
	 * 
	 * This will return false if the player is attempting to walk off the map or if the tile the player is moving onto
	 * has the "moveBlock == true" property
	 * 
	 * Even if the move is false, the player will turn to face the pressed direction
	 */
	private boolean validMoveEh (ACTION action)        {
		boolean[][] moveCheck = map.getMoveblocks();
		if (action == ACTION.LEFT)        {
			if (coordX == 0 || moveCheck[coordX-1][coordY] == true)        {
				currentImage = left.getImage();
				return false;
			}
		}
		else if (action == ACTION.UP)        {
			if (coordY == 0 || moveCheck[coordX][coordY-1] == true)        {
				currentImage = up.getImage();
				return false;
			}
		}
		else if (action == ACTION.RIGHT)        {
			if (coordX == map.getWidth() -1 || moveCheck[coordX+1][coordY] == true)        {
				currentImage = right.getImage();
				return false;
			}
		}
		else if (action == ACTION.DOWN)        {
			if (coordY == map.getHeight() -1 || moveCheck[coordX][coordY+1] == true)        {
				currentImage = down.getImage();
				return false;
			}
		}
		return true;
	}

	/**
	 * @param action Action that has just come to an end
	 * @return the non animated version of the ending action
	 */
	private ImageIcon stopAnimation(ACTION action)        {
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
	private ImageIcon startAnimation(ACTION action)        {
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
	 * @return Whether or not the player has ended on a tile that warrants a map or area transition
	 */
	public boolean transitionEh()	{
		if (!doorTransition)	{
			if (map.getArray()[coordX][coordY].getDoodad() != null)	{	
				if (map.getArray()[coordX][coordY].getDoodad().getClass() == Door.class)	{
					doorTransition = true;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return Background's X offset due to player movement
	 */
	public int getBackgroundX() {
		return backgroundX;
	}
	/**
	 * @return Background's Y offset due to player movement
	 */
	public int getBackgroundY()        {
		return backgroundY;
	}
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
		return currentImage;
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
	 * @param x The new X coordinate for the player
	 * 
	 * This method also updates the player's charX value (where the player is drawn on the screen)
	 */
	public void setCoordX(int x)	{
		coordX = x;
		charX = coordX * 40 + 4 - backgroundX;
	}
	/**
	 * @param y The new Y coordinate for the player
	 * 
	 * This method also updates the player's charY value (where the player is drawn on the screen)
	 */
	public void setCoordY(int y)	{
		coordY = y;
		charY = coordY * 40 - 10 - backgroundY;
	}
	/**
	 * @param x The new X offset for the background
	 */
	public void setBackgroundX(int x)	{
		backgroundX = x;
	}
	/**
	 * @param y The new Y offset for the background
	 */
	public void setBackgroundY(int y)	{
		backgroundY = y;
	}
	public boolean doorTransitionEh()	{
		return doorTransition;
	}
}