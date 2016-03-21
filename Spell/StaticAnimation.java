package Spell;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.ImageIcon;

public class StaticAnimation implements SpellAnimation {

	private int duration;
	private ImageIcon effect;
	private Point origin;

	public StaticAnimation(ANIMATION_TYPE type, Point origin)	{
		this.duration = type.getSpeed();
		effect = new ImageIcon("GUI/Resources/Animation/" + type);
		this.origin = origin;
	}
	
	@Override
	public void update() {
		duration--;
	}

	@Override
	public boolean doneEh() {
		if (duration <= 0) return true;
		return false;
	}
	
	public void drawAnimation(Graphics g)	{
		g.drawImage(effect.getImage(), origin.x, origin.y, null);
	}

}
