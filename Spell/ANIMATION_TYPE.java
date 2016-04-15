package Spell;

public enum ANIMATION_TYPE {

	FIREBALL(3, true),
	FROSTBOLT(3, true),
	HEAL(20, false);

	private int speed;
	private boolean dynamic;

	/**
	 * Creates an animation for a specific type of spell. Any spell that uses the FIREBALL animation type will all look
	 * visibly the same.
	 *
	 * @param speed            For dynamic animations, the speed that the spell moves across the screen. For static animations, this determines
	 *                         how long the animation is visible
	 * @param dynamicAnimation Whether or not the animation is dynamic
	 */
	private ANIMATION_TYPE(int speed, boolean dynamicAnimation) {
		this.speed = speed;
		this.dynamic = dynamicAnimation;
	}

	public int getSpeed() {
		return speed;
	}

	public boolean dynamicEh() {
		return dynamic;
	}

}
