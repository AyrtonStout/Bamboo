package Systems;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author mobius
 * Class responsible for grouping together a collection of enemies. This class
 * will determine the places that the enemies appear during the battle screen
 */
public class Encounter implements Serializable {

	private static final long serialVersionUID = 2856762499938325961L;
	
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	public Encounter(ArrayList<Enemy> enemies)	{
		this.enemies = enemies;
		if (enemies.size() == 1)	{
			enemies.get(0).setOrigin(new Point(350, 155));
		}
	}
	
	
	public ArrayList<Enemy> toArrayList()	{
		return enemies;
	}
	
	public boolean allDefeated()	{
		for (int i = 0; i < enemies.size(); i++)	{
			if (enemies.get(i).getCurrentHealth().getActual() > 0)	{
				return false;
			}
		}
		return true;
	}
	
	public int earnedXP()	{
		int total = 0;
		for (int i = 0; i < enemies.size(); i++)	{
			total += enemies.get(i).getXpReward();
		}
		return total;
	}
	
	public void drawEnemies(Graphics g)	{
		for (int i = 0; i < enemies.size(); i++)	{
			Enemy enemy = enemies.get(i);
			g.drawImage(enemy.getPicture().getImage(), enemy.getOrigin().x + enemy.getOffsetX(), enemy.getOrigin().y, null);
		}
	}


	public void giveXP(PartyMember[] party) {
		int partySize = 0;
		for (int i = 0; i < party.length; i++)	{
			if (party[i] != null)	{
				partySize++;
			}
		}
		for (int i = 0; i < partySize; i++)	{
			party[i].giveXP(earnedXP() / partySize);
		}
	}
	
	public double calculateAggressionScore()	{
		double aggressionScore = 0;
		int highestLevel = 0;
		for (int i = 0; i < enemies.size(); i++)	{
			if (enemies.get(i).aliveEh())	{
				aggressionScore += (enemies.get(i).getLevel() / 2.0) * enemies.get(i).getHealthPercentage();
				if (enemies.get(i).getLevel() > highestLevel)	{	
					highestLevel = enemies.get(i).getLevel();
				}
			}
		}
		aggressionScore += highestLevel / 2.0;
		return aggressionScore;
	}

}
