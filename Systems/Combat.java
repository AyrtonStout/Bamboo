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
		data.getBattleScreen().addBattleText(damageDealt(), victim);
	}
	
	public boolean missEh()	{
		return false;
	}
	public boolean critEh()	{
		return false;
	}
	public int damageDealt()	{
		if (critEh())	{
			return 0;
		}
		else	{
			return 20;
		}
	}

}
