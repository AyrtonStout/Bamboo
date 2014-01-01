package Quests;

import java.awt.Point;
import java.io.Serializable;

import GUI.NPC;
import Quests.Enums.TEVENT;
import Systems.GameData;

/**
 * @author mobius
 * Part of a triggerable sequence that controls when the trigger is ready to fire. An event is a fancy
 * boolean that determines when a trigger is ready to fire.
 */
public class TEvent implements Serializable {
	
	private static final long serialVersionUID = -3505855084423834681L;

	private TEVENT eventType;
	
	private Point enteredPoint;
	private NPC interactedNPC;
	
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
	public TEvent(TEVENT event, NPC interactedNPC)	{
		this.eventType = event;
		this.interactedNPC = interactedNPC;
	}
	
	/**
	 * An Event constructor that relies on the player's information to determine if the event is fulfilled. 
	 * 
	 * @param player The player character
	 * @return Whether or not the Event's conditions are met
	 */
	public boolean eventMetEh(GameData data)	{
		if (eventType == TEVENT.CHARACTER_ENTERS_TILE)	{
			if (data.getPlayer().getCoordX() == enteredPoint.x && data.getPlayer().getCoordY() == enteredPoint.y)	{
				return true;
			}
			for (int i = 0; i < data.getCurrentMap().getNPCs().size(); i++)	{
				if (data.getCurrentMap().getNPCs().get(i).getCoordX() == enteredPoint.x && data.getCurrentMap().getNPCs().get(i).getCoordY() == enteredPoint.y)	{
					return true;
				}	
			}
		}
		else if (eventType == TEVENT.CHARACTER_FINISHES_TALKING)	{
			if (interactedNPC.talkedToEh())	{
				return true;
			}
		}
		return false;
	}
	
}
