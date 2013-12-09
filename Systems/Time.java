package Systems;

import Systems.Enums.TIME;

public class Time {

	private int days;
	private int hours;
	private int minutes;
	
	private final int TIMESCALE = 90;
	private int count;
	
	public Time()	{
		hours = 9;
		minutes = 0;
	}
	
	public void update()	{
		count++;
		if (count == TIMESCALE)	{
			minutes++;
			count = 0;
		}
		if (minutes == 60)	{
			hours++;
			minutes = 0;
		}
		if (hours == 24)	{
			days++;
			hours = 0;
		}
	}
	
	public int getDays()	{
		return days;
	}
	public int getHours()	{
		return hours;
	}
	public int getMinutes()	{
		return minutes;
	}
	public TIME getTimeOfDay()	{
		if (hours > 6 && hours < 12)	{
			return TIME.MORNING;
		}
		else if (hours >= 12 && hours < 6)	{
			return TIME.DAY;
		}
		else	{
			return TIME.NIGHT;
		}
	}
	
	/*
	 * Returns the string version of the numbers if they are double digits. Otherwise will return the number
	 * with a 0 in front to make time look like 07:00.
	 */
	@Override
	public String toString()	{
		return ((Integer.toString(hours).length() == 2) ? Integer.toString(hours) : "0" + Integer.toString(hours)) + ":" + 
				((Integer.toString(minutes).length() == 2) ? Integer.toString(minutes) : "0" + Integer.toString(minutes));
	}
	
}
