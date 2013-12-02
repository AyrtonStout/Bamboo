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
	private TextBox dialogBox = new TextBox();
	
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
			if (enteredDoor.getLink().getX() * 40 + 20 - (windowWidth / 2) < 0)	{
				player.setBackgroundX(0);
			}
			else if (enteredDoor.getLink().getX() * 40 + 20 - (windowWidth / 2) > currentMap.getWidth() * 40 - windowWidth)	{
				player.setBackgroundX(currentMap.getWidth() * 40 - windowWidth);
			}
			else	{
				player.setBackgroundX(enteredDoor.getLink().getX() * 40 + 20 - (windowWidth / 2));
			}
			if (enteredDoor.getLink().getY() * 40 + 20 - (windowHeight / 2) < 0)	{
				player.setBackgroundY(0);
			}
			else if (enteredDoor.getLink().getY() * 40 + 20 - (windowHeight / 2) > currentMap.getHeight() * 40 - windowHeight)	{
				player.setBackgroundY(currentMap.getHeight() * 40 - windowHeight);
			}
			else	{
				player.setBackgroundY(enteredDoor.getLink().getY() * 40 + 20 - (windowHeight / 2));
			}
			player.setCoordX(enteredDoor.getLink().getX());
			player.setCoordY(enteredDoor.getLink().getY());
		}
		
		if (dialogBox.writingEh())	{
			dialogBox.update();	
		}
	}
	
	
	@SuppressWarnings("incomplete-switch")
	public void activate()	{
		if (gameState == 0)	{
			Tile facedTile = null;
			switch (player.getFacing())	{
			case LEFT:
				facedTile = currentMap.getArray()[player.getCoordX() - 1][player.getCoordY()]; break;
			case UP:
				facedTile = currentMap.getArray()[player.getCoordX()][player.getCoordY() - 1]; break;
			case RIGHT:
				facedTile = currentMap.getArray()[player.getCoordX() + 1][player.getCoordY()]; break;
			case DOWN:
				facedTile = currentMap.getArray()[player.getCoordX()][player.getCoordY() + 1]; break;
			}
			if (facedTile.getDoodad() != null)	{
				if(facedTile.getDoodad().getClass() == Interactable.class)	{
					((Interactable) facedTile.getDoodad()).interact();
				}
				else if (facedTile.getDoodad().getClass() == Sign.class)	{
					((Sign) facedTile.getDoodad()).getDialogue();
					gameState = 1;
					dialogBox.setVisible(true);
					dialogBox.setDialogue(((Sign) facedTile.getDoodad()).getDialogue());
					dialogBox.setWriting(true);
					advanceDialogue();
				}
			}
		}
	}
	
	public void advanceDialogue()	{
		if (dialogBox.hasNextLine())	{
			dialogBox.read();
		}
		else	{
			gameState = 0;
			dialogBox.setVisible(false);
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


	public TextBox getTextBox() {
		return dialogBox;
	}
}
