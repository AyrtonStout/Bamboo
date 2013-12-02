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
		System.out.println("Player X - " + data.getPlayer().getCoordX() + "   Player Y - " + data.getPlayer().getCoordY() + 
				"    facing - " + data.getPlayer().getFacing());
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
	
	
	
	@Override
	public void paintComponent(Graphics g)	{
		for (int row = 0; row < data.getCurrentMap().getArray().length; row++)	{
			for (int column = 0; column < data.getCurrentMap().getArray()[row].length; column++)	{
				if (data.getCurrentMap().getArray()[row][column] != null)	{
					g.drawImage(data.getCurrentMap().getArray()[row][column].getBackground(), 
							row*40 - data.getPlayer().getBackgroundX(), column*40 - data.getPlayer().getBackgroundY(), null);
				}
			}
		}
		/*
		 * Draws all of the tiles' doodads
		 */
		for (int row = 0; row < data.getCurrentMap().getArray().length; row++)	{
			for (int column = 0; column < data.getCurrentMap().getArray()[row].length; column++)	{
				if (data.getCurrentMap().getArray()[row][column].getDoodad() != null)	{
					g.drawImage(data.getCurrentMap().getArray()[row][column].getDoodad().getBackground(), 
							row*40 - data.getPlayer().getBackgroundX() + data.getCurrentMap().getArray()[row][column].getDoodad().getOffsetX(), 
							column*40 - data.getPlayer().getBackgroundY() + data.getCurrentMap().getArray()[row][column].getDoodad().getOffsetY(), null);
				}
			}
		}
		/*
		 * Draws the player character
		 */
		g.drawImage(data.getPlayer().getImage(), data.getPlayer().getCharX(), data.getPlayer().getCharY(), null);
		
		/*
		 * Redraws the doodads that are below the character so that they appear above it
		 */
		Tile drawnTile;
		//Down Center
		if (data.getPlayer().getCoordY() + 1 < data.getCurrentMap().getArray()[0].length)	{
			drawnTile = data.getCurrentMap().getArray()[data.getPlayer().getCoordX()][data.getPlayer().getCoordY() + 1];
			if (drawnTile.getDoodad() != null && drawnTile.getDoodad().dominantEh())	{
				g.drawImage(drawnTile.getDoodad().getBackground(), data.getPlayer().getCoordX()*40 - data.getPlayer().getBackgroundX() + drawnTile.getDoodad().getOffsetX(),
						(data.getPlayer().getCoordY() + 1)*40 - data.getPlayer().getBackgroundY() + drawnTile.getDoodad().getOffsetY(), null);
			}
		}
		//Down Right
		if (data.getPlayer().getCoordY() + 1 < data.getCurrentMap().getArray()[0].length && data.getPlayer().getCoordX() + 1 < data.getCurrentMap().getArray().length)	{
			drawnTile = data.getCurrentMap().getArray()[data.getPlayer().getCoordX() + 1][data.getPlayer().getCoordY() + 1];
			if (drawnTile.getDoodad() != null && drawnTile.getDoodad().dominantEh())	{
				g.drawImage(drawnTile.getDoodad().getBackground(),	(data.getPlayer().getCoordX() + 1)*40 - data.getPlayer().getBackgroundX() + drawnTile.getDoodad().getOffsetX(), 
						(data.getPlayer().getCoordY() + 1)*40 - data.getPlayer().getBackgroundY() + drawnTile.getDoodad().getOffsetY(), null);
			}
		}	
		//Down Left
		if (data.getPlayer().getCoordY() + 1 < data.getCurrentMap().getArray()[0].length && data.getPlayer().getCoordX() - 1 > 0)	{
			drawnTile = data.getCurrentMap().getArray()[data.getPlayer().getCoordX() - 1][data.getPlayer().getCoordY() + 1];
			if (drawnTile.getDoodad() != null && drawnTile.getDoodad().dominantEh())	{
				g.drawImage(drawnTile.getDoodad().getBackground(),	(data.getPlayer().getCoordX() - 1)*40 - data.getPlayer().getBackgroundX() + drawnTile.getDoodad().getOffsetX(), 
						(data.getPlayer().getCoordY() + 1)*40 - data.getPlayer().getBackgroundY() + drawnTile.getDoodad().getOffsetY(), null);
			}
		}
		//Double Down
		if (data.getPlayer().getCoordY() + 2 < data.getCurrentMap().getArray()[0].length)	{
			drawnTile = data.getCurrentMap().getArray()[data.getPlayer().getCoordX()][data.getPlayer().getCoordY() + 2];
			if (drawnTile.getDoodad() != null && drawnTile.getDoodad().dominantEh())	{
				g.drawImage(drawnTile.getDoodad().getBackground(), data.getPlayer().getCoordX()*40 - data.getPlayer().getBackgroundX() + drawnTile.getDoodad().getOffsetX(),
						(data.getPlayer().getCoordY() + 2)*40 - data.getPlayer().getBackgroundY() + drawnTile.getDoodad().getOffsetY(), null);
			}
		}
		if (data.getTextBox().visibleEh())	{
//			this.paintAll(g);
		}
	}
}