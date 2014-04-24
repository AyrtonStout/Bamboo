package BattleScreen;

public class Enums {
	
	public enum COMBAT_ACTION	{
		
		IDLE, ATTACK, IMPACT, ITEM;
		
	}
	
	public enum COMBAT_START	{
		
		PREEMPTIVE, NORMAL, AMBUSH;
	
	}
	
	public enum BATTLE_STATE	{
		
		MAIN, SELECT, WAIT, ENEMY_MOVE, ATTACK_SELECTION, END, ANIMATION, ANIM_RECOIL, ITEM_SELECTION, ITEM_USE_SELECTION;
	
	}
	
	public enum TEXT_TYPE	{
		
		DAMAGE, CRIT, MISS, HEAL, MANA_RESTORE;
		
	}

}
