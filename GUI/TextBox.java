package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TextBox extends JPanel {
	
//	private final int locationY = 450;
//	private final int verticalSize = 150;
	private boolean visible = true;
	private ImageIcon background = new ImageIcon("GUI/Resources/TextBox_Background.png");
	private ArrayList<String> dialogue = new ArrayList<String>();
	
	JLabel label1 = new JLabel();
	
	Font font1 = new Font("Book Antiqua", Font.BOLD, 30);
	
	public TextBox()	{
		label1.setFont(font1);
		this.setPreferredSize(new Dimension(600, 150));
		this.add(label1);
		this.setBackground(Color.WHITE);
		
		this.setVisible(false);
	}
	
	 @Override
	 protected void paintComponent(Graphics g) {
	     super.paintComponent(g); // paint the background image and scale it to fill the entire space
	     if (visible == true)	{
	    	 g.drawImage(background.getImage(), 0, 0, null);
	     }
	 }

	public boolean visibleEh() {
		if (visible == true)
			return true;
		return false;
	}

	public void setVisible(boolean b) {
		visible = b;
		if (b == true)	{
			this.setOpaque(true);
			label1.setVisible(true);
		}
		else	{
			this.setOpaque(false);
			label1.setVisible(false);
		}
		
	}

	public void read() {
		label1.setText(dialogue.remove(0));		
	}

	public void setDialogue(ArrayList<String> dialogue) {
		this.dialogue = dialogue;		
	}
	public boolean hasNextLine()	{
		if (dialogue.size() > 0)
			return true;
		else
			return false;
	}
}
