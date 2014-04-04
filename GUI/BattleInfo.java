package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Systems.Combatant;

public class BattleInfo extends JPanel {
	
	private static final long serialVersionUID = 5837665613319006998L;
	private JLabel name = new JLabel();
	private JLabel health = new JLabel();
	private JLabel mana = new JLabel();
	
	private boolean visible = false;
	
	private ImageIcon background = new ImageIcon("GUI/Resources/BattleInfo_Background.png");
	
	private Font infoFont;
	
	public BattleInfo()	{
		Dimension dimension = new Dimension(600, 50);
		this.setPreferredSize(dimension);
		this.setMaximumSize(dimension);
		this.setMinimumSize(dimension);
	
		InputStream stream;
		Font baseFont;
		
		try {
			stream = new BufferedInputStream(
					new FileInputStream("GUI/Resources/Font_Arial.ttf"));
			baseFont = Font.createFont(Font.TRUETYPE_FONT, stream);
			infoFont = baseFont.deriveFont(Font.PLAIN, 20);

		} catch (FontFormatException | IOException e) {
			System.err.println("Use your words!! Font not found");
			e.printStackTrace();
		}
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel buffer = new JPanel();
		buffer.setPreferredSize(new Dimension(600, 5));
		JPanel middle = new JPanel();
		middle.setPreferredSize(new Dimension(600, 40));
		JPanel buffer2 = new JPanel();
		buffer2.setPreferredSize(new Dimension(600, 5));
		
		buffer.setOpaque(false); middle.setOpaque(false); buffer2.setOpaque(false);
		
		JPanel nameWrapper = new JPanel();
		JPanel healthWrapper = new JPanel();
		JPanel manaWrapper = new JPanel();
		
		nameWrapper.setOpaque(false); healthWrapper.setOpaque(false); manaWrapper.setOpaque(false);
		Dimension nameDimension = new Dimension(280, 40);
		nameWrapper.setPreferredSize(nameDimension);
		nameWrapper.setMaximumSize(nameDimension);
		nameWrapper.setMinimumSize(nameDimension);
		Dimension healthDimension = new Dimension(140, 40);
		healthWrapper.setPreferredSize(healthDimension);
		healthWrapper.setMaximumSize(healthDimension);
		healthWrapper.setMinimumSize(healthDimension);
		Dimension manaDimension = new Dimension(140, 40);
		manaWrapper.setPreferredSize(manaDimension);
		manaWrapper.setMaximumSize(manaDimension);
		manaWrapper.setMinimumSize(manaDimension);
		
		name.setFont(infoFont); health.setFont(infoFont); mana.setFont(infoFont);
		nameWrapper.setLayout(new BoxLayout(nameWrapper, BoxLayout.X_AXIS));
		healthWrapper.setLayout(new BoxLayout(healthWrapper, BoxLayout.X_AXIS));
		manaWrapper.setLayout(new BoxLayout(manaWrapper, BoxLayout.X_AXIS));
		
		nameWrapper.add(name); healthWrapper.add(health); manaWrapper.add(mana);
		
		middle.setLayout(new BoxLayout(middle, BoxLayout.X_AXIS));
		middle.add(Box.createHorizontalStrut(10));
		middle.add(nameWrapper); 
		middle.add(healthWrapper); 
		middle.add(manaWrapper);
		middle.add(Box.createHorizontalStrut(30));
		
		this.add(buffer);
		this.add(middle);
		this.add(buffer2);
	}
	
	public void setTarget(Combatant target)	{
		name.setText(target.getName());
		health.setText(target.getCurrentHealth().getActual() + "/" + target.getMaxHealth().getActual() + "HP");
		mana.setText(target.getCurrentMana().getActual() + "/" + target.getMaxMana().getActual() + "MP");
	}
	
	public void setVisible(boolean b)	{
		visible = b;
		
		if (b == true)	{
			name.setVisible(true);
			health.setVisible(true);
			mana.setVisible(true);
		}
		else	{
			name.setVisible(false);
			health.setVisible(false);
			mana.setVisible(false);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)	{
		if (visible)	{
			g.drawImage(background.getImage(), 0, 0, null);
		}
	}

}
