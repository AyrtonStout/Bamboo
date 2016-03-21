package Spell;

import java.awt.Graphics;

public interface SpellAnimation {
	
	public void update();
	public boolean doneEh();
	public void drawAnimation(Graphics g);
}
