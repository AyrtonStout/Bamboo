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
			NPC interactedNPC;
			for (int i = 0; i < currentMap.getNPCs().size(); i++)	{
				interactedNPC = currentMap.getNPCs().get(i);
				if (interactedNPC.getCoordX() == facedX && interactedNPC.getCoordY() == facedY && !interactedNPC.movingEh())	{
					dialogueBox.setDialogue(currentMap.getNPCs().get(i).getDialogue());
					interactedNPC.invertFacing(player.getFacing());
					interactedNPC.setTalking(true);
					player.setInteractingNPC(interactedNPC);
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
				player.getInteractingNPC().setTalking(false);
				dialogueBox.setVisible(false);
			}
		}
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
}
