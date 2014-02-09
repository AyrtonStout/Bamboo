package Systems;

public class Enums {
	
	public enum TIME	{
		
		MORNING, DAY, NIGHT;
	
	}
	
	public enum COMBAT_ACTION	{
		
		IDLE, ATTACK;
		
	}
	
	/*
	 * Weapon enums
	 */
	public enum WEAPON_TYPE	{
		
		SWORD, AXE, DAGGER, MACE, STAFF;
		
	}
	
	public enum SWORD	{
		
		IRON;
		
	}
	
	public enum DAGGER	{
		
		IRON, GOLD;
				
	}
	
	public enum MACE	{
		
		WOOD;
		
	}
	
	public enum AXE		{
		
		IRON;
		
	}
	
	/*
	 * Armor enums
	 */
	public enum ARMOR_SLOT	{
		
		HELMET, CHEST, GLOVES, BOOTS;
		
	}
	
	public enum HELMET	{
		
		IRON;		
		
	}
	
	public enum CHEST	{

		IRON;
		
	}
	public enum GLOVES	{

		IRON;
		
	}
	public enum BOOTS	{

		IRON;
		
	}
	
	
	public enum ACCESSORY_TYPE	{
		
		RING, NECKLACE, TRINKET;
		
	}

	public enum CONSUMABLE_TYPE	{
		
		POTION, FOOD, WATER, POISON;
		
	}
	
	public enum POTION	{
		
		HEALTH_SMALL, HEALTH_LARGE, MANA_SMALL, MANA_LARGE, YELLOW_SMALL, YELLOW_LARGE;
		
	}
	
	public enum MONSTER	{
		
		GIANT_RAT, RAZORCLAW_CRAB, DEATHSTALKER_CROW;
		
	}
	

	
}
