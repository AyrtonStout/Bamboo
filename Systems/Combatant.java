package Systems;

import java.awt.Point;

import javax.swing.ImageIcon;

import Systems.Enums.COMBAT_ACTION;

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
	public Stat getMaxHealth();
	public void modCurrentHealth(int health);
	public Stat getCurrentMana();
	public Stat getMaxMana();
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
	public void clearTurnPriorityPrediction();
	public void clearTurnMaximumPrediction();
	public void setTurnPriorityPrediction(int i);
	public void setTurnMaximumPrediction(int i);
	public int getPredictiveSpeedMod();
	public void attackTarget(Combatant target);
	public COMBAT_ACTION getCombatAction();
	public Combatant getTarget();
	public int getLevel();
	public int getTurnPriorityPrediction();
	public int getTurnMaximumPrediction();
	
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
	public int getPredictiveSpeedTrue();

}
