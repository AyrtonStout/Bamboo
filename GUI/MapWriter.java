package GUI;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import GUI.Enums.DECORATION;
import GUI.Enums.DOOR;
import GUI.Enums.INTERACTABLE;
import GUI.Enums.TILE;

public class MapWriter {

	public static void main(String[] args)	{


		
		Map map1 = test1();
		Map map2 = test2();
		
		map1.getDoors().get(0).setParentMap(map1);
		map1.getDoors().get(0).setParentMap(map2);
		
		map1.getDoors().get(0).setLink(map2.getDoors().get(0));
		map2.getDoors().get(0).setLink(map1.getDoors().get(0));
		
		ObjectOutputStream streamin, streaming;
		try {
			streamin = new ObjectOutputStream(new FileOutputStream ("test1"));
			streamin.writeObject(map1);
			streamin.close();
			streaming = new ObjectOutputStream(new FileOutputStream ("test2"));
			streaming.writeObject(map2);
			streaming.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public static Map test2()	{
		Tile groundTile = new Tile(TILE.GROUND_CAVE);
		Tile[][] tiles = new Tile [15][14];
		for (int i = 0; i < tiles.length; i++)	{
			for (int j = 0; j < tiles[i].length; j++)	{
				tiles[i][j] = groundTile;
			}
		}
		tiles[7][7] = new Tile(TILE.GROUND_CAVE, INTERACTABLE.GROUND_TREASURE_CHEST);
		tiles[7][3] = new Tile(TILE.GROUND_CAVE, DOOR.WALL_CAVE_DOOR, 7, 3);
		
		Map map = new Map(tiles);
		return map;
	}

	public static Map test1()	{
		Tile[][] tiles = new Tile[22][18];
		Tile grassTile = new Tile(TILE.GROUND_GRASS);
		Tile waterTile = new Tile(TILE.GROUND_WATER);
		Tile treeTile = new Tile(TILE.GROUND_GRASS, DECORATION.TREE_PALM);
		
		for (int i = 0; i < tiles.length; i++)	{
			for (int j = 0; j < tiles[i].length; j++)	{
				tiles[i][j] = grassTile;
			}
		}
		tiles[6][7] = waterTile;
		tiles[6][8] = waterTile;
		tiles[6][9] = waterTile;
		tiles[5][8] = waterTile;
		tiles[5][9] = waterTile;
		tiles[7][7] = waterTile;
		tiles[7][8] = waterTile;
		tiles[8][7] = waterTile;
		tiles[15][7] = waterTile;
		tiles[21][7] = waterTile;
		tiles[21][6] = waterTile;
		tiles[21][5] = waterTile;
		tiles[14][12] = waterTile;
		tiles[13][17] = waterTile;
		tiles[12][17] = waterTile;
		tiles[14][17] = waterTile;
		tiles[7][9] = treeTile;
		tiles[10][8] = treeTile;
		tiles[11][8] = treeTile;
		tiles[9][12] = new Tile(TILE.GROUND_GRASS, INTERACTABLE.GROUND_TREASURE_CHEST);
		tiles[13][12] = new Tile(TILE.GROUND_GRASS, DECORATION.TREE_PALM);
		
		for (int i = 0; i < tiles.length; i++)	{
			tiles[i][0] = new Tile(TILE.WALL_CAVE);
			tiles[i][1] = new Tile(TILE.WALL_CAVE);
		}
		tiles[12][1] = new Tile(TILE.WALL_CAVE, DOOR.WALL_CAVE_DOOR, 12, 1);
		
		Map map = new Map(tiles);
		
		return map;
	}
	
}
