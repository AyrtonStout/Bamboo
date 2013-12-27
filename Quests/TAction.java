package Quests;

import java.io.Serializable;

import GUI.GameData;
import GUI.Map;
import GUI.NPC;
import Quests.Enums.TACTION;
import Systems.PartyMember;

/**
 * @author mobius
 * The result of a trigger firing
 */
public class TAction implements Serializable {
	
	private static final long serialVersionUID = 6316107685438344591L;
	
	private TACTION consequence;
	private NPC spawnedNPC;
	private PartyMember member;
	
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
	
	public TAction(TACTION c, PartyMember member)	{
		this.consequence = c;
		this.member = member;
	}
	
	/**
	 * An action performed by a trigger that affects the playable map area
	 * 
	 * @param map The affected map
	 */
	public void doAction(Map triggersMap, GameData data)	{
		if (consequence == TACTION.SPAWN_NPC)	{
			triggersMap.addNPC(spawnedNPC);
		}
		else if (consequence == TACTION.ADD_NPC_TO_PARTY)	{
			for (int i = 0; i < data.getParty().length; i++)	{
				if (data.getParty()[i] == null)	{
					data.getParty()[i] = member;
					PartyMember.incrementPartySize();
					break;
				}
			}
		}
	}
	
}
