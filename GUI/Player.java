package GUI;

import java.awt.event.KeyEvent;
import java.io.Serializable;

import javax.swing.ImageIcon;

import GUI.Enums.ACTION;

/**
 * @author mobius
 * The (player-controlled) character for the game. Has knowledge of its own position as a coordinate that corresponds to the map
 * as well as its pixels for when it is drawn to the screen. The offset for the background is also contained here
 */
public class Player extends Character implements Serializable {

	private static final long serialVersionUID = 5680477392037994831L;
	private int backgroundX, backgroundY, walkDelay;                                              //Current map passed in by the Board
	int windowWidth, windowHeight;

	private boolean doorTransition = false;                          //Whether or not the character just exited a door
	private final int MOVEMENT_BUFFER = 235;                         /*Minimum number of pixels between the character and the side of the screen
                                                                      for the screen to begin scrolling */
	private final int MOVEMENT_DELAY = 5;                            /*Delay before movement begins when changing directions
	                                                                   Recommended values 1-5 */
	private NPC interactingNPC = null;                                //The NPC the player is (likely) talking to
	
	private boolean keyLeft = false;
	private boolean keyUp = false;
	private boolean keyRight = false;
	private boolean keyDown = false;

	public Player(String name, int windowWidth, int windowHeight) {
		
		this.name = name;
		
		left = new ImageIcon("GUI/Resources/Sabin (Left).gif");
		up= new ImageIcon("GUI/Resources/Sabin (Up).gif");
		right= new ImageIcon("GUI/Resources/Sabin (Right).gif");
		down= new ImageIcon("GUI/Resources/Sabin (Down).gif");
	
		backgroundX = 200;                        //How far the background has scrolled so far
		backgroundY = 0;
		coordX = 10;                                //Character's grid location
		coordY = 5;
		charX = coordX * STEP_SIZE + OFFSET_X - backgroundX;          //Character's pixel location
		charY = coordY * STEP_SIZE + OFFSET_Y - backgroundY;
		currentImage = down;
		facing = ACTION.DOWN;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		speed = 2;
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
			keyRight = true;
			queuedAction = ACTION.RIGHT;        
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)        {
			keyLeft = true;
			queuedAction = ACTION.LEFT;        
		}
		else if (e.getKeyCode() == KeyEvent.VK_UP)        {
			keyUp = true;
			queuedAction = ACTION.UP;        
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)        {
			keyDown = true;
			queuedAction = ACTION.DOWN;        
		}
	}
	
	/**
	 * Cancels the queued move the character was going to make after its current move expired
	 * The move that is cancelled depends on the key that is released. If another key is still
	 * held down (denoted by the keyLeft, keyUp, etc booleans) that movement will take over instead
	 * 
	 * @param e The move to be cancelled
	 */
	public void cancelMove(KeyEvent e) {
		switch (e.getKeyCode())	{
		case KeyEvent.VK_LEFT:
			keyLeft = false; break;
		case KeyEvent.VK_UP:
			keyUp = false; break;
		case KeyEvent.VK_RIGHT:
			keyRight = false; break;
		case KeyEvent.VK_DOWN:
			keyDown = false; break;
		}
		
		if (keyLeft == true)
			queuedAction = ACTION.LEFT;
		else if (keyUp == true)
			queuedAction = ACTION.UP;
		else if (keyRight == true)
			queuedAction = ACTION.RIGHT;
		else if (keyDown == true)
			queuedAction = ACTION.DOWN;
		else	{
			queuedAction = ACTION.STAND;
			queuedMove = false;
		}
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
		if (moving)        {
			if (remainingSteps == 40)	{
				updateCoordinate(action, true);
			}
			updatePixels(action);
			remainingSteps -= speed;        

			//When current move finishes, keep moving or stop movement
			if (remainingSteps == 0) {
				updateCoordinate(action, false);
				if (doorTransition = true)
					doorTransition = false;
				if (queuedMove & validMoveEh(queuedAction))	{
					action = queuedAction;
					facing = action;
					remainingSteps = STEP_SIZE;
					currentImage = startAnimation(action);
					updateCoordinate(action, true);
				}
				else	{
					currentImage = stopAnimation(action);
					moving = false;
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
		else if (!moving)	{
			if (walkDelay > -1)	{
				walkDelay--;
			}
			if (queuedMove && validMoveEh(queuedAction))	{
				if (walkDelay == 0)	{
					action = queuedAction;
					remainingSteps = STEP_SIZE;
					moving = true;
					currentImage = startAnimation(queuedAction);
				}
				else if (walkDelay < 1)	{
					facingDelay(queuedAction);
				}
			}
			else if (queuedMove)	{
				facing = queuedAction;
			}	
		}
		
		//Changing maps
		if (transitionEh())	{
			map.moveBlocks[coordX][coordY] = false;
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
			map.moveBlocks[coordX][coordY] = true;
			moving = false;
			queuedMove = false;
			action = ACTION.STAND;
			queuedAction = ACTION.STAND;
		}
	}

	/**
	 * @param action The player's queued move
	 * 
	 * Delays the next move of the player if it is facing a new direction
	 */
	private void facingDelay(ACTION action)	{
		if (facing == action)	{
			currentImage = stopAnimation(action);
			walkDelay = 1;
		}
		else if (action != ACTION.STAND)	{
			currentImage = stopAnimation(action);
			facing = action;
			walkDelay = MOVEMENT_DELAY;
		}
	}
	
	/**
	 * Will return true if the character is walking into a door classified as a walkTransition.
	 * Will return true if the character is already on a door classified as a directionTransition
	 * and pushes the key that corresponds to the transition.
	 * 
	 * @return Whether or not the player has ended on a tile that warrants a map or area transition
	 */
	public boolean transitionEh()	{
		
		//Walk doors
		if (!doorTransition)	{
			if (map.getArray()[coordX][coordY].getDoodad() != null)	{	
				if (map.getArray()[coordX][coordY].getDoodad().getClass() == Door.class)	{
					if (((Door) map.getArray()[coordX][coordY].getDoodad()).walkTransitionEh())	{
						doorTransition = true;
						return true;
					}
					else if (((Door) map.getArray()[coordX][coordY].getDoodad()).directionTransitionEh())	{
						doorTransition = true;
					}
				}
			}
		}
		//Transition doors
		else if (doorTransition && !moving)	{
			if (((Door) map.getArray()[coordX][coordY].getDoodad()).directionTransitionEh())	{
				if (((Door) map.getArray()[coordX][coordY].getDoodad()).getDirection() == queuedAction)	{
					return true;
				}
			}
		}
		//No doors
		return false;
	}


	@Override
	protected void updatePixels(ACTION action) {
		if (action == ACTION.RIGHT)        {
			if (charX + speed <= windowWidth - MOVEMENT_BUFFER)        {             //Movement until character reaches the screen scroll buffer
				charX += speed;        
			}
			else if (backgroundX + windowWidth < map.getDrawingX())        {         //Movement of screen while character on buffer
				backgroundX += speed;
			}
			else if (charX < windowWidth - 35)        {                              //Movement of character when there is no more screen left to scroll
				charX += speed;
			}
		}
		else if (action == ACTION.LEFT)        {
			if (charX - speed >= MOVEMENT_BUFFER - 32)        {
				charX -= speed;
			}
			else if (backgroundX > 0)        {
				backgroundX -= speed;
			}
			else if (charX > 4)        {
				charX -= speed;
			}        
		}
		else if (action == ACTION.UP)        {
			if (charY - speed >= MOVEMENT_BUFFER - 5)        {
				charY -= speed;
			}
			else if (backgroundY > 0)        {
				backgroundY -= speed;
			}
			else if (charY > -10)        {
				charY -= speed;
			}        
		}
		else if (action == ACTION.DOWN)        {
			if (charY + speed <= windowHeight - MOVEMENT_BUFFER - 15)        {        
				charY += speed;
			}
			else if (backgroundY + windowHeight < map.getDrawingY())        {                
				backgroundY += speed;
			}
			else if (charY < windowHeight - 40)        {                                                
				charY += speed;
			}
		}
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
	/**
	 * @return Whether or not the player has just exited a door
	 */
	public boolean doorTransitionEh()	{
		return doorTransition;
	}
	/**
	 * @return The NPC the player is interacting/talking with
	 */
	public NPC getInteractingNPC()	{
		return interactingNPC;
	}
	/**
	 * @param interactedNPC The NPC the player is now talking to
	 */
	public void setInteractingNPC(NPC interactedNPC) {
		interactingNPC = interactedNPC;
	}
}
