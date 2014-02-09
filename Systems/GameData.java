package Systems;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import GUI.BattleScreen;
import GUI.Board;
import GUI.DialogueBox;
import GUI.InventoryPanel;
import GUI.Map;
import GUI.Menu;
import GUI.PartyPanel;
import GUI.PlayerAvatar;
import GUI.Enums.GAME_STATE;
import GUI.Enums.NAMED_NPC;
import GUI.StatisticsPanel;

/**
 * @author mobius
 * Holds the game data and updates the state of all objects every frame
 */
public class GameData {
	
	private PlayerAvatar player;
	private PartyMember[] party = new PartyMember[4];
	private ArrayList<PartyMember> playableCharacters = new ArrayList<PartyMember>();
	private GAME_STATE gameState = GAME_STATE.WALK;
	private ArrayList<Map> worldMaps = new ArrayList<Map>();
	private int windowWidth, windowHeight;
	private DialogueBox dialogueBox = new DialogueBox(this);
	private StatisticsPanel statsPanel = new StatisticsPanel(this);
	private Board gameBoard;
	private Menu menuBox = new Menu(this);
	private Inventory inventory = new Inventory(this);
	private InventoryPanel inventoryPanel = new InventoryPanel(this, inventory);
	private PartyPanel partyPanel = new PartyPanel(this);
	private BattleScreen battleScreen = new BattleScreen(this);
	private Combat combat = new Combat(this);
	private Time time = new Time();
	private boolean paused = false;
	private InputManager input;

	
	private Map currentMap;

	private ObjectInputStream stream;
	
	/**
	 * @param windowWidth Width of the game window
	 * @param windowHeight Height of the game window
	 * 
	 * Loads all game maps from files and sets the current map
	 */
	public GameData(int windowWidth, int windowHeight)	{
		player = new PlayerAvatar("Sabin", windowWidth, windowHeight, this);
		player.initializeImages();
		party[0] = new PartyMember(NAMED_NPC.SABIN);
		party[0].initialize(this);
		playableCharacters.add(party[0]);
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		try {
			stream = new ObjectInputStream( new FileInputStream(new File("GUI/Maps/test1")));
			for (int i = 0; i < 3; i++)	{
				worldMaps.add((Map) stream.readObject());
				worldMaps.get(i).initializeMap(worldMaps.get(i), this);
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
		if (gameState == GAME_STATE.BATTLE)	{
			battleScreen.update();
		}
		else if (!paused)	{
			player.update();
			if (player.doorTransitionEh())	{
				currentMap = player.getMap();
			}
			if (dialogueBox.writingEh())	{
				dialogueBox.update();	
			}
			time.update();
			if (gameState == GAME_STATE.MENU)	{
				menuBox.update();
			}
			currentMap.updateAll();
		}
		
		/*
		 * TODO Figure out why I need this
		 * If this statement is left out, the game will lose focus and become unresponsive
		 * after clicking away from the game and back in under certain circumstances
		 */
		else	{
			if (!input.isFocusOwner())	{
				input.requestFocusInWindow();
			}
		}
//		System.out.println(input.isFocusOwner());
	}

	/**
	 * @return Returns the player character
	 */
	public PlayerAvatar getPlayer()	{
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
	public DialogueBox getDialogueBox() {
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
	/**
	 * @return The game's Time object
	 */
	public Time getTime()	{
		return time;
	}
	/**
	 * Tells the GameData class where the GameBoard is. This is only called once and only in the Frame class
	 * 
	 * @param gameBoard The game's one and only GameBoard
	 */
	public void setGameBoard(Board gameBoard) {
		this.gameBoard = gameBoard;	
	}
	/**
	 * @return The game's GameBoard
	 */
	public Board getGameBoard()	{
		return gameBoard;
	}
	/**
	 * Sets the game as either paused or unpaused. The game should be paused when entering a full screen menu but
	 * should remain unpaused otherwise
	 * 
	 * @param b Whether or not the game should be paused
	 */
	public void setPaused(boolean b)	{
		paused = b;
	}
	/**
	 * @return The inventory screen
	 */
	public InventoryPanel getInventoryPanel()	{
		return inventoryPanel;
	}
	/**
	 * @return The actual collection of items in the party's inventory
	 */
	public Inventory getInventory()	{
		return inventory;
	}
	/**
	 * @return The party screen
	 */
	public PartyPanel getPartyPanel()	{
		return partyPanel;
	}
	/**
	 * @return An array of all of the party's current party members
	 */
	public PartyMember[] getParty()	{
		return party;
	}

	
	

	/**
	 * Shows the GameData where the InputManager is. This method is currently only
	 * being used to fix a big where focus of the InputManager is lost when clicking
	 * away from the program under unusual conditions. If that problem is fixed, this
	 * method should be removed.
	 * 
	 * @param input The game's InputManager
	 */
	public void setInputManager(InputManager input) {
		this.input = input;
	}


	public StatisticsPanel getStatisticsPanel() {
		return statsPanel;
	}

	public ArrayList<PartyMember> getPlayableCharacters()	{
		return playableCharacters;
	}
	
	public BattleScreen getBattleScreen()	{
		return battleScreen;
	}
	
	public Combat getCombat()	{
		return combat;
	}
	
}
