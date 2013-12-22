package Quests;

import java.awt.Point;
import java.io.Serializable;

import GUI.Player;
import Quests.Enums.TEVENT;

/**
 * @author mobius
 * Part of a triggerable sequence that controls when the trigger is ready to fire. An event is a fancy
 * boolean that determines when a trigger is ready to fire.
 */
public class TEvent implements Serializable {
	
	private static final long serialVersionUID = -3505855084423834681L;

	private TEVENT eventType;
	private Point enteredPoint;
	
	/**
	 * A constructor for a trigger event that relies on a point
	 * 
	 * @param event The type of event. In this case, must be an event that involves interacting with a point
	 * @param enteredPoint The point to be interacted with
	 */
	public TEvent(TEVENT event, Point enteredPoint)	{
		this.eventType = event;
		this.enteredPoint = enteredPoint;
	}
	
	/**
	 * An Event constructor that relies on the player's information to determine if the event is fulfilled. 
	 * 
	 * @param player The player character
	 * @return Whether or not the Event's conditions are met
	 */
	public boolean eventMetEh(Player player)	{
		if (eventType == TEVENT.CHARACTER_ENTERS_TILE)	{
			if (player.getCoordX() == enteredPoint.x && player.getCoordY() == enteredPoint.y)	{
				return true;
			}
			for (int i = 0; i < player.getMap().getNPCs().size(); i++)	{
				if (player.getMap().getNPCs().get(i).getCoordX() == enteredPoint.x && player.getMap().getNPCs().get(i).getCoordY() == enteredPoint.y)	{
					return true;
				}	
			}
		}
		return false;
	}
	
}
