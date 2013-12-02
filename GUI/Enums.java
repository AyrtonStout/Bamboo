package GUI;

public class Enums {
	
	public enum ACTION {

		LEFT, RIGHT, UP, DOWN, STAND;
		
	}
	
	public enum TILE {

		GROUND_GRASS, GROUND_WATER, GROUND_ROAD, WALL_CAVE, GROUND_CAVE;
		
	}
	
	public enum DECORATION {

		TREE_PALM, TREE_PINE, WATER_ROCKS, GRASS_FLOWERS, OPACITY_OVERLAY;
		
	}

	public enum INTERACTABLE {

		TREASURE_CHEST;
		
	}
	
	public enum DOOR {
		
		WALL_CAVE_DOOR, TRANSITION_LEFT, TRANSITION_RIGHT;
	}
	
	public enum SIGN {
		WOOD;
	}

}
