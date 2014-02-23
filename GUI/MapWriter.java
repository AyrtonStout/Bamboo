package GUI;

import java.awt.Point;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import GUI.Enums.*;
import Quests.TAction;
import Quests.TEvent;
import Quests.Trigger;
import Quests.Enums.*;
import Systems.Consumable;
import Systems.Enums.MACE;
import Systems.Enums.MONSTER;
import Systems.Enums.POTION;
import Systems.Enums.SWORD;
import Systems.Enums.DAGGER;
import Systems.PartyMember;
import Systems.SpawnGenerator;
import Systems.Weapon;

/**
 * @author mobius
 * Crude way of writing map files off to objects to load later
 */
public class MapWriter {
	
	public static void main(String[] args)	{

		
		
		Map map1 = test1();
		Map map2 = test2();
		Map map3 = test3();
	
		//Links the doors of the cave and outside world together
		map1.getDoors().get(0).setLink(map2.getDoors().get(0));
		map2.getDoors().get(0).setLink(map1.getDoors().get(0));
		
		//Links the transition doors going east/west
		map1.getDoors().get(1).setLink(map3.getDoors().get(0));
		map1.getDoors().get(2).setLink(map3.getDoors().get(1));
		map3.getDoors().get(0).setLink(map1.getDoors().get(1));
		map3.getDoors().get(1).setLink(map1.getDoors().get(2));
		
		//In theory, tells the door the map that they belong to
		//For some reason this doesn't work and has to be redone in the GameData class
		map1.getDoors().get(0).setParentMap(map1);
		map1.getDoors().get(0).setParentMap(map2);
		
		map1.getNPCs().get(0).setMap(map1);
		
		ObjectOutputStream stream;
		try {
			stream = new ObjectOutputStream(new FileOutputStream ("GUI/Maps/test1"));
			stream.writeObject(map1);
			stream.writeObject(map2);
			stream.writeObject(map3);
			stream.close();
		} catch (Exception e) {
			System.err.println("Can't write! Pen broke!");
			e.printStackTrace();
		}
	}
	
