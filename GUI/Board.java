package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
 
@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {

	private ArrayList<Character> characters = new ArrayList<Character>();
	private int gameState = 0;
	private Character player = new Character();
	private ImageIcon i = new ImageIcon("GUI/background.jpg");
	private Image background = i.getImage();
	private Timer time;
	private int FPS = 60;

	public Board() {
		addKeyListener(new AL());
		setFocusable(true);
		time = new Timer((int)(1000 / FPS), this);
		time.start();
		
		characters.add(player);
	}

	//Timer's event
	public void actionPerformed(ActionEvent e) {
		player.update();
		repaint();
	}

	public void paint(Graphics g) {

		g.drawImage(background, -player.getX(), 0, null);

		g.drawImage(player.getImage(), player.getCharX(), player.getCharY(), null);

	}

	private class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			if (gameState == 0)	{
				if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || 
						e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)	{
					player.move(e);
				}
			}
		}

		public void keyReleased(KeyEvent e) {
			player.cancelMove();
		}
	}
}