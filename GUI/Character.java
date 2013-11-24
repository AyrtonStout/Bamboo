package GUI;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 * @author mobius
 * The (player-controlled) character for the game. Has knowledge of its own position as a coordinate that corresponds to the map
 * as well as its pixels for when it is drawn to the screen. The offset for the background is also contained here
 */
public class Character {
	private int backgroundX, backgroundY, charX, charY, coordX, coordY;
	private Map map;									//Current map passed in by the Board
	
	private Action action = Action.STAND;				//The action the character is currently performing
	private Action queuedAction = Action.STAND;			//The action the character will perform after the current action completes
	private boolean movingEh = false;					//Is the character currently performing an action
	private boolean queuedMove = false;					//Does the character have an action ready for when the current action completes
	private int STEP_SIZE = 40;							//The number of pixels in a "grid square"
	private int remainingSteps = 0;						//The number of pixels remaining in a character's move until it completes
	private int speed = 2;								//The number of pixels traveled each time the character is updated
	private int movementBuffer = 160;					/*Minimum number of pixels between the character and the side of the screen
														for the screen to begin scrolling */
	
	private Image currentImage;							//The image that the game draws when update() is called
	
	private ImageIcon left = new ImageIcon("GUI/Resources/Sabin (Left).gif");
	private ImageIcon up= new ImageIcon("GUI/Resources/Sabin (Up).gif");
	private ImageIcon right= new ImageIcon("GUI/Resources/Sabin (Right).gif");
	private ImageIcon down= new ImageIcon("GUI/Resources/Sabin (Down).gif");
	
	private ImageIcon walkLeft = new ImageIcon("GUI/Resources/Sabin - Walk (Left).gif");
	private ImageIcon walkUp = new ImageIcon("GUI/Resources/Sabin - Walk (Up).gif");
	private ImageIcon walkRight = new ImageIcon("GUI/Resources/Sabin - Walk (Right).gif");
	private ImageIcon walkDown = new ImageIcon("GUI/Resources/Sabin - Walk (Down).gif");


	public Character() {
		backgroundX = 200;			//How far the background has scrolled so far
		backgroundY = 0;
		coordX = 12;				//Character grid location
		coordY = 3;
		charX = coordX * 40 + 4 - backgroundX;				//Character pixel location
		charY = coordY * 40 + -10;
		currentImage = left.getImage();
	
	}

