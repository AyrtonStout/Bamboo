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
	private TextBox dialogueBox = new TextBox();
	
	private Map currentMap;
	private Map map1;
	private Map map2;
	private Map map3;

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
			map3 = (Map) stream.readObject();
		} catch (Exception e) {
			System.err.println("Something went horribly wrong grabbing the map!");
			e.printStackTrace();
		}
		
		//TODO Figure out why I have to do this in GameData and not MapWriter
		map1.getDoors().get(0).setParentMap(map1);
		map1.getDoors().get(1).setParentMap(map1);
		map1.getDoors().get(2).setParentMap(map1);
		map2.getDoors().get(0).setParentMap(map2);
		map3.getDoors().get(0).setParentMap(map3);
		map3.getDoors().get(1).setParentMap(map3);
		
		map1.getNPCs().get(0).setMap(map1);
		
		currentMap = map1;
		player.setMap(currentMap);
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
	}
	
	
	@SuppressWarnings("incomplete-switch")
	public void activate()	{
		if (gameState == 0)	{
			Tile facedTile = null;
			int facedX = player.getCoordX();
			int facedY = player.getCoordY();
			switch (player.getFacing())	{
			case LEFT:
				facedTile = currentMap.getArray()[player.getCoordX() - 1][player.getCoordY()];
				facedX--; break;
			case UP:
				facedTile = currentMap.getArray()[player.getCoordX()][player.getCoordY() - 1]; 
				facedY--; break;
			case RIGHT:
				facedTile = currentMap.getArray()[player.getCoordX() + 1][player.getCoordY()]; 
				facedX++; break;
			case DOWN:
				facedTile = currentMap.getArray()[player.getCoordX()][player.getCoordY() + 1]; 
				facedY++; break;
			}
			if (facedTile.getDoodad() != null)	{
				if(facedTile.getDoodad().getClass() == Interactable.class)	{
					((Interactable) facedTile.getDoodad()).interact();
				}
				else if (facedTile.getDoodad().getClass() == Sign.class)	{
					((Sign) facedTile.getDoodad()).getDialogue();
					gameState = 1;
					dialogueBox.setVisible(true);
					dialogueBox.setDialogue(((Sign) facedTile.getDoodad()).getDialogue());
					advanceDialogue();
				}
			}
			for (int i = 0; i < currentMap.getNPCs().size(); i++)	{
				if (currentMap.getNPCs().get(i).getCoordX() == facedX && currentMap.getNPCs().get(i).getCoordY() == facedY)	{
					dialogueBox.setDialogue(currentMap.getNPCs().get(i).getDialogue());
					currentMap.getNPCs().get(i).setFacing(player.getFacing());
					gameState = 1;
					dialogueBox.setVisible(true);
					advanceDialogue();
				}
			}
		}
	}
	
	public void advanceDialogue()	{
		if (dialogueBox.writingEh() && !dialogueBox.writeFasterEh())	{
			dialogueBox.writeFaster();
		}
		else if (!dialogueBox.writingEh())	{
			if (dialogueBox.hasNextLine())	{
				dialogueBox.read();
			}
			else	{
				gameState = 0;
				dialogueBox.setVisible(false);
			}
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
	/**
	 * @return The game's bottom dialog box used for dialogue
	 */
	public TextBox getTextBox() {
		return dialogueBox;
	}
}
