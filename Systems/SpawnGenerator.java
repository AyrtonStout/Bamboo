package Systems;

import java.io.Serializable;
import java.util.ArrayList;

public class SpawnGenerator implements Serializable {
	
	ArrayList<Enemy> enemies;
	ArrayList<Integer> spawnChances;
	int spawnProbability;
	
	public SpawnGenerator(ArrayList<Enemy> enemies, ArrayList<Integer> spawnChance, int spawnProbability)	{
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
	
	public Enemy spawnEnemy(int random)	{
		random = (int) 100.0 / spawnProbability * random;
		
//		System.out.println("Spawn: " + random);
		
		for (int i = spawnChances.size() - 1; i >= 0; i--)	{
			if (random < spawnChances.get(i))	{
				return enemies.get(i);
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
