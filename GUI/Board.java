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
//	private JPanel dialogBox = new JPanel();
	
	public Board(int windowWidth, int windowHeight) {
		data = new GameData(windowWidth, windowHeight);
		addKeyListener(new AL());
		setFocusable(true);
		time = new Timer((int)(1000 / FPS), this);
		time.start();
	}

	//Timer's event
	public void actionPerformed(ActionEvent e) {
		data.update();
		repaint();
	}

	public void paint(Graphics g) {
		data.getCurrentMap().drawImage(g, data.getPlayer());
		if (data.getTextBox().visibleEh())	{
			data.getTextBox().paintComponent(g);
		}
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
		}

		/* 
		 * Cancels the character's queued action causing it to stand still after its current action completes
		 */
		public void keyReleased(KeyEvent e) {
			data.getPlayer().cancelMove();
			//TODO Fix bug where releasing a key other than the arrow keys will cause the player to quit moving
		}
	}
}