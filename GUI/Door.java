package GUI;

import javax.swing.ImageIcon;

public class Door extends Doodad {

	public Door(DOOR_TYPE type)	{
		if (type == DOOR_TYPE.WALL_CAVE_DOOR)	{
			background = new ImageIcon("GUI/Resources/Tile_CaveDoor.png").getImage();
			moveBlock = false;
			verticalOffset = 0;
			horizontalOffset = 0;
		}
	}
	
}
