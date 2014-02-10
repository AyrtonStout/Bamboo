package Systems;

import java.awt.Point;

public interface Combatant {
	
	public Point getOrigin();
	public Stat getHit();
	public Stat getCritChance();
	public Stat getCritDamage();
	public Stat getDodge();
	public int getAttack();
	public Stat getArmor();
	public Stat getCurrentHealth();
	public void modCurrentHealth(int health);
	public int getWidth();
	public int getHeight();
	public boolean aliveEh();
	public boolean justDiedEh();
	public void setAlive(boolean b);
	public void setJustDied(boolean b);

}
