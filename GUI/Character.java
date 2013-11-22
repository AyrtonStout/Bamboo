package GUI;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Character {
	int x, dx, charX, y, charY, dy;
	boolean moving = false;
	Image currentImage, reverseImage;
	
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

	public void move() {
		if (dx == 1){
			if (charX+1 <= 550)	{
				charX += 1;
			}
			else if (x < 450)	{
				x = x + 1;
			}
			else if (charX < 668)	{
				charX += 1;
			}
		}
		else if (dx == -1)	{
			if (charX+dx >= 150)	{
				charX += dx;
			}
			else if (x > 0)	{
				x = x + dx;
			}
			else if (charX > 0)	{
				charX += dx;
			}
		}

		else if (dy == -1)	{
			if (charY+dy >= 150)	{
				charY += dy;
			}
			else if (y > 0)	{
				y = y + dy;
			}
			else if (charY > 0)	{
				charY += dy;
			}
		}
		else if (dy == 1)	{
			if (charY+dy <= 300)	{
				charY += dy;
			}
			else if (y < 300)	{
				y = y + dy;
			}
			else if (charY < 430)	{
				charY += dy;
			}
		}
	}

	public int getX() {
		return x;
	}
	public int getdx() {
		return dx;
	}
	public int getCharX()	{
		return charX;
	}
	
	public int getY()	{
		return y;
	}
	public int getdy()	{
		return dy;
	}
	public int getCharY()	{
		return charY;
	}
	
	public Image getImage() {
		return currentImage;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (moving == false)	{
			if (key == KeyEvent.VK_LEFT)
			{		
				dx = -1;
				currentImage = walkLeft.getImage();
				moving = true;
			}
			if (key == KeyEvent.VK_RIGHT)	{
				dx = 1;
				currentImage = walkRight.getImage();
				moving = true;
			}
			if (key == KeyEvent.VK_UP)	{
				dy = -1;
				currentImage = walkUp.getImage();
				moving = true;
			}
			if (key == KeyEvent.VK_DOWN)	{
				dy = 1;
				currentImage = walkDown.getImage();
				moving = true;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT)	{
			dx = 0;
			currentImage = left.getImage();
			moving = false;
		}
		if (key == KeyEvent.VK_RIGHT)	{
			dx = 0;
			currentImage = right.getImage();
			moving = false;
		}
		if (key == KeyEvent.VK_UP)	{
			dy = 0;
			currentImage = up.getImage();
			moving = false;
		}
		if (key == KeyEvent.VK_DOWN)	{
			dy = 0;
			currentImage = down.getImage();
			moving = false;
		}
	}

}