	public static Map test3()	{
		Tile grassTile = new Tile(TILE.GROUND_GRASS);
		Tile waterTile = new Tile(TILE.GROUND_WATER);
		Tile[][] tiles = new Tile [17][27];
		for (int i = 0; i < tiles.length; i++)	{
			for (int j = 0; j < tiles[i].length; j++)	{
				tiles[i][j] = grassTile;
			}
		}
		for (int i = 0; i < 8; i++)	{
			for (int j = 7; j < tiles[i].length; j++)	{
				tiles[i][j] = waterTile;
			}
		}
		for (int i = 11; i < tiles.length; i++)	{
			for (int j = 4; j < 16; j++)	{
				tiles[i][j] = waterTile;
			}
		}
		tiles[0][6] = waterTile;
		tiles[1][6] = waterTile;
		tiles[2][6] = waterTile;
		tiles[3][6] = new Tile(TILE.GROUND_GRASS, DECORATION.TREE_PALM);
		tiles[0][4] = new Tile(TILE.GROUND_GRASS, DOOR.TRANSITION_LEFT, 0, 4);
		tiles[0][5] = new Tile(TILE.GROUND_GRASS, DOOR.TRANSITION_LEFT, 0, 5);
		
		for (int i = 0; i < tiles.length; i++)	{
			for (int j = 0; j < 4; j++)	{
				tiles[i][j] = waterTile;
			}
		}
		
		Chest smallHealthChest = new Chest(TREASURE_CHEST.TREASURE_CHEST_SMALL);
		smallHealthChest.setLoot(new Consumable(POTION.HEALTH_SMALL));
		Chest largeHealthChest = new Chest(TREASURE_CHEST.TREASURE_CHEST_SMALL);
		largeHealthChest.setLoot(new Consumable(POTION.HEALTH_LARGE));
		Chest smallManaChest = new Chest(TREASURE_CHEST.TREASURE_CHEST_SMALL);
		smallManaChest.setLoot(new Consumable(POTION.MANA_SMALL));
		Chest largeManaChest = new Chest(TREASURE_CHEST.TREASURE_CHEST_SMALL);
		largeManaChest.setLoot(new Consumable(POTION.MANA_LARGE));
		Chest smallYellowChest = new Chest(TREASURE_CHEST.TREASURE_CHEST_SMALL);
		smallYellowChest.setLoot(new Consumable(POTION.YELLOW_SMALL));
		Chest largeYellowChest = new Chest(TREASURE_CHEST.TREASURE_CHEST_SMALL);
		largeYellowChest.setLoot(new Consumable(POTION.YELLOW_LARGE));
		
		tiles[11][16] = new Tile(TILE.GROUND_GRASS, smallHealthChest);
		tiles[12][16] = new Tile(TILE.GROUND_GRASS, largeHealthChest);
		tiles[13][16] = new Tile(TILE.GROUND_GRASS, smallManaChest);
		tiles[14][16] = new Tile(TILE.GROUND_GRASS, largeManaChest);
		tiles[15][16] = new Tile(TILE.GROUND_GRASS, smallYellowChest);
		tiles[16][16] = new Tile(TILE.GROUND_GRASS, largeYellowChest);
		
		ArrayList<NPC> NPCs = new ArrayList<NPC>();
		
		ArrayList<String> lockeTalk = new ArrayList<String>();
		lockeTalk.add("Two peanuts were walking down an alley...");
		lockeTalk.add("...One was assaulted.");
		
		ArrayList<String> cyanTalk = new ArrayList<String>();
		cyanTalk.add("I feel dizzy... oh so dizzy....");
		
		NPC locke = new NPC(NAMED_NPC.LOCKE, ACTION.DOWN, ACTION.RANDOM, lockeTalk, 8, 4);
		NPC cyan = new NPC(NAMED_NPC.CYAN, ACTION.DOWN, ACTION.ROTATE, cyanTalk, 8, 10);
		
		NPCs.add(locke);
		NPCs.add(cyan);
		
		Map map = new Map(tiles, NPCs);
		return map;
	}
	
	/**
	 * @return An "indoor cave" style map
	 */
	public static Map test2()	{
		Tile groundTile = new Tile(TILE.GROUND_CAVE);
		Tile treeTile = new Tile(TILE.GROUND_CAVE, DECORATION.TREE_PALM);
		Tile[][] tiles = new Tile [15][15];
		for (int i = 0; i < tiles.length; i++)	{
			for (int j = 0; j < tiles[i].length; j++)	{
				tiles[i][j] = groundTile;
			}
		}
		for (int i = 0; i < tiles.length; i++)	{
			tiles[i][0] = new Tile(TILE.WALL_CAVE);
			tiles[i][1] = new Tile(TILE.WALL_CAVE);
			tiles[i][tiles[0].length-1] = new Tile(TILE.GROUND_WATER);
		} 
		for (int i = 0; i < tiles[0].length; i++)	{
			tiles[0][i] = new Tile(TILE.GROUND_WATER);
			tiles[tiles.length-1][i] = new Tile(TILE.GROUND_WATER);
		}
		
		Chest magicSwordChest = new Chest(TREASURE_CHEST.TREASURE_CHEST_BIG);
		magicSwordChest.setLoot(new Weapon(MACE.WOOD));
		
		tiles[7][7] = new Tile(TILE.GROUND_CAVE, magicSwordChest);
		tiles[7][3] = new Tile(TILE.GROUND_CAVE, DOOR.WALL_CAVE_DOOR, 7, 3);
		
		tiles[4][9] = treeTile;
		tiles[10][9] = treeTile;
		tiles[4][12] = treeTile;
		tiles[10][12] = treeTile;
		
		ArrayList<NPC> NPCs = new ArrayList<NPC>();
		
		ArrayList<String> ghostTalk = new ArrayList<String>();
		ghostTalk.add("Get out of my way you ass.");
		ArrayList<String> ghostTalk2 = new ArrayList<String>();
		ghostTalk2.add("Why can't I escape all of these trees anymore!");
		ghostTalk2.add("And that treasure chest even used to turn into a tree.");
		ghostTalk2.add("Why doesn't anybody believe me? You do don't you?");
		ghostTalk2.add("All I wanted was a Pepsi. Just one Pepsi...");
		
		NPC ghost = new NPC(NAMED_NPC.GHOST, ACTION.RIGHT, ACTION.PATROL, ghostTalk, 1, 2);
		NPC ghost2 = new NPC(NAMED_NPC.GHOST, ACTION.LEFT, ACTION.WANDER, ghostTalk2, 7, 10);
		ghost.setSpeed(2);
		
		ArrayList<Point> ghostPatrol = new ArrayList<Point>();
		ghostPatrol.add(new Point(13, 2));
		ghostPatrol.add(new Point(13, 13));
		ghostPatrol.add(new Point(1, 13));
		ghostPatrol.add(new Point(1, 2));
		ghost.setPatrolPath(ghostPatrol);
		
		boolean[][] wanderLimit = new boolean[15][15];
		for (int i = 0; i < wanderLimit.length; i++)	{
			for (int j = 0; j < wanderLimit[0].length; j++)	{
				wanderLimit[i][j] = true;
			}
		}
		for (int i = 4; i <= 10; i++)	{
			for (int j = 9; j <= 12; j++)	{
				wanderLimit[i][j] = false;
			}
		}
		
		ghost2.setWanderLimit(wanderLimit);
		
		NPCs.add(ghost);
		NPCs.add(ghost2);
		
		
		Map map = new Map(tiles, NPCs);
		return map;
	}

