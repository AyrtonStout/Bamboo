package Quests;

import java.io.Serializable;

import GUI.Map;
import GUI.NPC;
import Quests.Enums.TACTION;

public class TAction implements Serializable {
	
	private static final long serialVersionUID = 6316107685438344591L;
	
	private TACTION consequence;
	private NPC spawnedNPC;
	
	
	
	public TAction(TACTION c, NPC npc)	{
		this.consequence = c;
		this.spawnedNPC = npc;
	}
	
	public void doAction(Map map)	{
		if (consequence == TACTION.SPAWN_NPC)	{
			map.addNPC(spawnedNPC);
		}
	}


	
}
