package Map;

import Map.Enums.ACTION;
import Map.Enums.NAMED_NPC;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author mobius
 *         A visible map character that can be initialized with several behaviors.
 */
public class NPC extends CharacterAvatar implements Serializable {

	private static final long serialVersionUID = 5327185907689402233L;
	private ACTION behavior;
	private ArrayList<String> dialogue;
	private boolean talking = false;
	private boolean pointWalking = false;
	private boolean talkedTo = false;
	private Point endPoint;
	private int random;

	private ArrayList<Point> patrolPath = new ArrayList<Point>();
	private int currentPatrolPoint = 0;

	private boolean[][] wanderLimit;

	private Random rand = new Random();

	/**
	 * Creates an NPC at a particular coordinate and with a particular behavior. NPC must be of the enumerated type
	 * "NAMED_NPC" as that tells the constructor how to initialize the images and animations. NPC must be initialized
	 * with a behavior and a dialogue even if they are standing (initialize behavior as STAND) or are positioned somewhere
	 * that the player would never be able to talk to them.
	 *
	 * @param type     The NAMED_NPC enumerated type that this character draws art from
	 * @param facing   The initial facing direction of the NPC
	 * @param behavior The behavior of the NPC (standing, wandering, rotating)
	 * @param dialogue An ArrayList of all the lines of dialogue the NPC will speak
	 * @param coordX   The NPC's X coordinate on the map
	 * @param coordY   The NPC's Y coordinate on the map
	 */
	public NPC(NAMED_NPC type, ACTION facing, ACTION behavior, ArrayList<String> dialogue, int coordX, int coordY) {

		switch (type) {
			case SABIN:
				name = "Sabin";
				break;
			case TERRA:
				name = "Terra";
				break;
			case CELES:
				name = "Celes";
				break;
			case LOCKE:
				name = "Locke";
				break;
			case CYAN:
				name = "Cyan";
				break;
			case GHOST:
				name = "Ghost";
				break;
		}

		this.coordX = coordX;
		this.coordY = coordY;

		charX = coordX * 40 + OFFSET_X;
		charY = coordY * 40 + OFFSET_Y;

		speed = 1;

		if (facing != ACTION.LEFT && facing != ACTION.RIGHT && facing != ACTION.UP && facing != ACTION.DOWN) {
			throw new IllegalArgumentException(facing + " is an improper argument for a character's facing");
		} else {
			changeFacing(facing);
		}
		if (behavior != ACTION.STAND && behavior != ACTION.ROTATE && behavior != ACTION.RANDOM && behavior != ACTION.WANDER
				&& behavior != ACTION.PATROL) {
			throw new IllegalArgumentException(behavior + " is an improper argument for a character's behavior");
		} else {
			this.behavior = behavior;
		}
		if (dialogue.size() == 0) {
			throw new IllegalArgumentException("NPCs must be initiated with dialogue");
		} else {
			this.dialogue = dialogue;
		}
	}

