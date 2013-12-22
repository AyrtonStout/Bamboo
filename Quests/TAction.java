package Quests;

import java.io.Serializable;

import GUI.Map;
import GUI.NPC;
import Quests.Enums.TACTION;

/**
 * @author mobius
 * The result of a trigger firing
 */
public class TAction implements Serializable {
	
	private static final long serialVersionUID = 6316107685438344591L;
	
	private TACTION consequence;
	private NPC spawnedNPC;
	
	
	/**
	 * An action that modifies or creates an NPC
	 * 
	 * @param c The type of action being performed
	 * @param npc The npc being affected by the action
	 */
	public TAction(TACTION c, NPC npc)	{
		this.consequence = c;
		this.spawnedNPC = npc;
	}
	
	/**
	 * An action performed by a trigger that affects the playable map area
	 * 
	 * @param map The affected map
	 */
	public void doAction(Map map)	{
		if (consequence == TACTION.SPAWN_NPC)	{
			map.addNPC(spawnedNPC);
		}
	}
	
}
