package Systems;

import java.io.Serializable;

public class Stat implements Serializable {
	
	private static final long serialVersionUID = -6815283919717300517L;
	
	int base = 0;
	int actual = 0;
	
	public Stat(int original)	{
		this.base = original;
		actual = original;
	}
	public void setBase(int value)	{
		base = value;
	}
	public void setActual(int value)	{
		actual = value;
	}
	public void buff(int value)	{
		actual += value;
	}
	public void clear()	{
		actual = base;
	}
	public int getBase()	{
		return base;
	}
	public int getActual()	{
		return actual;
	}
	@Override
	public String toString()	{
		return Integer.toString(actual);
	}
}
