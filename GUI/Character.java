package GUI;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Character {
	private int backgroundX, backgroundY, charX, charY, coordX, coordY;
	private Map map;
	
	private Action action = Action.STAND;				//The action the character is currently performing
	private Action queuedAction = Action.STAND;			//The action the character will perform after the current action completes
	private boolean movingEh = false;					//Is the character currently performing an action
	private boolean queuedMove = false;					//Does the character have an action ready for when the current action completes
	private int STEP_SIZE = 40;							//The number of pixels in a "grid square"
	private int remainingSteps = 0;						//The number of pixels remaining in a character's move until it completes
	private int speed = 2;								//The number of pixels traveled each time the character is updated
	private int movementBuffer = 160;					/*Minimum number of pixels between the character and the side of the screen
														for the screen to begin scrolling */
	
	private Image currentImage;
	
	private ImageIcon left = new ImageIcon("GUI/Sabin (Left).gif");
	private ImageIcon up= new ImageIcon("GUI/Sabin (Up).gif");
	private ImageIcon right= new ImageIcon("GUI/Sabin (Right).gif");
	private ImageIcon down= new ImageIcon("GUI/Sabin (Down).gif");
	
	private ImageIcon walkLeft = new ImageIcon("GUI/Sabin - Walk (Left).gif");
	private ImageIcon walkUp = new ImageIcon("GUI/Sabin - Walk (Up).gif");
	private ImageIcon walkRight = new ImageIcon("GUI/Sabin - Walk (Right).gif");
	private ImageIcon walkDown = new ImageIcon("GUI/Sabin - Walk (Down).gif");


	public Character() {
		backgroundX = 200;			//How far the background has scrolled so far
		backgroundY = 0;
		coordX = 12;				//Character grid location
		coordY = 3;
		charX = coordX * 40 + 4 - backgroundX;				//Character pixel location
		charY = coordY * 40 + -10;
		currentImage = left.getImage();
	
	}

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
	
	public void cancelMove() {
		queuedAction = Action.STAND;
		queuedMove = false;
	}
	
	public void update()	{
		if (movingEh)	{
			if (action == Action.RIGHT)	{
				if (charX + speed <= map.getWindowWidth() - movementBuffer)	{	//Movement until character reaches buffer
					charX += speed;
				}
				else if (backgroundX + map.getWindowWidth() < map.getWidth() * 40)	{			//Movement of screen while character on buffer
					backgroundX += speed;
				}
				else if (charX < map.getWindowWidth() - 35)	{						//Movement of character when no more screen remains
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
				else if (backgroundY + map.getWindowHeight() < map.getHeight() * 40 + 30)	{		
					backgroundY += speed;
				}
				else if (charY < map.getWindowHeight() - 80)	{						
					charY += speed;
				}
			}
			remainingSteps -= speed;
			if (remainingSteps == 0)	{
				movingEh = false;
				currentImage = stopAnimation(action).getImage();
				updateCoordinate(action);
				action = Action.STAND;
			}
		}
		if (!movingEh && queuedMove)	{
			if (validMoveEh(queuedAction))	{
				action = queuedAction;
				remainingSteps = STEP_SIZE;
				movingEh = true;
				currentImage = startAnimation(queuedAction).getImage();
			}
		}
		System.out.println("X -   " + backgroundX + "   charX -   " + charX + "    coordX -   " + coordX + "    coordY -  " + coordY);
	}
	
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
		


	public int getBackgroundX() {
		return backgroundX;
	}
	public int getBackgroundY()	{
		return backgroundY;
	}
	public int getCharX()	{
		return charX;
	}
	public int getCharY()	{
		return charY;
	}

	public Image getImage() {
		return currentImage;
	}
	
	public void setMap(Map map)	{
		this.map = map;
	}
}