package GUI;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Character {
	int x, charX, charY;
	
	Action action = Action.STAND;				//The action the character is currently performing
	Action queuedAction = Action.STAND;			//The action the character will perform after the current action completes
	boolean movingEh = false;					//Is the character currently performing an action
	boolean queuedMove = false;					//Does the character have an action ready for when the current action completes
	int MAX_STEPS = 40;							//The number of pixels in a "grid square"
	int remainingSteps = 0;						//The number of pixels remaining in a character's move until it completes
	int speed = 2;								//The number of pixels traveled each time the character is updated
	
	
	Image currentImage;
	
	ImageIcon left = new ImageIcon("GUI/Sabin (Left).gif");
	ImageIcon up= new ImageIcon("GUI/Sabin (Up).gif");
	ImageIcon right= new ImageIcon("GUI/Sabin (Right).gif");
	ImageIcon down= new ImageIcon("GUI/Sabin (Down).gif");
	
	ImageIcon walkLeft = new ImageIcon("GUI/Sabin - Walk (Left).gif");
	ImageIcon walkUp = new ImageIcon("GUI/Sabin - Walk (Up).gif");
	ImageIcon walkRight = new ImageIcon("GUI/Sabin - Walk (Right).gif");
	ImageIcon walkDown = new ImageIcon("GUI/Sabin - Walk (Down).gif");
	
	public Character() {
		x = 75;			//How far the background has scrolled so far
		charX = 400;	//Character Location
		charY = 172;
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
				if (charX + speed <= 550)	{
					charX += speed;
					currentImage = walkRight.getImage();
				}
				else if (x < 450)	{
					x += speed;
					currentImage = walkRight.getImage();
				}
				else if (charX < 668)	{
					charX += speed;
					currentImage = walkRight.getImage();
				}
			}
			else if (action == Action.LEFT)	{
				if (charX - speed >= 150)	{
					charX -= speed;
				}
				else if (x > 0)	{
					x -= speed;
				}
				else if (charX > 0)	{
					charX -= speed;
				}	
			}
			else if (action == Action.UP)	{
				if (charY > 0)	{
					charY -= speed;
				}	
			}
			else if (action == Action.DOWN)	{
				if (charY <= 300)	{
					charY += speed;
				}	
			}
			remainingSteps -= speed;
			if (remainingSteps == 0)	{
				movingEh = false;
				currentImage = stopAnimation(action).getImage();
				action = Action.STAND;
			}
		}
		if (!movingEh && queuedMove)	{
			action = queuedAction;
			remainingSteps = MAX_STEPS;
			movingEh = true;
			currentImage = startAnimation(queuedAction).getImage();
		}
	}
	
	public ImageIcon stopAnimation(Action action)	{
		if (action == Action.LEFT)
			return left;
		else if (action == Action.UP)
			return up;
		else if (action == Action.RIGHT)
			return right;
		else 
			return down;
	}
	public ImageIcon startAnimation(Action action)	{
		if (action == Action.LEFT)
			return walkLeft;
		else if (action == Action.UP)
			return walkUp;
		else if (action == Action.RIGHT)
			return walkRight;
		else 
			return walkDown;
	}
		


	public int getX() {
		return x;
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
}