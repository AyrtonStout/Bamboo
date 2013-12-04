package GUI;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

import GUI.Enums.ACTION;
import GUI.Enums.*;

public class NPC implements Serializable {
	
	private static final long serialVersionUID = 5327185907689402233L;
	private ACTION facing;
	private ACTION behavior;
	private ArrayList<String> dialogue;
	private String name;
	private int coordX;
	private int coordY;
	private int charX;
	private int charY;
	
	private Map currentMap;
	
	private final int OFFSET_X = 4;
	private final int OFFSET_Y = -10;
	
	private ImageIcon currentImage;
	
	private ImageIcon left;
	private ImageIcon up;
	private ImageIcon right;
	private ImageIcon down;

	private ImageIcon walkLeft;
	private ImageIcon walkUp;
	private ImageIcon walkRight;
	private ImageIcon walkDown;
	
	Random rand = new Random();
	
	@SuppressWarnings("incomplete-switch")
	public NPC(NAMED_NPC type, ACTION facing, ACTION behavior, ArrayList<String> dialogue, int coordX, int coordY)	{
		
		if (type == NAMED_NPC.TERRA)	{
			
			name = "Terra";
			
			left = new ImageIcon("GUI/Resources/Terra (Left).gif");
			up= new ImageIcon("GUI/Resources/Terra (Up).gif");
			right= new ImageIcon("GUI/Resources/Terra (Right).gif");
			down= new ImageIcon("GUI/Resources/Terra (Down).gif");

			walkLeft = new ImageIcon("GUI/Resources/Terra - Walk (Left).gif");
			walkUp = new ImageIcon("GUI/Resources/Terra - Walk (Up).gif");
			walkRight = new ImageIcon("GUI/Resources/Terra - Walk (Right).gif");
			walkDown = new ImageIcon("GUI/Resources/Terra - Walk (Down).gif");
			
		}
		
		this.coordX = coordX;
		this.coordY = coordY;
		
		charX = coordX * 40 + OFFSET_X;
		charY = coordY * 40 + OFFSET_Y;
		
		if (facing != ACTION.LEFT && facing != ACTION.RIGHT && facing != ACTION.UP && facing != ACTION.DOWN)	{
			throw new IllegalArgumentException(facing + " is an improper argument for a character's facing");
		}
		else	{
			changeFacing(facing);
		}
		if (behavior != ACTION.STAND && behavior != ACTION.ROTATE && behavior != ACTION.RANDOM && behavior != ACTION.WANDER)	{
			throw new IllegalArgumentException(behavior + " is an improper argument for a character's behavior");
		}
		else	{
			this.behavior = behavior;
		}
		if (dialogue.size() == 0)	{
			throw new IllegalArgumentException("NPCs must be initiated with dialogue");
		}
		else	{
			this.dialogue = dialogue;
		}	
		
		
	}
	
	@SuppressWarnings("incomplete-switch")
	public void update()	{
		
		if (behavior == ACTION.STAND)	{
			
		}
		else if (behavior == ACTION.ROTATE)	{
			switch (facing)	{
			case LEFT:
				changeFacing(ACTION.DOWN);
				break;
			case DOWN:
				changeFacing(ACTION.RIGHT);
				break;
			case RIGHT:
				changeFacing(ACTION.UP);
				break;
			case UP:
				changeFacing(ACTION.LEFT);
				break;
			}
		}
		else if (behavior == ACTION.RANDOM)	{
			int random = rand.nextInt() % 4;
			switch (random)	{
			case 0:
				changeFacing(ACTION.DOWN);
				break;
			case 1:
				changeFacing(ACTION.RIGHT);
				break;
			case 2:
				changeFacing(ACTION.UP);
				break;
			case 3:
				changeFacing(ACTION.LEFT);
				break;
			}
		}
		else if (behavior == ACTION.WANDER)	{
			
		}
	}
	
	@SuppressWarnings("incomplete-switch")
	private void changeFacing(ACTION newFacing)	{
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
	
	public String getName()	{
		return name;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<String> getDialogue()	{
		return (ArrayList<String>) dialogue.clone();
	}
	
	public Image getImage()	{
		return currentImage.getImage();
	}
	
	public int getCoordX()	{
		return coordX;
	}
	public int getCoordY()	{
		return coordY;
	}
	public int getCharX()	{
		return charX;
	}
	public int getCharY()	{
		return charY;
	}
	public void setMap(Map map)	{
		currentMap = map;
	}

	@SuppressWarnings("incomplete-switch")
	public void setFacing(ACTION playerFacing) {
		switch (playerFacing)	{
		case LEFT:
			changeFacing(ACTION.RIGHT);
			break;
		case UP:
			changeFacing(ACTION.DOWN);
			break;
		case RIGHT:
			changeFacing(ACTION.LEFT);
			break;
		case DOWN:
			changeFacing(ACTION.UP);
			break;
		}
		
	}

}
