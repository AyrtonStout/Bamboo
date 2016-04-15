package Map;

import Systems.GameData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author mobius
 *         Contains the main game loop. Instantiates the GameData object and updates it with the timer
 *         Player objects or game's actions are determined by the implemented KeyAdaptor
 *         KeyAdaptor does not start behavior by itself, but merely sets the behavior for the object
 *         the next time update is called
 */
@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {

	private GameData data;
	private Timer time;
	private int FPS = 60;

	public Board(GameData data) {
		this.data = data;

		this.setPreferredSize(new Dimension(data.getWindowWidth(), data.getWindowHeight()));
		this.setLayout(new BorderLayout());

		this.add(data.getDialogueBox(), BorderLayout.SOUTH);
		this.add(data.getMenu(), BorderLayout.EAST);

		time = new Timer((int) (1000 / FPS), this);
		time.start();
	}

	//Timer's event
	public void actionPerformed(ActionEvent e) {
		data.update();
		repaint();
	}

	/* 
	 * Draws all of the game board's elements (which is basically the current map)
	 * The drawing of other elements happens from in the GameData class
	 */
	@Override
	public void paintComponent(Graphics g) {
		Doodad doodad;

		//Draws all tiles and background doodads
		for (int column = 0; column < data.getCurrentMap().getArray()[0].length; column++) {
			for (int row = 0; row < data.getCurrentMap().getArray().length; row++) {
				g.drawImage(data.getCurrentMap().getArray()[row][column].getBackground(),
						row * 40 - data.getPlayer().getBackgroundX(), column * 40 - data.getPlayer().getBackgroundY(), null);

				doodad = data.getCurrentMap().getArray()[row][column].getDoodad();
				if (doodad != null && !doodad.dominantEh()) {
					g.drawImage(doodad.getBackground(), row * 40 - data.getPlayer().getBackgroundX() + doodad.getOffsetX(),
							column * 40 - data.getPlayer().getBackgroundY() + doodad.getOffsetY(), null);
				}
			}
		}

		NPC npc;

		//Draws all characters and dominant doodads (doodads that are meant to be drawn over the player) one row at a time
		for (int column = 0; column < data.getCurrentMap().getArray()[0].length; column++) {
			for (int row = 0; row < data.getCurrentMap().getArray().length; row++) {

				if (data.getPlayer().getCoordX() == row && data.getPlayer().getCoordY() == column) {
					g.drawImage(data.getPlayer().getImage(), data.getPlayer().getCharX(), data.getPlayer().getCharY(), null);
				}

				for (int i = 0; i < data.getCurrentMap().getNPCs().size(); i++) {
					npc = data.getCurrentMap().getNPCs().get(i);
					if (npc.getCoordX() == row && npc.getCoordY() == column) {
						g.drawImage(npc.getImage(), npc.getCharX() - data.getPlayer().getBackgroundX(),
								npc.getCharY() - data.getPlayer().getBackgroundY(), null);
					}
				}

				doodad = data.getCurrentMap().getArray()[row][column].getDoodad();
				if (doodad != null && doodad.dominantEh()) {
					g.drawImage(doodad.getBackground(), row * 40 - data.getPlayer().getBackgroundX() + doodad.getOffsetX(),
							column * 40 - data.getPlayer().getBackgroundY() + doodad.getOffsetY(), null);
				}
			}
		}
	}

	/**
	 * @return The game's data
	 */
	public GameData getData() {
		return data;
	}
}