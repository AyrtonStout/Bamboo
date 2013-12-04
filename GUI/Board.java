package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
 
/**
 * @author mobius
 * Contains the main game loop. Instantiates the GameData object and updates it with the timer
 * Player objects or game's actions are determined by the implemented KeyAdaptor
 * KeyAdaptor does not start behavior by itself, but merely sets the behavior for the object
 * the next time update is called
 */
@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {

	private GameData data;
	private Timer time;
	private int FPS = 60;
	
	public Board(int windowWidth, int windowHeight) {
		data = new GameData(windowWidth, windowHeight);
		addKeyListener(new AL());
		
		this.setBackground(Color.BLUE);
		this.setLayout(new BorderLayout());

		this.add(data.getTextBox(), BorderLayout.SOUTH);
		
		setFocusable(true);
		time = new Timer((int)(1000 / FPS), this);
		time.start();
	}

	//Timer's event
	public void actionPerformed(ActionEvent e) {
		data.update();
		repaint();
	}

	/**
	 * @author mobius
	 * If the key pressed during the overworld screen (gamestate 0) is an arrow key, this will send the key event
	 * to the character object to queue up its corresponding next action to perform when update() is called. Releasing
	 * the key will clear the queued command
	 */
	private class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			if (data.getGameState() == 0)	{
				if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || 
						e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)	{
					data.getPlayer().move(e);
				}
				else if (e.getKeyCode() == KeyEvent.VK_Z)	{
					data.activate();
				}
			}
			else if (data.getGameState() == 1)	{
				data.advanceDialogue();
			}
		}

		/* 
		 * Cancels the character's queued action causing it to stand still after its current action completes
		 */
		public void keyReleased(KeyEvent e) {
			data.getPlayer().cancelMove();
			//TODO Fix bug where releasing a key other than the arrow keys will cause the player to quit moving
		}
	}
	
	
	
	/* 
	 * Draws all of the game board's elements (which is basically the current map)
	 * The drawing of other elements happens from in the GameData class
	 */
	@Override
	public void paintComponent(Graphics g)	{
		Doodad doodad;
		
		//Draws all tiles and background doodads
		for (int column = 0; column < data.getCurrentMap().getArray()[0].length; column++)	{
			for (int row = 0; row < data.getCurrentMap().getArray().length; row++)	{
				g.drawImage(data.getCurrentMap().getArray()[row][column].getBackground(), 
						row*40 - data.getPlayer().getBackgroundX(), column*40 - data.getPlayer().getBackgroundY(), null);
				
				doodad = data.getCurrentMap().getArray()[row][column].getDoodad();
				if (doodad != null && !doodad.dominantEh())	{
					g.drawImage(doodad.getBackground(), row*40 - data.getPlayer().getBackgroundX() + doodad.getOffsetX(), 
						column*40 - data.getPlayer().getBackgroundY() + doodad.getOffsetY(), null);
				}
			}
		}
		
		NPC npc;
		
		//Draws all characters and dominant doodads (doodads that are meant to be drawn over the player) one row at a time
		for (int column = 0; column < data.getCurrentMap().getArray()[0].length; column++)	{
			for (int row = 0; row < data.getCurrentMap().getArray().length; row++)	{
				
				if (data.getPlayer().getCoordX() == row && data.getPlayer().getCoordY() == column)	{
					g.drawImage(data.getPlayer().getImage(), data.getPlayer().getCharX(), data.getPlayer().getCharY(), null);
				}
				
				for (int i = 0; i < data.getCurrentMap().getNPCs().size(); i++)	{
					npc = data.getCurrentMap().getNPCs().get(i);
					if (npc.getCoordX() == row && npc.getCoordY() == column)	{
						g.drawImage(npc.getImage(), npc.getCharX() - data.getPlayer().getBackgroundX(), 
								npc.getCharY() - data.getPlayer().getBackgroundY(), null);
					}
				}
				
				doodad = data.getCurrentMap().getArray()[row][column].getDoodad();
				if (doodad != null && doodad.dominantEh())	{
					g.drawImage(doodad.getBackground(), row*40 - data.getPlayer().getBackgroundX() + doodad.getOffsetX(), 
						column*40 - data.getPlayer().getBackgroundY() + doodad.getOffsetY(), null);
				}
				
			}
		}

	}
}