package Spell;

import javax.swing.*;
import java.awt.*;

public class MovingAnimation implements SpellAnimation {

	private static int startDelay = 55;
	private int duration;
	private ImageIcon effect;
	private double xLoc, yLoc;
	private double xUpdate, yUpdate;
	private boolean xMovementDominant = true;
	private boolean delay = true;

	public MovingAnimation(ANIMATION_TYPE type, Point origin, Point target) {

		if (Math.abs(target.x - origin.x) < Math.abs(target.y - origin.y)) {
			xMovementDominant = false;
		}

		xLoc = origin.x;
		yLoc = origin.y;

		if (xMovementDominant) {
			effect = new ImageIcon("GUI/Resources/Animation/" + type);
			duration = Math.abs(origin.x - target.x) / type.getSpeed();
		} else {
			System.err.println("Missing vertical picture");
			duration = Math.abs(origin.y - target.y) / type.getSpeed();
		}
		xUpdate = (origin.x - target.x) / duration;
		yUpdate = (origin.y - target.y) / duration;
	}

	@Override
	public void update() {
		if (delay) {
			startDelay--;
			if (startDelay == 0) {
				delay = false;
			}
		} else {
			xLoc -= xUpdate;
			yLoc -= yUpdate;
			duration--;
		}
	}

	@Override
	public boolean doneEh() {
		if (duration <= 0)
			return true;
		return false;
	}

	public void drawAnimation(Graphics g) {
		g.drawImage(effect.getImage(), (int) xLoc, (int) yLoc, null);
		System.out.println((int) xLoc + "  " + (int) yLoc);
	}
}
