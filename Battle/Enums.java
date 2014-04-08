package Battle;

public class Enums {
	
	public enum COMBAT_ACTION	{
		
		IDLE, ATTACK, IMPACT;
		
	}
	
	public enum COMBAT_START	{
		
		PREEMPTIVE, NORMAL, AMBUSH;
	
	}
	
	public enum BATTLE_STATE	{
		
		MAIN, SELECT, WAIT, ENEMY_MOVE, ATTACK_SELECTION, END, ANIM_ATTACK, ANIM_RECOIL, ITEM_SELECTION;
	
	}

}
