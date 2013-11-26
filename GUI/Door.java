package GUI;

import javax.swing.ImageIcon;

import GUI.Enums.*;

public class Door extends Doodad {

	public Door(DOOR type)	{
		if (type == DOOR.WALL_CAVE_DOOR)	{
			background = new ImageIcon("GUI/Resources/Tile_CaveDoor.png").getImage();
			moveBlock = false;
			verticalOffset = 0;
			horizontalOffset = 0;
		}
	}
	
}
