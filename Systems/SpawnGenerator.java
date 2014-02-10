package Systems;

import java.io.Serializable;
import java.util.ArrayList;

import Systems.Enums.MONSTER;

public class SpawnGenerator implements Serializable {

	private static final long serialVersionUID = -3440878875373516134L;
	
	ArrayList<ArrayList<MONSTER>> enemies;
	ArrayList<Integer> spawnChances;
	int spawnProbability;
	
	public SpawnGenerator(ArrayList<ArrayList<MONSTER>> enemies, ArrayList<Integer> spawnChance, int spawnProbability)	{
		this.enemies = enemies;
		this.spawnChances = spawnChance;
		this.spawnProbability = spawnProbability;
		
		int total = 0;
		for (int i = 0; i < spawnChance.size(); i++)	{
			total += spawnChance.get(i);
		}
		if (total != 100)	{
			throw new IllegalArgumentException("Spawn chance does not add up to 100");
		}
	}
	
	public boolean spawnEh(int random)	{
		if (random < spawnProbability)	{
			return true;
		}
		return false;
	}
	
	public Encounter spawnEncounter(int random)	{
		random = (int) 100.0 / spawnProbability * random;
		
		for (int i = spawnChances.size() - 1; i >= 0; i--)	{
			if (random < spawnChances.get(i))	{
				ArrayList<Enemy> mobs = new ArrayList<Enemy>();
				for (int index = 0; index < enemies.get(i).size(); index++)	{
					mobs.add(new Enemy(enemies.get(i).get(index)));
				}
				return new Encounter(mobs);
			}
			else	{
				random -= spawnChances.get(i);
			}
		}
		try {
			throw new Exception("Bad programmer logic exception");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
