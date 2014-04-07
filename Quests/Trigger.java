package Quests;

import java.io.Serializable;
import java.util.ArrayList;

import Map.Map;
import Systems.GameData;

/**
 * @author mobius
 * A modular way of causing actions to take place in the game world. A trigger is composed of events and actions. When all 
 * events are met, all actions of a trigger fire. A trigger can be one time use or repeated.
 */
public class Trigger implements Serializable {
	
	private static final long serialVersionUID = -7516675186987091933L;
	
	private GameData data;
	private Map triggersMap;
	
	private boolean activated = false;
	private boolean repeatedTrigger = false;
	
	private ArrayList<TEvent> events = new ArrayList<TEvent>();
	private ArrayList<TAction> actions = new ArrayList<TAction>();
	
	/**
	 * Base constructor for a trigger. Additional events and actions can be added to a trigger later.
	 * 
	 * @param e The event that must be met
	 * @param a The action that results from the trigger
	 */
	public Trigger(TEvent e, TAction a)	{
		events.add(e);
		actions.add(a);
	}

	/**
	 * Activates all of the trigger's actions
	 */
	public void fire()	{
		if (conditionsMet())	{
			for (int i = 0; i < actions.size(); i++)	{
				actions.get(i).doAction(triggersMap, data);
			}
		}
	}
	
	/**
	 * @return Whether or not all of the trigger's events are met
	 */
	private boolean conditionsMet()	{
		if (activated && !repeatedTrigger)	{
			return false;
		}
		for (int i = 0; i < events.size(); i++)	{
			if(!events.get(i).eventMetEh(data))	{
				return false;
			}
		}
		activated = true;
		return true;
	}
	
	public void addEvent(TEvent e)	{
		events.add(e);
	}
	
	public void addAction(TAction a)	{
		actions.add(a);
	}
	
	/**
	 * Initializes the trigger's information when the game starts
	 * 
	 * @param map The map the trigger is bound to
	 * @param player The player character
	 */
	public void initialize(Map map, GameData data) {
		this.triggersMap = map;
		this.data = data;
	}

}
