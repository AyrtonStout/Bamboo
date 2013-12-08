package Quests;

import java.awt.Point;
import java.io.Serializable;

import GUI.Map;
import GUI.Player;
import Quests.Enums.TEVENT;

public class TEvent implements Serializable {
	
	private static final long serialVersionUID = -3505855084423834681L;

	private TEVENT eventType;
	private Point enteredPoint;
	
	public TEvent(TEVENT event, Point enteredPoint)	{
		this.eventType = event;
		this.enteredPoint = enteredPoint;
	}
	
	public boolean eventMetEh(Map map, Player player)	{
		if (eventType == TEVENT.CHARACTER_ENTERS_TILE)	{
			if (player.getCoordX() == enteredPoint.x && player.getCoordY() == enteredPoint.y)	{
				return true;
			}
			for (int i = 0; i < map.getNPCs().size(); i++)	{
				if (map.getNPCs().get(i).getCoordX() == enteredPoint.x && map.getNPCs().get(i).getCoordY() == enteredPoint.y)	{
					return true;
				}	
			}
		}
		return false;
	}
	
}
