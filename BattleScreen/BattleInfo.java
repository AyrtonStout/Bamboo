package BattleScreen;

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

/**
 * @author mobius
 * This is the bar that appears on the top of the battle screen that displays additional information about
 * the combatant. The bar is not visible when a combatant is not selected.
 */
public class BattleInfo extends JPanel {
	
	private static final long serialVersionUID = 5837665613319006998L;
	private JLabel name = new JLabel();
	private JLabel health = new JLabel();
	private JLabel mana = new JLabel();
	private JLabel level = new JLabel();
	
	private JLabel text = new JLabel();
	
	private boolean visible = false;
	private boolean textMode = false;
	
	private JPanel normalModeContents = new JPanel();
	private JPanel textModeContents = new JPanel();
	
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
		
		JPanel middle = new JPanel();
		middle.setPreferredSize(new Dimension(600, 40));
		middle.setOpaque(false);
		
		JPanel levelWrapper = new JPanel();
		JPanel nameWrapper = new JPanel();
		JPanel healthWrapper = new JPanel();
		JPanel manaWrapper = new JPanel();
		
		levelWrapper.setOpaque(false); nameWrapper.setOpaque(false); 
		healthWrapper.setOpaque(false); manaWrapper.setOpaque(false);
		
		Dimension levelDimension = new Dimension(50, 40);
		levelWrapper.setPreferredSize(levelDimension);
		levelWrapper.setMaximumSize(levelDimension);
		levelWrapper.setMinimumSize(levelDimension);
		Dimension nameDimension = new Dimension(250, 40);
		nameWrapper.setPreferredSize(nameDimension);
		nameWrapper.setMaximumSize(nameDimension);
		nameWrapper.setMinimumSize(nameDimension);
		Dimension healthDimension = new Dimension(130, 40);
		healthWrapper.setPreferredSize(healthDimension);
		healthWrapper.setMaximumSize(healthDimension);
		healthWrapper.setMinimumSize(healthDimension);
		Dimension manaDimension = new Dimension(130, 40);
		manaWrapper.setPreferredSize(manaDimension);
		manaWrapper.setMaximumSize(manaDimension);
		manaWrapper.setMinimumSize(manaDimension);
		
		level.setFont(infoFont); name.setFont(infoFont); health.setFont(infoFont); mana.setFont(infoFont);
		levelWrapper.setLayout(new BoxLayout(levelWrapper, BoxLayout.X_AXIS));
		nameWrapper.setLayout(new BoxLayout(nameWrapper, BoxLayout.X_AXIS));
		healthWrapper.setLayout(new BoxLayout(healthWrapper, BoxLayout.X_AXIS));
		manaWrapper.setLayout(new BoxLayout(manaWrapper, BoxLayout.X_AXIS));
		
		levelWrapper.add(level); nameWrapper.add(name); healthWrapper.add(health); manaWrapper.add(mana);
		
		middle.setLayout(new BoxLayout(middle, BoxLayout.X_AXIS));
		middle.add(Box.createHorizontalStrut(10));
		middle.add(levelWrapper);
		middle.add(nameWrapper); 
		middle.add(healthWrapper); 
		middle.add(manaWrapper);
		middle.add(Box.createHorizontalStrut(30));
		
		normalModeContents.setLayout(new BoxLayout(normalModeContents, BoxLayout.Y_AXIS));
		normalModeContents.setOpaque(false);
		Dimension fullDimension = new Dimension(600, 50);
		normalModeContents.setPreferredSize(fullDimension);
		normalModeContents.setMaximumSize(fullDimension);
		normalModeContents.setMinimumSize(fullDimension);
		
		normalModeContents.add(Box.createVerticalStrut(5));
		normalModeContents.add(middle);
		normalModeContents.add(Box.createVerticalStrut(5));
		
		textModeContents.setLayout(new BoxLayout(textModeContents, BoxLayout.Y_AXIS));
		textModeContents.setOpaque(false);
		textModeContents.setPreferredSize(fullDimension);
		textModeContents.setMaximumSize(fullDimension);
		textModeContents.setMinimumSize(fullDimension);
		
		JPanel textWrapper = new JPanel();
		textWrapper.setOpaque(false);
		Dimension textDimension = new Dimension(600, 40);
		textWrapper.setPreferredSize(textDimension);
		textWrapper.setMaximumSize(textDimension);
		textWrapper.setMinimumSize(textDimension);
		textWrapper.setLayout(new BoxLayout(textWrapper, BoxLayout.X_AXIS));
		textWrapper.add(Box.createHorizontalStrut(10));
		text.setFont(infoFont);
		textWrapper.add(text);
		
		textModeContents.add(Box.createVerticalStrut(5));
		textModeContents.add(textWrapper);
		textModeContents.add(Box.createVerticalStrut(5));
		
		this.add(normalModeContents);
		this.setVisible(false);

	}
	
	/**
	 * Sets the target that the info bar is displaying information on
	 * 
	 * @param target The new target of the info bar
	 */
	public void setTarget(Combatant target)	{
		if (textMode)	{
			textModeContents.setVisible(false);
			this.remove(textModeContents);
			this.add(normalModeContents);
			normalModeContents.setVisible(true);
			textMode = false;
		}
		
		level.setText("L:" + target.getLevel());
		name.setText(target.getName());
		health.setText(target.getCurrentHealth().getActual() + "/" + target.getMaxHealth().getActual() + "HP");
		mana.setText(target.getCurrentMana().getActual() + "/" + target.getMaxMana().getActual() + "MP");
	}
	
	public void setText(String str)	{

		if (!textMode)	{
			normalModeContents.setVisible(false);
			this.remove(normalModeContents);
			this.add(textModeContents);
			textModeContents.setVisible(true);
			textMode = true;
		}
		text.setText(str);
	}
	
	public void setVisible(boolean b)	{
		visible = b;
		
		if (b == true)	{
			level.setVisible(true);
			name.setVisible(true);
			health.setVisible(true);
			mana.setVisible(true);
			text.setVisible(true);
		}
		else	{
			level.setVisible(false);
			name.setVisible(false);
			health.setVisible(false);
			mana.setVisible(false);
			text.setVisible(false);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)	{
		if (visible)	{
			g.drawImage(background.getImage(), 0, 0, null);
		}
	}

}
