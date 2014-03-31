package Systems;

import java.awt.Point;

import javax.swing.ImageIcon;

public interface Combatant {
	
	public String getName();
	public Point getOrigin();
	public Stat getHit();
	public Stat getCritChance();
	public Stat getCritDamage();
	public Stat getDodge();
	public Stat getSpeed();
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
	public ImageIcon getBattlePicture();
	public int getTurnPriority();
	public void setTurnPriority(int priority);
	public int getTurnMaximum();
	public void setTurnMaximum(int maximum);
	public int getTurnPrediction();
	
	/**
	 * This is used to calculate the turn order of the combatants in advance. This integer saves the prediction
	 * time by making it so it doesn't have to look through the turn order array every time it is calculating who
	 * will go next.
	 */
	public void incrementTurnPrediction();

	/**
	 * Used to reset turn order prediction to 0 after turn order calculations are made.
	 */
	public void clearTurnPrediction();
	public int getPredictiveSpeed();

}
