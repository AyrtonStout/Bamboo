package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class GlassPane extends JPanel {
	
	private static final long serialVersionUID = -7294918537396164938L;
	ImageIcon icon = new ImageIcon("GUI/Resources/Tree_Palm.png");
	private JPanel glassPanel = new JPanel();
	private GameData data;
	
	JTextPane primaryStats = new JTextPane();
	
	private int x = 50;
	private int y = 50;
	private int width = 200;
	private int height = 200;
	
	public GlassPane(GameData data)	{
		this.data = data;
		this.setOpaque(false);
		this.setLayout(null);
		
		glassPanel.setPreferredSize(new Dimension(100, 100));
		glassPanel.setBackground(Color.GRAY);
		
		primaryStats.setEditable(false);
//		primaryStats.setText("Whoooaaa");
		
		JLabel label = new JLabel();
		glassPanel.add(label);
		glassPanel.add(primaryStats);
		
		glassPanel.setBounds(x, y, width, height);
		
		System.out.println(label.getHorizontalTextPosition());

		
		this.add(glassPanel);
	}
	
//	@Override
//	public void paintComponent(Graphics g)	{
//		g.drawImage(icon.getImage(), 50, 50, null);
//	}
	
	public void setX(int newX)	{
		x = newX;
		glassPanel.setBounds(x, y, width, height);
	}
	public void setY(int newY)	{
		y = newY;
		glassPanel.setBounds(x, y, width, height);
	}
	public void setWidth(int newWidth)	{
		width = newWidth;
		glassPanel.setBounds(x, y, width, height);
	}
	public void setHeight(int newHeight)	{
		height = newHeight;
		glassPanel.setBounds(x, y, width, height);
	}

}
