package Spell;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.ImageIcon;

public class MovingAnimation implements Animation {
	
	private int duration;
	private ImageIcon effect;
	private double xLoc, yLoc;
	private double xUpdate, yUpdate;
	private boolean xMovementDominant = true;
	
	public MovingAnimation(ANIMATION_TYPE type, int speed, Point origin, Point target)	{
		
		if (Math.abs(target.x - origin.x) < Math.abs(target.y - origin.y))	{
			xMovementDominant = false;
		}
		
		xLoc = origin.x;
		yLoc = origin.y;
		
		if (xMovementDominant)	{
			effect = new ImageIcon("GUI/Resources/Animation/" + type);
			duration = Math.abs(origin.x - target.x) / speed;
		} else {
			System.err.println("Missing vertical picture");
			duration = Math.abs(origin.y - target.y) / speed;
		}
		xUpdate = (origin.x - target.x) / duration;
		yUpdate = (origin.y - target.y) / duration;
	
		
	}
	
	@Override
	public void update() {
		xLoc -= xUpdate;
		yLoc -= yUpdate;
	}
	
	@Override
	public boolean doneEh() {
		if (duration <= 0)	return true;
		return false;
	}
	
	public void drawAnimation(Graphics g)	{
		g.drawImage(effect.getImage(), (int) xLoc, (int) yLoc, null);
	}


}
