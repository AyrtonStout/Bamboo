package Quests;

import java.io.Serializable;
import java.util.ArrayList;

import GUI.Map;
import GUI.Player;

public class Trigger implements Serializable {
	
	private static final long serialVersionUID = -7516675186987091933L;
	
	private Map map;
	private Player player;
	
	private boolean activated = false;
	private boolean repeatedTrigger = false;
	
	private ArrayList<TEvent> events = new ArrayList<TEvent>();
	private ArrayList<TCondition> conditions = new ArrayList<TCondition>();
	private ArrayList<TAction> actions = new ArrayList<TAction>();
	
	public Trigger(TEvent e, TAction a)	{
		events.add(e);
		actions.add(a);
	}

	public void fire()	{
		if (conditionsMet())	{
			for (int i = 0; i < actions.size(); i++)	{
				actions.get(i).doAction(map);
			}
		}
	}
	
	private boolean conditionsMet()	{
		if (activated && !repeatedTrigger)	{
			return false;
		}
		for (int i = 0; i < events.size(); i++)	{
			if(!events.get(i).eventMetEh(map, player))	{
				return false;
			}
		}
		activated = true;
		return true;
	}
	
	public void initialize(Map map, Player player) {
		this.map = map;
		this.player = player;
	}

}
