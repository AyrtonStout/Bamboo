package GUI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

import GUI.Enums.ACTION;
import GUI.Enums.*;

public class NPC extends Character implements Serializable {
	
	private static final long serialVersionUID = 5327185907689402233L;
	private ACTION behavior;
	private ArrayList<String> dialogue;
	private boolean talking = false;
	
	Random rand = new Random();
	
	public NPC(NAMED_NPC type, ACTION facing, ACTION behavior, ArrayList<String> dialogue, int coordX, int coordY)	{
		
		if (type == NAMED_NPC.TERRA)	{
			
			name = "Terra";
			
		}
		
		else if (type == NAMED_NPC.CELES)	{
			
			name = "Celes";
				
		}
		
		this.coordX = coordX;
		this.coordY = coordY;
		
		charX = coordX * 40 + OFFSET_X;
		charY = coordY * 40 + OFFSET_Y;
		
		speed = 1;

		if (facing != ACTION.LEFT && facing != ACTION.RIGHT && facing != ACTION.UP && facing != ACTION.DOWN)	{
			throw new IllegalArgumentException(facing + " is an improper argument for a character's facing");
		}
		else	{
			changeFacing(facing); 
		}
		if (behavior != ACTION.STAND && behavior != ACTION.ROTATE && behavior != ACTION.RANDOM && behavior != ACTION.WANDER
				&& behavior != ACTION.PATROL)	{
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
	
	
	
	/**
	 * Update's the NPC's next action based on their already specified behavior
	 * NPC updates are largely ignored if they are initiated in dialogue
	 */
	public void update()	{
		
		if (behavior == ACTION.STAND)	{
			
		}
		else if (behavior == ACTION.ROTATE)	{
			rotatingBehavior();
		}
		else if (behavior == ACTION.RANDOM)	{
			randomBehavior();
		}
		else if (behavior == ACTION.WANDER)	{
			wanderingBehavior();
		}
	}

	/* 
	 * Changes the NPCs pixel representation according to the
	 * direction that they are moving
	 */
	@SuppressWarnings("incomplete-switch")
	@Override
	protected void updatePixels(ACTION action) {
		switch (action)	{
		case LEFT:
			charX -= speed; break;
		case RIGHT:
			charX += speed; break;
		case UP:
			charY -= speed; break;
		case DOWN:
			charY += speed; break;
		}
	}
	
	@SuppressWarnings("incomplete-switch")
	public void invertFacing(ACTION action)	{
		switch (action)	{
		case LEFT:
			changeFacing(ACTION.RIGHT); break;
		case RIGHT:
			changeFacing(ACTION.LEFT); break;
		case UP:
			changeFacing(ACTION.DOWN); break;
		case DOWN:
			changeFacing(ACTION.UP); break;
		}
	}
	
	/**
	 * Default behavior for a rotating NPC. A rotating NPC will change 
	 * direction at regular intervals in a counter-clockwise fashion
	 */
	@SuppressWarnings("incomplete-switch")
	private void rotatingBehavior()	{
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
	
	/**
	 * Default random behavior will cause an NPC to randomly
	 * change their facing at random intervals
	 */
	private void randomBehavior()	{
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

	/**
	 * Default wandering behavior will cause an NPC to pick a direction
	 * at random and, if it is valid, proceed to walk one step in that 
	 * direction. 
	 */
	private void wanderingBehavior()	{
		if (moving)	{
			updatePixels(action);
			remainingSteps -= speed;
			if (remainingSteps == 0)	{
				moving = false;
				currentImage = stopAnimation(action);
				updateCoordinate(facing, false);
			}
		}
		else if (remainingSteps > 0)	{
			remainingSteps -= speed;
		}
		else if (!talking)	{
			int random = rand.nextInt(100);
			if (Math.abs(random) < 2)	{
				random = rand.nextInt(4);{
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
					random = rand.nextInt(3);
					remainingSteps = STEP_SIZE * 2;
					if (random != 0 && validMoveEh(facing))	{
						remainingSteps -= STEP_SIZE;
						action = facing;
						currentImage = startAnimation(action);
						moving = true;
						updateCoordinate(facing, true);
					}
				}
			}
		}
	}

	/**
	 * @return The name of the NPC
	 */
	public String getName()	{
		return name;
	}
	/**
	 * @return An ArrayList of Strings containing an NPC's dialogue
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getDialogue()	{
		return (ArrayList<String>) dialogue.clone();
	}
	@Override
	public void setCoordX(int x) {
		coordX = x;	
	}
	@Override
	public void setCoordY(int y) {
		coordY = y;
	}
	/**
	 * @return Whether or not the NPC is talking with someone
	 */
	public boolean talkingEh()	{
		return talking;
	}
	/**
	 * @param b Set whether or not the NPC is talking with someone
	 */
	public void setTalking(boolean b)	{
		talking = b;
	}
}
