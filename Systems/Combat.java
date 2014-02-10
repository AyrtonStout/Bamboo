package Systems;

import java.util.ArrayList;

import GUI.BattleScreen;

public class Combat {
	
	private GameData data;
	
	private PartyMember[] party;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private BattleScreen battleScreen;
	
	public Combat(GameData data)	{
		this.data = data;
	}
	
	public void initialize(PartyMember[] party, ArrayList<Enemy> enemies)	{
		this.party = party;
		this.enemies = enemies;
	}
	
	public void attack(Combatant aggressor, Combatant victim)	{
		if (!missEh())	{
			
		}
		int painBrought = damageDealt(aggressor, victim);
		
		data.getBattleScreen().addBattleText(painBrought, victim);
		victim.modCurrentHealth(-painBrought);
		if (victim.getCurrentHealth().getActual() < 1)	{
			victim.setJustDied(true);
			victim.setAlive(false);
		}

		
		if (aggressor.getClass() == PartyMember.class)	{
			((PartyMember) aggressor).increaseDamageDealt(painBrought);
		}
	}
	
	public boolean missEh()	{
		return false;
	}
	public boolean critEh()	{
		return false;
	}
	public int damageDealt(Combatant aggressor, Combatant victim)	{
		if (critEh())	{
			return 0;
		}
		else	{
			return (aggressor.getAttack() - victim.getArmor().getActual() < 1) ? 
					1 : aggressor.getAttack() - victim.getArmor().getActual();
		}
	}

}
