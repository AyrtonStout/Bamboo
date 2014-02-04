package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import GUI.Enums.GAME_STATE;
import Systems.Enemy;
import Systems.GameData;

public class BattleScreen extends JPanel {

	private static final long serialVersionUID = 9019740276603325359L;
	GameData data;
	Enemy enemy;
	
	private BattleArea battleArea = new BattleArea();
	private Menu menu = new Menu();
	
	public BattleScreen(GameData data)	{
		this.data = data;
		
		Dimension screenSize = new Dimension(600, 600);
		this.setPreferredSize(screenSize);
		this.setMaximumSize(screenSize);
		this.setMinimumSize(screenSize);
		
		this.setLayout(new BorderLayout());
		this.add(battleArea, BorderLayout.NORTH);
		this.add(menu, BorderLayout.SOUTH);
	}
	
	public void enterBattle(Enemy enemy)	{
		data.setGameState(GAME_STATE.BATTLE);
		data.getMenu().setVisible(true);         //Needed for some unknown stupid reason
		data.getMenu().setVisible(false);
		data.getMenu().shrink();
		data.getDialogueBox().shrink();
		data.getGameBoard().add(this);
//		data.getStatisticsPanel().update();
		
		this.enemy = enemy;
	}
	
	public void leaveBattle()	{
		
	}
	
	@Override
	protected void paintComponent(Graphics g)	{
		super.paintComponent(g);
		
	}
	
	private class BattleArea extends JPanel	{

		private static final long serialVersionUID = 1081923729370436576L;

		public BattleArea()	{
			Dimension screenSize = new Dimension(600, 450);
			this.setPreferredSize(screenSize);
			this.setMaximumSize(screenSize);
			this.setMinimumSize(screenSize);

		}
		
		@Override
		protected void paintComponent(Graphics g)	{
			g.drawImage(enemy.getPicture().getImage(), 350, 115, null);
		}
		
	}
	
	private class Menu extends JPanel	{

		private static final long serialVersionUID = 7952290532732938184L;
		private ImageIcon background = new ImageIcon("GUI/Resources/TextBox_Background.png");
		
		public Menu()	{
			Dimension screenSize = new Dimension(600, 150);
			this.setPreferredSize(screenSize);
			this.setMaximumSize(screenSize);
			this.setMinimumSize(screenSize);

		}
		
		@Override
		protected void paintComponent(Graphics g)	{
			g.drawImage(background.getImage(), 0, 0, null);
		}
		
	}
	
}
