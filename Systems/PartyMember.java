package Systems;

import javax.swing.ImageIcon;

import GUI.Enums.NAMED_NPC;

public class PartyMember {
	
	private Stat strength;
	private Stat agility;
	private Stat spirit;
	private Stat intellect;
	private Stat stamina;
	
	private Stat health;
	private Stat secondary;
	
	private Stat critChance = new Stat(0);
	private Stat critDamage = new Stat(0);
	private Stat hit = new Stat(0);
	private Stat armorPen = new Stat(0);
	private Stat dodge = new Stat(0);
	private Stat resist = new Stat(0);
	
	private int STAMINA_TO_HEALTH_RATIO = 5;
	
	private ImageIcon portrait;
	private ImageIcon right;
	
	@SuppressWarnings("incomplete-switch")
	public PartyMember(NAMED_NPC member)	{
		
		switch (member)	{
		case SABIN:
			portrait = new ImageIcon("GUI/Resources/Characters/Sabin - Portrait.gif");
			right = new ImageIcon("GUI/Resources/Characters/Sabin (Right).gif");
			
			strength = new Stat(8);
			agility = new Stat(4);
			spirit = new Stat(5);
			intellect = new Stat(4);
			stamina = new Stat(8);
			
			health = new Stat(stamina.getActual() * STAMINA_TO_HEALTH_RATIO);
			secondary = new Stat(100);
		}
		
	}
	
	public ImageIcon getPortrait()	{
		return portrait;
	}

}
