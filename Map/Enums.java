package Map;

public class Enums {
	
	public enum ACTION {

		LEFT, RIGHT, UP, DOWN, STAND, WANDER, ROTATE, RANDOM, PATROL;
		
	}
	
	public enum TILE {

		GROUND_GRASS, GROUND_WATER, GROUND_ROAD, WALL_CAVE, GROUND_CAVE;
		
	}
	
	public enum DECORATION {

		TREE_PALM, TREE_PINE, WATER_ROCKS, GRASS_FLOWERS, OPACITY_OVERLAY;
		
	}

	public enum TREASURE_CHEST {

		TREASURE_CHEST_SMALL, TREASURE_CHEST_BIG;
		
	}
	
	public enum DOOR {
		
		WALL_CAVE_DOOR, TRANSITION_LEFT, TRANSITION_RIGHT;
		
	}
	
	public enum SIGN {
		
		WOOD;
		
	}
	
	
	public enum GENERIC_NPC	{
		
		
	}
	
	public enum NAMED_NPC	{
		
		SABIN, TERRA, CELES, LOCKE, CYAN, GHOST;
		
	}
	

}