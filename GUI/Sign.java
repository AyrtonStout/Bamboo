package GUI;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import GUI.Enums.SIGN;

public class Sign extends Doodad implements Serializable {

	private static final long serialVersionUID = 2095848039837938438L;
	
	private ArrayList<String> content = new ArrayList<String>();
	
	public Sign(SIGN type, ArrayList<String> dialogue)	{
		if (type == SIGN.WOOD)	{
			background = new ImageIcon("GUI/Resources/Sign_Wood.png");
			moveBlock = true;
			verticalOffset = 7;
			horizontalOffset = 3;
			content = dialogue;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getDialogue()	{
		return (ArrayList<String>)content.clone();
	}
	
	

}
