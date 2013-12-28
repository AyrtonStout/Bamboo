package GUI;

import javax.swing.*;

import Systems.InputManager;

/**
 * @author mobius
 * @version 0.25 - Party Update
 */
public class Frame extends JFrame {

	private static final long serialVersionUID = 7994551050627642940L;

	public final int WINDOW_HEIGHT = 600;
	public final int WINDOW_WIDTH = 600;
	
	public Frame(){
		GameData data = new GameData(WINDOW_WIDTH, WINDOW_HEIGHT);
		Board gameBoard = new Board(data);
		data.setGameBoard(gameBoard);
		InputManager input = new InputManager(data);
		data.setInputManager(input);
		
		this.add(input);
		this.add(gameBoard);	
		this.setTitle("Project Bamboo");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		this.pack();
		this.setVisible(true);	
		this.setLocationRelativeTo(null);
		
	}
	
	public static void main(String[] args){
		new Frame();
	}
}