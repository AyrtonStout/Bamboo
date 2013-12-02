package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class TextBox extends JPanel{
	
//	private final int locationY = 450;
//	private final int verticalSize = 150;
	private boolean visible = true;
	private boolean writing = false;
	private ImageIcon background = new ImageIcon("GUI/Resources/TextBox_Background.png");
	private ArrayList<String> dialogue = new ArrayList<String>();
	private String currentString = "", pendingString;
	private final int FRAME_SKIP = 1;
	private int currentFrame = 0;
	
	
	JLabel label1 = new JLabel("", SwingConstants.LEFT);
	JLabel label2 = new JLabel("", SwingConstants.LEFT);
	
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	
	
	Font font1 = new Font("Book Antiqua", Font.BOLD, 30);
	
	public TextBox()	{
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		panel1.setOpaque(false);
		panel2.setOpaque(false);
		
		panel1.setAlignmentX(LEFT_ALIGNMENT);
		panel1.add(Box.createHorizontalStrut(20));
		
		panel2.setAlignmentX(LEFT_ALIGNMENT);
		panel2.add(Box.createHorizontalStrut(20));
		
		panel1.setPreferredSize(new Dimension(600, 75));
		panel2.setPreferredSize(new Dimension(600, 75));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		label1.setFont(font1);
		label2.setFont(font1);
		this.setPreferredSize(new Dimension(600, 150));
		panel1.add(label1);
		panel2.add(label2);
		this.add(panel1);
		this.add(panel2);
		
		this.setVisible(false);
	}
	
	 @Override
	 protected void paintComponent(Graphics g) {
	     //super.paintComponent(g);
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
			label2.setVisible(true);
		}
		else	{
			this.setOpaque(false);
			label1.setVisible(false);
			label2.setVisible(false);
		}
		
	}

	public void update()	{
		if (writing)	{
			if (currentFrame == FRAME_SKIP)	{
				label1.setText(currentString += pendingString.charAt(currentString.length()));
				currentFrame = 0;
			}
			else	{
				currentFrame++;
			}
		}
		if (currentString.length() == pendingString.length())	{
			writing = false;
			currentString = "";
		}
		
	}
	
	public void read() {

		label1.setText(currentString);
		label2.setText(currentString);
		pendingString = (dialogue.remove(0));		
	}

	public void setDialogue(ArrayList<String> dialogue) {
		this.dialogue = dialogue;		
	}
	public void setWriting(boolean b)	{
		writing = b;
	}
	public boolean writingEh()	{
		return writing;
	}
	public boolean hasNextLine()	{
		if (dialogue.size() > 0)
			return true;
		else
			return false;
	}
}
