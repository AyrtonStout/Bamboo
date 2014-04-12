package Systems;

import java.io.Serializable;
import java.util.Random;

import javax.swing.ImageIcon;

import Battle.Enums.COMBAT_ACTION;
import Systems.Enums.MONSTER;

/**
 * @author mobius
 * Any kind of combatant that the player can fight. Has most of the same stats as the player's characters
 * but generally more simplified. 
 */
public class Enemy extends Combatant implements Serializable {
	
	private static final long serialVersionUID = 8656364136085883106L;
	private int xpReward;
	
	public Enemy (MONSTER type)	{
		Random rand = new Random();
		int minLevel = 0, maxLevel = 0;
		int healthBase = 0, healthGrowth = 0;
		double attackBase = 0, defenseBase = 0, magicBase = 0, magDefenseBase = 0;
		double attackGrowth = 0, defenseGrowth = 0, magicGrowth = 0, magDefenseGrowth = 0;
		double speedBase = 0;
		double speedGrowth = 0;
		int xpPerLevel = 0;
		
		switch (type)	{
		case GIANT_RAT:
			name = "Giant Rat";
			picture = new ImageIcon("GUI/Resources/Enemies/Crab_RazorClaw.png");
			battlePicture = new ImageIcon("GUI/Resources/Enemies/Crab_RazorClaw-Small.png");
			width = 162; height = 151;
			xpPerLevel = 45;
			
			minLevel = 1;       maxLevel = 3;
			
			attackBase = 5.2;       attackGrowth = 1.1;
			defenseBase = 3.6;      defenseGrowth = 0.9;
			magicBase = 0;          magicGrowth = 0;
			magDefenseBase = 1.2;   magDefenseGrowth = 0.3;
			speedBase = 5; speedGrowth = 0.4;
			
			healthBase = 3;    healthGrowth = 4;
			
			break;
		case RAZORCLAW_CRAB:
			name = "Razorclaw Crab";
			picture = new ImageIcon("GUI/Resources/Enemies/Crab_RazorClaw.png");
			battlePicture = new ImageIcon("GUI/Resources/Enemies/Crab_RazorClaw-Small.png");
			width = 162; height = 151;
			xpPerLevel = 45;
			
			minLevel = 2;       maxLevel = 3;
			
			attackBase = 2.4;       attackGrowth = 0.7;
			defenseBase = 3.3;      defenseGrowth = 1.3;
			magicBase = 0;          magicGrowth = 0;
			magDefenseBase = 2.2;   magDefenseGrowth = 0.7;
			speedBase = 3; speedGrowth = 0.3;
			
			healthBase = 4;    healthGrowth = 5;
			
			break;
		case DEATHSTALKER_CROW:
			name = "Deathstalker Crow";
			picture = new ImageIcon("GUI/Resources/Enemies/Crab_RazorClaw.png");
			battlePicture = new ImageIcon("GUI/Resources/Enemies/Crab_RazorClaw-Small.png");
			width = 162; height = 151;
			xpPerLevel = 45;
			
			minLevel = 3;       maxLevel = 3;
			
			attackBase = 3.2;       attackGrowth = 1.4;
			defenseBase = 2.6;      defenseGrowth = 0.7;
			magicBase = 0;          magicGrowth = 0;
			magDefenseBase = 1.6;   magDefenseGrowth = 0.4;
			speedBase = 9; speedGrowth = 0.5;
			
			healthBase = 3;    healthGrowth = 4;
			
			break;
		}
		
		int variance = maxLevel - minLevel;
		if (variance == 0)	{
			level = minLevel;
		}
		else	{
			level = Math.abs(rand.nextInt()) % variance + minLevel;
		}
		attackPower.setBase((int) (attackBase + (attackGrowth * level)));
		defense.setBase((int) (defenseBase + (defenseGrowth * level)));
		spellPower.setBase((int) (magicBase + (magicGrowth * level)));
		resist.setBase((int) (magDefenseBase + (magDefenseGrowth * level)));
		speed.setBase((int) (speedBase + (speedGrowth * level)));
		
		maximumHealth.setBase((int) (healthBase + (healthGrowth * level)));
		currentHealth.setBase(maximumHealth.getActual());
		
		xpReward = xpPerLevel * level;
	}
	
	public void update()	{
		switch (combatAction)	{
		case IDLE:
			break;
		case ATTACK:
			if (animationSteps == 75)	{
				animationSteps -= 1;
			}
			else if (animationSteps > 40)	{
				animationSteps -= 1;
			}
			else if (animationSteps == 40)	{
				combatAction = COMBAT_ACTION.IMPACT;
				animationSteps -= 1;
			}
			else if (animationSteps > 20)	{
				offsetX -= 6;
				animationSteps -= 4;
			}
			else if (animationSteps > 0)	{
				offsetX += 6;
				animationSteps -= 4;
			}
			else {
				combatAction = COMBAT_ACTION.IDLE;
			}
			break;
		case IMPACT:
			combatAction = COMBAT_ACTION.ATTACK;
		case ITEM:
			break;
		default:
			break;
		}
	}
	
	public int getXpReward()	{
		return xpReward;
	}
	
	public void takeAction(PartyMember[] party, Encounter enemies)	{
		int possibleTargets = 0;
		Random rand = new Random();
		int attackTarget;
		
		for (int i = 0; i < party.length; i++)	{
			if (party[i] != null && party[i].aliveEh())	{
				possibleTargets++;
			}
		}
		attackTarget = rand.nextInt(possibleTargets);
		
		while (attackTarget != 0)	{
			//TODO finish this
		}
		
		target = party[0];
		combatAction = COMBAT_ACTION.ATTACK;
		animationSteps = 100;
	}

	@Override
	public int getAttack() {
		return super.attackPower.getActual();
	}
}

