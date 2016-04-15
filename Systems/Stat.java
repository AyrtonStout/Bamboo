package Systems;

import java.io.Serializable;

public class Stat implements Serializable {

	/**
	 * Contains the base and buff values for a particular stat. Useful for remembering what an item or
	 * character's stats were prior to the application of a buff, but still being able to return an
	 * integer that represents the combination of both values.
	 */
	private static final long serialVersionUID = -6815283919717300517L;

	int base;
	int buff = 0;

	/**
	 * Creates a new stat and sets its base value to the integer provided
	 *
	 * @param original The original value of the stat
	 */
	public Stat(int original) {
		this.base = original;
	}

	/**
	 * Changes the base value by the amount provided. The provided number can be negative and will reduce the
	 * character's stat instead.
	 *
	 * @param value The value that the base will be changed by
	 */
	public void modifyBase(int value) {
		base += value;
	}

	/**
	 * Sets the value of the buff to a new value. Those wishing only to modify the buff by a set amount
	 * should instead use the modifyBuff() method.
	 *
	 * @param value The new value for the buff
	 */
	public void setBuff(int value) {
		buff = value;
	}

	/**
	 * Changes the base value's buff's strength by the amount provided. The provided number can be negative
	 * and will reduce the character's stat instead.
	 *
	 * @param value Modify the buffing value of this stat by a certain amount
	 */
	public void modifyBuff(int value) {
		buff += value;
	}

	/**
	 * Removes the buffs from this stat
	 */
	public void clear() {
		buff = 0;
	}

	/**
	 * @return The base value for the stat before buffs
	 */
	public int getBase() {
		return base;
	}

	/**
	 * Sets the base value of the stat to a new value. Those wishing only to modify the stat by a set amount
	 * should instead use the modifyBase() method.
	 *
	 * @param value The new base value for the stat
	 */
	public void setBase(int value) {
		base = value;
	}

	/**
	 * @return The actual value of the stat (base + buffs)
	 */
	public int getActual() {
		return base + buff;
	}

	@Override
	public String toString() {
		return Integer.toString(base + buff);
	}
}
