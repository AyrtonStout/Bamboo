package GUI;

import java.awt.Dimension;

import javax.swing.*;

/**
 * @author mobius
 * @version 0.15 - Sign Update
 */
public class Frame {

	public final int WINDOW_HEIGHT = 600;
	public final int WINDOW_WIDTH = 600;
	
	public Frame(){
		JFrame frame = new JFrame();
		Board gameBoard = new Board(WINDOW_WIDTH, WINDOW_HEIGHT);
		gameBoard.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
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