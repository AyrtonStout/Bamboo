package GUI;

import java.awt.KeyboardFocusManager;

import javax.swing.*;

public class Frame {

	public Frame(){
		JFrame frame = new JFrame();
		frame.add(new Board());	
		frame.setTitle("'Working' Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,500);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	public static void main(String[] args){
		new Frame();
	}
}