package GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * @author mobius
 * Holds the game data and updates the state of all objects every frame
 */
public class GameData {
	
	private Player player;
	private int gameState = 0;
	private ArrayList<Map> worldMaps = new ArrayList<Map>();
	private int windowWidth, windowHeight;
	private TextBox dialogueBox = new TextBox();
	
	private Map currentMap;

	private ObjectInputStream stream;
	
	/**
	 * @param windowWidth Width of the game window
	 * @param windowHeight Height of the game window
	 * 
	 * Loads all game maps from files and sets the current map
	 */
	public GameData(int windowWidth, int windowHeight)	{
		player = new Player("Sabin", windowWidth, windowHeight);
		player.initializeImages();
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		try {
			stream = new ObjectInputStream( new FileInputStream(new File("GUI/Maps/test1")));
			for (int i = 0; i < 3; i++)	{
				worldMaps.add((Map) stream.readObject());
				worldMaps.get(i).initializeMap(worldMaps.get(i));
			}
			stream.close();
		} catch (Exception e) {
			System.err.println("Something went horribly wrong grabbing the map!");
			e.printStackTrace();
		}
		
		currentMap = worldMaps.get(0);
		player.setMap(currentMap);
		currentMap.getMoveblocks()[player.getCoordX()][player.getCoordY()] = true;
	}

	
	/**
	 * King of all methods
	 * Updates all the game elements in the current map
	 */
	public void update()	{
		player.update();
		if (player.doorTransitionEh())	{
			currentMap = player.getMap();
		}
		if (dialogueBox.writingEh())	{
			dialogueBox.update();	
		}
		for (int i = 0; i < currentMap.getNPCs().size(); i++)	{
			currentMap.getNPCs().get(i).update();
		}
		
//		System.out.println("X - " + currentMap.getNPCs().get(0).getCoordX() + "  Y - " + currentMap.getNPCs().get(0).getCoordY());
	}

	/**
	 * @return Returns the player character
	 */
	public Player getPlayer()	{
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
	/**
	 * @return The game's bottom dialog box used for dialogue
	 */
	public TextBox getTextBox() {
		return dialogueBox;
	}
	/**
	 * @return The width of the game's window
	 */
	public int getWindowWidth()	{
		return windowWidth;
	}
	/**
	 * @return The height of the game's window
	 */
	public int getWindowHeight()	{
		return windowHeight;
	}
	/**
	 * @param i The new state of the game
	 */
	public void setGameState(int i) {
		gameState = i;
	}
}
