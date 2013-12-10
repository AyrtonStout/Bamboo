package GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import GUI.Enums.GAME_STATE;
import Systems.Time;

/**
 * @author mobius
 * Holds the game data and updates the state of all objects every frame
 */
public class GameData {
	
	private Player player;
	private GAME_STATE gameState = GAME_STATE.WALK;
	private ArrayList<Map> worldMaps = new ArrayList<Map>();
	private int windowWidth, windowHeight;
	private TextBox dialogueBox = new TextBox();
	private Board gameBoard;
	private Menu menuBox = new Menu(this);
	private Time time = new Time();
	
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
				worldMaps.get(i).initializeMap(worldMaps.get(i), player);
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
		time.update();
		menuBox.update();
		currentMap.updateAll();
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
	public GAME_STATE getGameState()	{
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
	 * @return The general right-hand side game menu
	 */
	public Menu getMenu()	{
		return menuBox;
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
	public void setGameState(GAME_STATE state) {
		gameState = state;
	}
	public Time getTime()	{
		return time;
	}


	public void setGameBoard(Board gameBoard) {
		this.gameBoard = gameBoard;	
	}
	public Board getGameBoard()	{
		return gameBoard;
	}
}