	/**
	 * Update's the NPC's next action based on their already specified behavior
	 * NPC updates are largely ignored if they are initiated in dialogue
	 */
	public void update() {

		if (!talking) {
			if (pointWalking) {
				pointWalkBehavior();
			} else if (behavior == ACTION.STAND) {

			} else if (behavior == ACTION.ROTATE) {
				rotatingBehavior();
			} else if (behavior == ACTION.RANDOM) {
				randomBehavior();
			} else if (behavior == ACTION.WANDER) {
				wanderingBehavior();
			} else if (behavior == ACTION.PATROL) {
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
		switch (action) {
			case LEFT:
				charX -= speed;
				break;
			case RIGHT:
				charX += speed;
				break;
			case UP:
				charY -= speed;
				break;
			case DOWN:
				charY += speed;
				break;
		}
	}

	/**
	 * Makes the NPC face the opposite direction as the action used as the parameter. Useful for
	 * making the NPC react to an event such as the player talking to them (player faces to the left
	 * to talk to the NPC, therefore the NPC should face right)
	 *
	 * @param action The facing to be inverted
	 */
	@SuppressWarnings("incomplete-switch")
	public void invertFacing(ACTION action) {
		switch (action) {
			case LEFT:
				changeFacing(ACTION.RIGHT);
				break;
			case RIGHT:
				changeFacing(ACTION.LEFT);
				break;
			case UP:
				changeFacing(ACTION.DOWN);
				break;
			case DOWN:
				changeFacing(ACTION.UP);
				break;
		}
	}

	/**
	 * The destination coordinate for the NPC to walk to. The coordinate can only be in one direction
	 * so to get at a new X, Y coordinate this method will have to be called twice. Once for the X and
	 * one for the Y. If the character gets blocked by something on the way to its destination, movement
	 * will resume once the pathway is restored.
	 *
	 * @param destination The destination point for the NPC
	 */
	public void walkToPoint(Point destination) {

		ACTION attemptedMove;

		if (coordX != destination.x && coordY != destination.y) {
			System.out.println("X-  " + coordX + "  Y-  " + coordY);
			System.out.println("pX- " + destination.x + "  pY- " + destination.y);
			throw new IllegalArgumentException("Illegal coordinate. Only move in one direction at a time");
		}
		if (coordX < destination.x) {
			attemptedMove = ACTION.RIGHT;
		} else if (coordX > destination.x) {
			attemptedMove = ACTION.LEFT;
		} else if (coordY < destination.y) {
			attemptedMove = ACTION.DOWN;
		} else if (coordY > destination.y) {
			attemptedMove = ACTION.UP;
		} else {
			return;                //In case someone issues a move to nowhere
		}
		changeFacing(attemptedMove);
		action = attemptedMove;
		pointWalking = true;
		endPoint = destination;

		if (validMoveEh(action)) {
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
	private void rotatingBehavior() {
		if (remainingSteps > 0) {
			remainingSteps--;
		} else {
			switch (facing) {
				case LEFT:
					changeFacing(ACTION.UP);
					break;
				case UP:
					changeFacing(ACTION.RIGHT);
					break;
				case RIGHT:
					changeFacing(ACTION.DOWN);
					break;
				case DOWN:
					changeFacing(ACTION.LEFT);
					break;
			}
			remainingSteps = STEP_SIZE;
		}
	}

	/**
	 * Default random behavior will cause an NPC to randomly
	 * change their facing at random intervals
	 */
	private void randomBehavior() {
		random = rand.nextInt(50);
		if (random == 0) {
			random = rand.nextInt(4);
			switch (random) {
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
	}

	/**
	 * Default wandering behavior will cause an NPC to pick a direction
	 * at random and, if it is valid, proceed to walk one step in that
	 * direction. NPC may also turn to a new direction but not walk. It is
	 * not recommended to define a wandering NPC but not set wanderLimit() constraints.
	 */
	private void wanderingBehavior() {
		if (moving) {
			updatePixels(action);
			remainingSteps -= speed;
			if (remainingSteps == 0) {
				moving = false;
				currentImage = stopAnimation(action);
				updateCoordinate(facing, false);
			}
		} else if (remainingSteps > 0) {
			remainingSteps -= speed;
		} else {
			random = rand.nextInt(100);
			if (Math.abs(random) < 2) {
				random = rand.nextInt(4);
				{
					switch (random) {
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
					if (random != 0 && validMoveEh(facing) && validWanderEh(facing)) {
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
	 * Defines the behavior of a patrolling NPC. Once a patrolling NPC has completed its patrol cycle it will
	 * start over from the beginning.
	 */
	private void patrolBehavior() {
		walkToPoint(patrolPath.get(currentPatrolPoint));
		currentPatrolPoint++;
		if (currentPatrolPoint == patrolPath.size()) {
			currentPatrolPoint = 0;
		}
	}

	/**
	 * Defines the actions of an NPC that is walking from one point to another point called by the walkToPoint() method.
	 */
	private void pointWalkBehavior() {
		if (moving) {
			if (remainingSteps > 0) {
				updatePixels(action);
				remainingSteps -= speed;
			} else {
				updateCoordinate(action, false);
				if (coordX != endPoint.x || coordY != endPoint.y) {
					if (validMoveEh(action)) {
						remainingSteps = STEP_SIZE;
						updateCoordinate(action, true);
					} else {
						moving = false;
					}
				} else {
					currentImage = stopAnimation(action);
					pointWalking = false;
					moving = false;
				}
			}
		} else {
			if (validMoveEh(action)) {
				moving = true;
				remainingSteps = STEP_SIZE;
				updateCoordinate(action, true);
				currentImage = startAnimation(action);
			}
		}
	}

	/**
	 * Used by an NPC with a wandering behavior to see if its move violates its wander limit constraints
	 *
	 * @param wanderDirection The move the NPC is trying to do
	 * @return Whether or not the move is a valid one
	 */
	private boolean validWanderEh(ACTION wanderDirection) {
		if (wanderLimit == null) {
			wanderLimit = map.getMoveblocks().clone();
		}
		if (wanderDirection == ACTION.LEFT) {
			if (coordX == 0 || wanderLimit[coordX - 1][coordY] == true) {
				return false;
			}
		} else if (wanderDirection == ACTION.UP) {
			if (coordY == 0 || wanderLimit[coordX][coordY - 1] == true) {
				return false;
			}
		} else if (wanderDirection == ACTION.RIGHT) {
			if (coordX == map.getWidth() - 1 || wanderLimit[coordX + 1][coordY] == true) {
				return false;
			}
		} else if (wanderDirection == ACTION.DOWN) {
			if (coordY == map.getHeight() - 1 || wanderLimit[coordX][coordY + 1] == true) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return The name of the NPC
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return An ArrayList of Strings containing an NPC's dialogue
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getDialogue() {
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
	public boolean talkingEh() {
		return talking;
	}

	/**
	 * @param b Set whether or not the NPC is talking with someone
	 */
	public void setTalking(boolean b) {
		talking = b;
	}

	/**
	 * @return An ArrayList of points that this NPC will patrol between
	 */
	public ArrayList<Point> getPatrolPath() {
		return patrolPath;
	}

	/**
	 * The NPC will follow the points in order. Points must only change one coordinate (X or Y) at a time
	 *
	 * @param patrolPath The ArrayList of points that this NPC will patrol between
	 */
	public void setPatrolPath(ArrayList<Point> patrolPath) {
		this.patrolPath = patrolPath;
	}

	/**
	 * Sets the movement speed of the NPC. Default speed is 1. Player speed is 2. Other speeds not recommended
	 * and may break things.
	 *
	 * @param speed The new desired speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * A two dimensional array that adds constraints to what is considered a valid move for an NPC.
	 * Useful for a variety of things, the biggest being preventing NPCs from wandering into doors.
	 *
	 * @param wanderLimit
	 */
	public void setWanderLimit(boolean[][] wanderLimit) {
		this.wanderLimit = wanderLimit;
	}

	/**
	 * Denotes whether or not the NPC has been talked to by the player. This is a one time triggered event.
	 * NPC will not naturally return to a false state once triggered true.
	 *
	 * @return Whether or not the player has spoken to this NPC
	 */
	public boolean talkedToEh() {
		return talkedTo;
	}

	/**
	 * @param b Whether or not this NPC has been spoken to by the player
	 */
	public void setTalkedTo(boolean b) {
		talkedTo = b;
	}
}
