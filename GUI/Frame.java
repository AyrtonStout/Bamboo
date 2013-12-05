package GUI;

import javax.swing.*;

/**
 * @author mobius
 * @version 0.20 - NPC Update
 */
public class Frame {

	public final int WINDOW_HEIGHT = 600;
	public final int WINDOW_WIDTH = 600;
	
	public Frame(){
		JFrame frame = new JFrame();
		GameData data = new GameData(WINDOW_WIDTH, WINDOW_HEIGHT);
		Board gameBoard = new Board(data);
		InputManager input = new InputManager(data);

		frame.add(input);
		frame.add(gameBoard);	
		frame.setTitle("Project Bamboo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);	
		frame.setLocationRelativeTo(null);
		
	}
	public static void main(String[] args){
		new Frame();
		
		
	}
}