package GUI;

import javax.swing.*;

/**
 * @author mobius
 * Version 0.12
 */
public class Frame {

	public final int WINDOW_HEIGHT = 600;
	public final int WINDOW_WIDTH = 600;
	
	public Frame(){
		JFrame frame = new JFrame();
		frame.add(new Board(WINDOW_WIDTH, WINDOW_HEIGHT));	
		frame.setTitle("Project Bamboo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	public static void main(String[] args){
		new Frame();
	}
}