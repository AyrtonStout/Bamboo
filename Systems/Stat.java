package Systems;

import java.io.Serializable;

public class Stat implements Serializable {
	
	/**
	 * Contains the base and actual values for a particular stat. Useful for remembering what an item or
	 * character's stats were prior to the application of a buff.
	 */
	private static final long serialVersionUID = -6815283919717300517L;
	
	int base = 0;
	int actual = 0;
	
	/**
	 * Creates a new stat and sets its base value to the integer provided
	 * 
	 * @param original The original value of the stat
	 */
	public Stat(int original)	{
		this.base = original;
		actual = original;
	}
	/**
	 * @param value The new base value for the stat
	 */
	public void setBase(int value)	{
		base = value;
	}
	/**
	 * @param value The new actual or practical value for the stat
	 */
	public void setActual(int value)	{
		actual = value;
	}
	/**
	 * @param value Increase the actual stat value by a set amount
	 */
	public void buff(int value)	{
		actual += value;
	}
	/**
	 * Sets the actual value back down to the base value
	 */
	public void clear()	{
		actual = base;
	}
	/**
	 * @return The base value for the stat
	 */
	public int getBase()	{
		return base;
	}
	/**
	 * @return The actual value of the stat
	 */
	public int getActual()	{
		return actual;
	}
	@Override
	public String toString()	{
		return Integer.toString(actual);
	}
}
