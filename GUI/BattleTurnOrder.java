package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Systems.Combatant;
import Systems.Enemy;
import Systems.GameData;
import Systems.PartyMember;
import Systems.Enums.COMBAT_START;

	public class BattleTurnOrder extends JPanel	{

		private static final long serialVersionUID = -4464976184980976130L;
		private GameData data;
		private BattleScreen battleScreen;
		
		private ArrayList<Combatant> turns = new ArrayList<Combatant>();
		private ArrayList<ImageIcon> turnIcons = new ArrayList<ImageIcon>();
		
		private int baseTurnMaximum = 0;
		private final int PREDICTIVE_SIZE = 12;
		
		ArrayList<Combatant> combatants = new ArrayList<Combatant>();
		
		public BattleTurnOrder(GameData data, BattleScreen screen)	{
			this.data = data;
			battleScreen = screen;
			
			Dimension screenSize = new Dimension(600, 50);
			this.setPreferredSize(screenSize);
			this.setMaximumSize(screenSize);
			this.setMinimumSize(screenSize);		
		}
		
		public void initialize()	{
			for (int i = 0; i < PREDICTIVE_SIZE; i++)	{
				turnIcons.add(new ImageIcon());
			}
			for (int i = 0; i < data.getParty().length; i++)	{
				if (data.getParty()[i] != null)	{
					combatants.add(data.getParty()[i]);
					if (data.getParty()[i].getSpeed().getActual() > baseTurnMaximum)	{
						baseTurnMaximum = data.getParty()[i].getSpeed().getActual();
					}
				}
			}
			
			for (int i = 0; i < battleScreen.getEnemies().toArrayList().size(); i++)	{
				combatants.add(battleScreen.getEnemies().toArrayList().get(i));
				if (battleScreen.getEnemies().toArrayList().get(i).getSpeed().getActual() > baseTurnMaximum)	{
					baseTurnMaximum = battleScreen.getEnemies().toArrayList().get(i).getSpeed().getActual();
				}
			}
			
			for (int i = 0; i < combatants.size(); i++)	{
				combatants.get(i).setTurnMaximum(2 * baseTurnMaximum - combatants.get(i).getSpeed().getActual());
				if (battleScreen.getStartingCondition() == COMBAT_START.AMBUSH)	{
					if (combatants.get(i).getClass() == PartyMember.class)	{
						combatants.get(i).setTurnPriority(combatants.get(i).getTurnMaximum());
					}
					else if (combatants.get(i).getClass() == Enemy.class)	{
						combatants.get(i).setTurnPriority(combatants.get(i).getTurnMaximum()/5);
					}
				}
				else if (battleScreen.getStartingCondition() == COMBAT_START.NORMAL)	{
					combatants.get(i).setTurnPriority(combatants.get(i).getTurnMaximum()/2);
				}
				else if (battleScreen.getStartingCondition() == COMBAT_START.PREEMPTIVE)	{
					if (combatants.get(i).getClass() == PartyMember.class)	{
						combatants.get(i).setTurnPriority(combatants.get(i).getTurnMaximum()/5);
					}
					else if (combatants.get(i).getClass() == Enemy.class)	{
						combatants.get(i).setTurnPriority(combatants.get(i).getTurnMaximum());
					}
				}
			}
			calculateTurnOrder();
		}
		
		public void clear()	{
			int size = combatants.size();
			for (int i = 0; i < size; i++)	{
				combatants.remove(0);
			}
			baseTurnMaximum = 0;
			for (int i = 0; i < PREDICTIVE_SIZE; i++)	{
				turnIcons.remove(0);
			}
		}
		
		public void updateImages()	{
			for (int i = 0; i < turnIcons.size(); i++)	{
				turnIcons.set(i, turns.get(i).getBattlePicture());
			}
		}
		
		public void calculateTurnOrder()	{
			int size = turns.size();
			for (int i = 0; i < size; i++)	{
				turns.remove(0);
			}
			
			for (int i = 0; i < PREDICTIVE_SIZE; i++)	{
				Combatant fastest = null;
				for (int j = 0; j < combatants.size(); j++)	{
					if (combatants.get(j).aliveEh())	{
						Combatant compare = combatants.get(j);
						if (fastest == null || compare.getPredictiveSpeed() < fastest.getPredictiveSpeed())	{
							System.out.println(compare.getName() + "  " + compare.getPredictiveSpeed() + "/" + 
									compare.getTurnMaximum());
							fastest = compare;
						}
					}
				}
				fastest.incrementTurnPrediction();
				turns.add(fastest);
			}
			for (int i = 0; i < combatants.size(); i++)	{
				combatants.get(i).clearTurnPrediction();
			}
			updateImages();
		}
		
		@Override
		protected void paintComponent(Graphics g)	{
			for (int i = 0; i < turnIcons.size(); i++)	{
				if (turns.get(i).getClass() == PartyMember.class)	{
					g.drawImage(turnIcons.get(i).getImage(), 10 + (i * 50), 0, null);
				}
				else	{
					g.drawImage(turnIcons.get(i).getImage(), i * 50, 0, null);
				}
			}
		}

	}
