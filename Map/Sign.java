package Map;

import Map.Enums.SIGN;
import Systems.PartyMember;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author mobius
 *         A kind of doodad that, when interacted with, freezes the player's movements and brings up the
 *         dialogue box that displays the sign's messages
 */
public class Sign extends Doodad implements Serializable {

	private static final long serialVersionUID = 2095848039837938438L;

	private ArrayList<String> content = new ArrayList<String>();

	/**
	 * The sign takes an ArrayList of Strings that are retrieved one at a time every time the player advances
	 * the dialogue. When creating dialogue for the sign, a pause can be forced by making the player advance
	 * to the next line themselves. Care should be made to not make any String excessively long as it will run
	 * off the dialogue box.
	 *
	 * @param type     The type of sign to be created visually
	 * @param dialogue An ArrayList of Strings displayed in the dialogue box
	 */
	public Sign(SIGN type, ArrayList<String> dialogue) {
		if (type == SIGN.WOOD) {
			background = new ImageIcon("GUI/Resources/Sign_Wood.png");
			moveBlock = true;
			verticalOffset = 7;
			horizontalOffset = 3;
			content = dialogue;
		}
	}

	/**
	 * @return An ArrayList of all of the sign's dialogue
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getDialogue() {
		PartyMember.incrementSignsRead();
		return (ArrayList<String>) content.clone();
	}
}
