package GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import GUI.Enums.DECORATION;
import GUI.Enums.DOOR;
import GUI.Enums.INTERACTABLE;
import GUI.Enums.TILE;

public class GameData {
	
	Character player;
	private int gameState = 0;
	Map[] worldMaps = new Map[2];
	int windowWidth, windowHeight;
	
	private Map currentMap;
	private Map map1;
	private Map map2;
	
	ObjectInputStream stream;
	
	public GameData(int windowWidth, int windowHeight)	{
		player = new Character(windowWidth, windowHeight);
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		try {
			map1 = (Map) new ObjectInputStream( new FileInputStream(new File("test1"))).readObject();
			map2 = (Map) new ObjectInputStream( new FileInputStream(new File("test2"))).readObject();
		} catch (Exception e) {
			System.err.println("Something went horribly wrong grabbing the map!");
			e.printStackTrace();
		}
		currentMap = map1;
		player.setMap(currentMap);
	}

	public void update()	{
		player.update();
		if (player.transitionEh())	{
			if (currentMap == map1)	{
//				currentMap = ((Door) currentMap.tiles[player.getCoordX()][player.getCoordY()].getDoodad()).getParentMap();
				
				currentMap = map2;
				player.setMap(map2);
			}
			else if (currentMap == map2)	{
				currentMap = map1;
				player.setMap(map1);
			}
		}
//		currentMap = player.getMap();
	}
	
	public Character getPlayer()	{
		return player;
	}
	public int getGameState()	{
		return gameState;
	}
	public Map getCurrentMap()	{
		return currentMap;
	}
}
