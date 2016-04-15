package Systems;

import Systems.Enums.TIME;

/**
 * @author mobius
 *         In game clock. Timescale controls the speed of the passage of time. At 60, one real world second equals one in game minute.
 *         At a timescale of 120, two real world seconds equals one in game minute.
 */
public class Time {

	private final int TIMESCALE = 120;
	private int days;
	private int hours;
	private int minutes;
	private int count;

	public Time() {
		hours = 9;
		minutes = 0;
	}

	public void update() {
		count++;
		if (count == TIMESCALE) {
			minutes++;
			count = 0;
		}
		if (minutes == 60) {
			hours++;
			minutes = 0;
		}
		if (hours == 24) {
			days++;
			hours = 0;
		}
	}

	public int getDays() {
		return days;
	}

	public int getHours() {
		return hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public TIME getTimeOfDay() {
		if (hours > 6 && hours < 12) {
			return TIME.MORNING;
		} else if (hours >= 12 && hours < 6) {
			return TIME.DAY;
		} else {
			return TIME.NIGHT;
		}
	}

	/*
	 * Returns the string version of the numbers if they are double digits. Otherwise will return the number
	 * with a 0 in front to make time look like 07:00.
	 */
	@Override
	public String toString() {
		return ((Integer.toString(hours).length() == 2) ? Integer.toString(hours) : "0" + Integer.toString(hours)) + ":" +
				((Integer.toString(minutes).length() == 2) ? Integer.toString(minutes) : "0" + Integer.toString(minutes));
	}
}
