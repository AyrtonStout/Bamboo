package GUI;

import java.io.Serializable;

import javax.swing.ImageIcon;

import GUI.Enums.*;

public class Door extends Doodad implements Serializable {

	private static final long serialVersionUID = -4451983441299961170L;
	
	private int x;
	private int y;
	private Door link;
	private Map parentMap;
	
	public Door(DOOR type, int x, int y)	{
		this.x = x;
		this.y = y;
		if (type == DOOR.WALL_CAVE_DOOR)	{
			background = new ImageIcon("GUI/Resources/Tile_CaveDoor.png");
			moveBlock = false;
			verticalOffset = 0;
			horizontalOffset = 0;
		}
	}
	
	public void setLink(Door door)	{
		link = door;
	}
	public Door getLink()	{
		return link;
	}
	public void setParentMap(Map map)	{
		parentMap = map;
	}

	public Map getParentMap() {
		return parentMap;
	}
	
}
