package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PartyPanel extends JPanel {
	
	private static final long serialVersionUID = 9030554443534484268L;

	private GameData data;
	private ImageIcon party1, party2, party3, party4;
	
	public PartyPanel(GameData gameData)	{
		data = gameData;
		this.setPreferredSize(new Dimension(600, 600));
		this.setBackground(Color.CYAN);
		
	}

//	@Override
//	protected void paintComponent(Graphics g)	{
//		g.drawImage(party1.getImage(), 50, 0, null);
//	}
	
	public void initialize()	{
		for (int i = 0; i < data.getParty().length; i++)	{
			if (data.getParty()[i] != null)	{
				party1 = data.getParty()[i].getPortrait();
			}
		}
	}

}