	/**
	 * @return The main testing map
	 */
	public static Map test1()	{
		
		ArrayList<ArrayList<MONSTER>> encounters = new ArrayList<ArrayList<MONSTER>>();
		ArrayList<Integer> spawnChances = new ArrayList<Integer>();
		int spawnProbability = 20;
		
		ArrayList<MONSTER> encounter1 = new ArrayList<MONSTER>(4);
		encounter1.add(MONSTER.GIANT_RAT);
		ArrayList<MONSTER> encounter2 = new ArrayList<MONSTER>(4);
		encounter2.add(MONSTER.RAZORCLAW_CRAB);
		ArrayList<MONSTER> encounter3 = new ArrayList<MONSTER>(4);
		encounter3.add(MONSTER.DEATHSTALKER_CROW);
		
		encounters.add(encounter1);
		encounters.add(encounter2);
		encounters.add(encounter3);
		
		spawnChances.add(50);
		spawnChances.add(30);
		spawnChances.add(20);
		
		SpawnGenerator spawn = new SpawnGenerator(encounters, spawnChances, spawnProbability);
		
		Tile[][] tiles = new Tile[22][18];
		Tile grassTile = new Tile(TILE.GROUND_GRASS);
		grassTile.setSpawn(spawn);
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
		tiles[20][7] = waterTile;
		tiles[21][6] = waterTile;
		tiles[21][5] = waterTile;
		tiles[21][10] = waterTile;
		tiles[21][11] = waterTile;
		tiles[20][10] = waterTile;
		tiles[20][11] = waterTile;
		tiles[20][12] = waterTile;
		tiles[20][13] = waterTile;
		tiles[20][14] = waterTile;
		tiles[20][15] = waterTile;
		tiles[20][16] = waterTile;
		tiles[21][13] = waterTile;
		tiles[21][14] = waterTile;
		tiles[21][15] = waterTile;
		tiles[21][16] = waterTile;
		tiles[21][17] = waterTile;
		tiles[20][17] = waterTile;
		tiles[21][12] = waterTile;
		tiles[20][13] = waterTile;
		tiles[14][12] = waterTile;
		tiles[13][17] = waterTile;
		tiles[12][17] = waterTile;
		tiles[14][17] = waterTile;
		tiles[7][9] = treeTile;
		tiles[10][8] = treeTile;
		tiles[11][8] = treeTile;
		
		Chest ironSwordChest = new Chest(TREASURE_CHEST.TREASURE_CHEST_SMALL);
		ironSwordChest.setLoot(new Weapon(SWORD.IRON));
		Chest ironDaggerChest = new Chest(TREASURE_CHEST.TREASURE_CHEST_SMALL);
		ironDaggerChest.setLoot(new Weapon(DAGGER.GOLD));
		
		tiles[9][12] = new Tile(TILE.GROUND_GRASS, ironSwordChest);
		tiles[13][12] = new Tile(TILE.GROUND_GRASS, DECORATION.TREE_PALM);
		tiles[11][15] = new Tile(TILE.GROUND_GRASS, ironDaggerChest);
		
		for (int i = 0; i < tiles.length; i++)	{
			tiles[i][0] = new Tile(TILE.WALL_CAVE);
			tiles[i][1] = new Tile(TILE.WALL_CAVE);
		}
		tiles[12][1] = new Tile(TILE.WALL_CAVE, DOOR.WALL_CAVE_DOOR, 12, 1);
		
		tiles[21][9] = new Tile(TILE.GROUND_GRASS, DOOR.TRANSITION_RIGHT, 21, 9);
		tiles[21][8] = new Tile(TILE.GROUND_GRASS, DOOR.TRANSITION_RIGHT, 21, 8);
		
		ArrayList<String> sign1 = new ArrayList<String>();
		sign1.add("I'm a sign, bro.");
		ArrayList<String> sign2 = new ArrayList<String>();
		sign2.add("I'm a sign too, dude.");
		ArrayList<String> sign3 = new ArrayList<String>();
		sign3.add("This is actually a mountain range. I promise.");
		sign3.add("I'm a multi-sentenced sign. :O");
		
		tiles[18][5] = new Tile(TILE.GROUND_GRASS, SIGN.WOOD, sign1);
		tiles[17][5] = new Tile(TILE.GROUND_GRASS, SIGN.WOOD, sign2);
		tiles[13][2] = new Tile(TILE.GROUND_GRASS, SIGN.WOOD, sign3);
				
		ArrayList<String> terraTalk = new ArrayList<String>();
		terraTalk.add("I have teal hair.");
		terraTalk.add("Push G and your party will probably level up.");
		ArrayList<String> celesTalk = new ArrayList<String>();
		celesTalk.add("I wander about aimlessly.");
		
		ArrayList<NPC> NPCs = new ArrayList<NPC>();
		
		NPC terra = new NPC(NAMED_NPC.TERRA, ACTION.DOWN, ACTION.STAND, terraTalk, 8, 2);
		NPC celes = new NPC(NAMED_NPC.CELES, ACTION.DOWN, ACTION.WANDER, celesTalk, 8, 3);
		
		NPCs.add(terra);
		NPCs.add(celes);
		
		ArrayList<String> spawnTalk = new ArrayList<String>();
		spawnTalk.add("Whoa! Where the hell did I come from!");
		
		NPC spawnGhost = new NPC(NAMED_NPC.GHOST, ACTION.DOWN, ACTION.WANDER, spawnTalk, 2, 2);
		
		ArrayList<Trigger> triggers = new ArrayList<Trigger>();
		
		Trigger spawnNPC = new Trigger(new TEvent(TEVENT.CHARACTER_ENTERS_TILE, new Point(5, 2)), new TAction(TACTION.SPAWN_NPC, spawnGhost));
		Trigger addToParty = new Trigger(new TEvent(TEVENT.CHARACTER_FINISHES_TALKING, terra), new TAction(TACTION.ADD_NPC_TO_PARTY, new PartyMember(NAMED_NPC.TERRA)));
		addToParty.addAction(new TAction(TACTION.REMOVE_NPC_FROM_MAP, terra));
		
		triggers.add(spawnNPC);
		triggers.add(addToParty);
		
		Map map = new Map(tiles, NPCs, triggers);
		
		return map;
	}
	
}
