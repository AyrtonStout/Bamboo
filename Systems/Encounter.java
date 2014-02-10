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
			enemies.get(0).setOrigin(new Point(350, 115));
		}
	}
	
	
	public ArrayList<Enemy> toArrayList()	{
		return enemies;
	}
	
	public boolean defeatedEh()	{
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
			total += enemies.get(i).getXP();
		}
		return total;
	}
	
	public void drawEnemies(Graphics g)	{
		for (int i = 0; i < enemies.size(); i++)	{
			Enemy enemy = enemies.get(i);
			g.drawImage(enemy.getPicture().getImage(), enemy.getOrigin().x, enemy.getOrigin().y, null);
		}
	}

}
