package GUI;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class InputManager extends JPanel {

	GameData data;

	public InputManager(GameData data)	{
		this.data = data;
		this.addKeyListener(new AL());
		this.setFocusable(true);
		this.setOpaque(false);
//		this.setVisible(false);
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

}