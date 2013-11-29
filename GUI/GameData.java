package GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * @author mobius
 * Holds the game data and updates the state of all objects every frame
 */
public class GameData {
	
	Character player;
	private int gameState = 0;
	Map[] worldMaps = new Map[2];
	int windowWidth, windowHeight;
	
	private Map currentMap;
	private Map map1;
	private Map map2;

	ObjectInputStream stream;
	
	/**
	 * @param windowWidth Width of the game window
	 * @param windowHeight Height of the game window
	 * 
	 * Loads all game maps from files and sets the current map
	 */
	public GameData(int windowWidth, int windowHeight)	{
		player = new Character(windowWidth, windowHeight);
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		try {
			stream = new ObjectInputStream( new FileInputStream(new File("GUI/Maps/test1")));
			map1 = (Map) stream.readObject();
			map2 = (Map) stream.readObject();
		} catch (Exception e) {
			System.err.println("Something went horribly wrong grabbing the map!");
			e.printStackTrace();
		}
		
		//TODO Figure out why I have to do this in GameData and not MapWriter
		map1.getDoors().get(0).setParentMap(map1);
		map2.getDoors().get(0).setParentMap(map2);
	
		currentMap = map1;
		player.setMap(currentMap);
	}

	
	/**
	 * Updates all the game elements in the current map
	 */
	public void update()	{
		player.update();
		
		//TODO Relocate this logic into the Character class and tie it into player.update();
		if (player.transitionEh())	{
			Door enteredDoor = currentMap.findDoor(player.getCoordX(), player.getCoordY());
			currentMap = enteredDoor.getLink().getParentMap();
			player.setMap(currentMap);
			if (enteredDoor.getLink().getX() * 40 - (windowWidth / 2) < 0)	{
				player.setBackgroundX(0);
			}
			else	{
				player.setBackgroundX(enteredDoor.getLink().getX() * 40 - (windowWidth / 2));
			}
			if (enteredDoor.getLink().getY() * 40 - (windowHeight / 2) < 0)	{
				player.setBackgroundY(0);
			}
			else	{
				player.setBackgroundY(enteredDoor.getLink().getY() * 40 - (windowHeight / 2));
			}
			player.setCoordX(enteredDoor.getLink().getX());
			player.setCoordY(enteredDoor.getLink().getY());
		}
	}
	
	/**
	 * @return Returns the player character
	 */
	public Character getPlayer()	{
		return player;
	}
	/**
	 * @return The game's current state
	 */
	public int getGameState()	{
		return gameState;
	}
	/**
	 * @return The map being played on
	 */
	public Map getCurrentMap()	{
		return currentMap;
	}
}
