package GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TextBox extends JPanel {
	
	private final int locationY = 450;
	private final int verticalSize = 150;
	private boolean visible = false;
	private AttributedString top = new AttributedString("hats are cool and fun");
	private String bottom = "hats are cool and fun";
	private ImageIcon background = new ImageIcon("GUI/Resources/TextBox_Background.png");
	private ArrayList<String> dialogue = new ArrayList<String>();
	
	Font font1 = new Font("Book Antiqua", Font.PLAIN, 30);
	
	
	public TextBox()	{
		this.setPreferredSize(new Dimension(600, 150));
		top.addAttribute(TextAttribute.FONT, font1);
	}
	
	@Override
	public void paintComponent(Graphics g)	{
		Graphics2D g2d = (Graphics2D) g;
		
		g.drawImage(background.getImage(), 0, locationY, null);
//		g2d.drawString(top.getIterator(), 30, 500);
		g2d.drawString(bottom,  30,  500);
	}

	public boolean visibleEh() {
		if (visible == true)
			return true;
		return false;
	}

	public void setVisible(boolean b) {
		visible = b;
	}

	public void read() {
		
		
	}
	
	
	
}