	/**
	 * @param e KeyEvent passed in from the Board. Can only be an arrow key input
	 * 
	 * The KeyEvent causes the character to queue up a move to make when its current move
	 * has expired. This method does not start the action
	 */
	public void move(KeyEvent e) {
		queuedMove = true;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)	{
			queuedAction = Action.RIGHT;	
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)	{
			queuedAction = Action.LEFT;	
		}
		else if (e.getKeyCode() == KeyEvent.VK_UP)	{
			queuedAction = Action.UP;	
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)	{
			queuedAction = Action.DOWN;	
		}
	}
	
	/**
	 * Cancels the queued move the character was going to make after its current move expired
	 * This results from the arrow keys being released
	 */
	public void cancelMove() {
		queuedAction = Action.STAND;
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
	public void update()	{
		if (movingEh)	{
			if (action == Action.RIGHT)	{
				if (charX + speed <= map.getWindowWidth() - movementBuffer)	{		//Movement until character reaches the screen scroll buffer
					charX += speed;	
				}
				else if (backgroundX + map.getWindowWidth() < map.getDrawingX())	{		//Movement of screen while character on buffer
					backgroundX += speed;
				}
				else if (charX < map.getWindowWidth() - 35)	{						//Movement of character when there is no more screen left to scroll
					charX += speed;
				}
			}
			else if (action == Action.LEFT)	{
				if (charX - speed >= movementBuffer)	{
					charX -= speed;
				}
				else if (backgroundX > 0)	{
					backgroundX -= speed;
				}
				else if (charX > 4)	{
					charX -= speed;
				}	
			}
			else if (action == Action.UP)	{
				if (charY - speed >= movementBuffer)	{
					charY -= speed;
				}
				else if (backgroundY > 0)	{
					backgroundY -= speed;
				}
				else if (charY > -10)	{
					charY -= speed;
				}	
			}
			else if (action == Action.DOWN)	{
				if (charY + speed <= map.getWindowHeight() - movementBuffer)	{	
					charY += speed;
				}
				else if (backgroundY + map.getWindowHeight() < map.getDrawingY() + 30)	{		
					backgroundY += speed;
				}
				else if (charY < map.getWindowHeight() - 80)	{						
					charY += speed;
				}
			}
			
			remainingSteps -= speed;	
			
			
			if (remainingSteps == 0)	{								//Stops animation of current action once the current action has completed all of its updates
				movingEh = false;
				currentImage = stopAnimation(action).getImage();
				updateCoordinate(action);
				action = Action.STAND;
			}
		}
		
		if (!movingEh && queuedMove)	{			//If there is no current action but one is queued, the queued action becomes the current action
			if (validMoveEh(queuedAction))	{
				action = queuedAction;
				remainingSteps = STEP_SIZE;
				movingEh = true;
				currentImage = startAnimation(queuedAction).getImage();
			}
		}
//		System.out.println("X -   " + backgroundX + "   charX -   " + charX + "    coordX -   " + coordX + "    coordY -  " + coordY);
	}
	
	/**
	 * Updates the grid coordinate of the character based on the action being performed
	 * This is called once the current action completes, meaning that the game thinks the character is
	 * still at its previous location until the action has entirely finished
	 * 
	 * ^Note that this will probably create bugs with another NPC moving onto the same tile as the player
	 */
	private void updateCoordinate(Action action) {
		if (action == Action.LEFT)
			coordX--;
		else if (action == Action.UP)
			coordY--;
		else if (action == Action.RIGHT)
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
	 */
	private boolean validMoveEh (Action action)	{
		Tile[][] moveCheck = map.getArray();
		if (action == Action.LEFT)	{
			if (coordX == 0 || moveCheck[coordX-1][coordY].moveBlockEh())	{
				currentImage = left.getImage();
				return false;
			}
		}
		else if (action == Action.UP)	{
			if (coordY == 0 || moveCheck[coordX][coordY-1].moveBlockEh())	{
				currentImage = up.getImage();
				return false;
			}
		}
		else if (action == Action.RIGHT)	{
			if (coordX == map.getWidth() -1 || moveCheck[coordX+1][coordY].moveBlockEh())	{
				currentImage = right.getImage();
				return false;
			}
		}
		else if (action == Action.DOWN)	{
			if (coordY == map.getHeight() -1 || moveCheck[coordX][coordY+1].moveBlockEh())	{
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
	private ImageIcon stopAnimation(Action action)	{
		if (action == Action.LEFT)
			return left;
		else if (action == Action.UP)
			return up;
		else if (action == Action.RIGHT)
			return right;
		else 
			return down;
	}
	/**
	 * @param action Action to be started
	 * @return The animated version of that action
	 */
	private ImageIcon startAnimation(Action action)	{
		if (action == Action.LEFT)
			return walkLeft;
		else if (action == Action.UP)
			return walkUp;
		else if (action == Action.RIGHT)
			return walkRight;
		else 
			return walkDown;
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
	public int getBackgroundY()	{
		return backgroundY;
	}
	/**
	 * @return Character's X coordinate on the visible screen. Left hand side is always 0 regardless of how far the map has scrolled
	 */
	public int getCharX()	{
		return charX;
	}
	/**
	 * @return Character's Y coordinate on the visible screen. Top side is always 0 regardless of how far the map has scrolled
	 */
	public int getCharY()	{
		return charY;
	}

	/**
	 * @return Returns the image of the character's currently performed action
	 */
	public Image getImage() {
		return currentImage;
	}
	
	/**
	 * @param map Sets the map the character uses to determine the outcome of collision-based decisions
	 */
	public void setMap(Map map)	{
		this.map = map;
	}
}