package GUI;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import GUI.Enums.ACTION;
import GUI.Enums.*;

public class NPC extends Character implements Serializable {
	
	private static final long serialVersionUID = 5327185907689402233L;
	private ACTION behavior;
	private ArrayList<String> dialogue;
	private boolean talking = false;
	private boolean pointWalking = false;
	private Point endPoint;
	private int random;
	
	private ArrayList<Point> patrolPath = new ArrayList<Point>();
	private int currentPatrolPoint = 0;
	
	private boolean[][] wanderLimit;
	
	private Random rand = new Random();
	
	public NPC(NAMED_NPC type, ACTION facing, ACTION behavior, ArrayList<String> dialogue, int coordX, int coordY)	{
		
		switch (type)	{
		case TERRA:
			name = "Terra"; break;
		case CELES:
			name = "Celes"; break;
		case LOCKE:
			name = "Locke"; break;
		case CYAN:
			name = "Cyan"; break;
		case GHOST:
			name = "Ghost"; break;
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
		
		if (!talking)	{
			if (pointWalking)	{
				pointWalkBehavior();
			}
			else if (behavior == ACTION.STAND)	{

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
			else if (behavior == ACTION.PATROL)	{
				patrolBehavior();
			}
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
	
	public void walkToPoint(Point destination)	{
		
		ACTION attemptedMove;
		
		if (coordX != destination.x && coordY != destination.y)	{
			System.out.println("X-  " + coordX + "  Y-  " + coordY);
			System.out.println("pX- " + destination.x + "  pY- " + destination.y);
			throw new IllegalArgumentException("Illegal coordinate. Only move in one direction at a time");
		}
		if (coordX < destination.x)	{
			attemptedMove = ACTION.RIGHT;
		}
		else if (coordX > destination.x)	{
			attemptedMove = ACTION.LEFT;
		}
		else if (coordY < destination.y)	{
			attemptedMove = ACTION.DOWN;
		}
		else if (coordY > destination.y)	{
			attemptedMove = ACTION.UP;
		}
		else {
			return;				//In case someone issues a move to nowhere
		}
		changeFacing(attemptedMove);
		action = attemptedMove;
		pointWalking = true;
		endPoint = destination;

		if (validMoveEh(action))	{
			moving = true;
			remainingSteps = STEP_SIZE;
			this.updateCoordinate(action, true);
			currentImage = startAnimation(attemptedMove);
		}
	}
	
	/**
	 * Default behavior for a rotating NPC. A rotating NPC will change 
	 * direction at regular intervals in a clockwise fashion
	 */
	@SuppressWarnings("incomplete-switch")
	private void rotatingBehavior()	{
		if (remainingSteps > 0)	{
			remainingSteps--;
		}
		else	{
			switch (facing)	{
			case LEFT:
				changeFacing(ACTION.UP); break;
			case UP:
				changeFacing(ACTION.RIGHT); break;
			case RIGHT:
				changeFacing(ACTION.DOWN); break;
			case DOWN:
				changeFacing(ACTION.LEFT); break;
			}
			remainingSteps = STEP_SIZE;
		}
	}
	
	/**
	 * Default random behavior will cause an NPC to randomly
	 * change their facing at random intervals
	 */
	private void randomBehavior()	{
		random = rand.nextInt(50);
		if (random == 0)	{
			random = rand.nextInt(4);
			switch (random)	{
			case 0:
				changeFacing(ACTION.DOWN); break;
			case 1:
				changeFacing(ACTION.RIGHT); break;
			case 2:
				changeFacing(ACTION.UP); break;
			case 3:
				changeFacing(ACTION.LEFT); break;
			}
		}
	}

	/**
	 * Default wandering behavior will cause an NPC to pick a direction
	 * at random and, if it is valid, proceed to walk one step in that 
	 * direction. NPC may also turn to a new direction but not walk
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
		else {
			random = rand.nextInt(100);
			if (Math.abs(random) < 2)	{
				random = rand.nextInt(4);{
					switch (random)	{
					case 0:
						changeFacing(ACTION.DOWN); break;
					case 1:
						changeFacing(ACTION.RIGHT); break;
					case 2:
						changeFacing(ACTION.UP); break;
					case 3:
						changeFacing(ACTION.LEFT); break;
					}
					random = rand.nextInt(3);
					remainingSteps = STEP_SIZE * 2;
					if (random != 0 && validMoveEh(facing) && validWanderEh(facing))	{
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
	
	private void patrolBehavior()	{
		walkToPoint(patrolPath.get(currentPatrolPoint));
		currentPatrolPoint++;
		if (currentPatrolPoint == patrolPath.size())	{
			currentPatrolPoint = 0;
		}
	}

	private void pointWalkBehavior()	{
		if (moving)	{
			if (remainingSteps > 0)	{
				updatePixels(action);
				remainingSteps -= speed;
			}
			else {
				updateCoordinate(action, false);
				if (coordX != endPoint.x || coordY != endPoint.y)	{
					if (validMoveEh(action))	{
						remainingSteps = STEP_SIZE;
						updateCoordinate(action, true);
					}
					else	{
						moving = false;
					}
				}
				else	{
					currentImage = stopAnimation(action);
					pointWalking = false;
					moving = false;
				}
			}
		}
		else	{
			if (validMoveEh(action))	{
				moving = true;
				remainingSteps = STEP_SIZE;
				updateCoordinate(action, true);
				currentImage = startAnimation(action);
			}
		}
	}
	
	private boolean validWanderEh(ACTION wanderDirection)	{
		if (wanderLimit == null)	{
			wanderLimit = map.getMoveblocks().clone();
		}
		if (wanderDirection == ACTION.LEFT)        {
			if (coordX == 0 || wanderLimit[coordX-1][coordY] == true)        {
				return false;
			}
		}
		else if (wanderDirection == ACTION.UP)        {
			if (coordY == 0 || wanderLimit[coordX][coordY-1] == true)        {
				return false;
			}
		}
		else if (wanderDirection == ACTION.RIGHT)        {
			if (coordX == map.getWidth() - 1 || wanderLimit[coordX+1][coordY] == true)        {
				return false;
			}
		}
		else if (wanderDirection == ACTION.DOWN)        {
			if (coordY == map.getHeight() - 1 || wanderLimit[coordX][coordY+1] == true)        {
				return false;
			}
		}
		return true;
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
	/**
	 * @return An ArrayList of points that this NPC will patrol between
	 */
	public ArrayList<Point> getPatrolPath()	{
		return patrolPath;
	}
	/**
	 * The NPC will follow the points in order. Points must only change one coordinate (X or Y) at a time
	 * 
	 * @param patrolPath The ArrayList of points that this NPC will patrol between
	 */
	public void setPatrolPath(ArrayList<Point> patrolPath)	{
		this.patrolPath = patrolPath;
	}
	public void setSpeed(int speed)	{
		this.speed = speed;
	}
	public void setWanderLimit(boolean[][] wanderLimit) {
		this.wanderLimit = wanderLimit;
	}
}
