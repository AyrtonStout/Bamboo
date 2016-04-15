package BattleScreen;

import BattleScreen.Enums.COMBAT_START;
import Systems.Combatant;
import Systems.Enemy;
import Systems.GameData;
import Systems.PartyMember;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author mobius
 *         <p>
 *         This is the component on the battle screen that calculates and draws the order that the characters
 *         take their turn during combat
 */
public class BattleTurnOrder extends JPanel {

	private static final long serialVersionUID = -4464976184980976130L;
	private final int PREDICTIVE_SIZE = 12;
	private GameData data;
	private BattleScreen battleScreen;
	private ImageIcon friendlyBackground = new ImageIcon("GUI/Resources/TurnOrder_Friendly.png");
	private ImageIcon enemyBackground = new ImageIcon("GUI/Resources/TurnOrder_Enemy.png");
	private ArrayList<Combatant> turns = new ArrayList<Combatant>();
	private ArrayList<ImageIcon> turnIcons = new ArrayList<ImageIcon>();
	private int baseTurnMaximum = 0;
	private ArrayList<Combatant> combatants = new ArrayList<Combatant>();

	private boolean visible = true;

	public BattleTurnOrder(GameData data, BattleScreen screen) {
		this.data = data;
		battleScreen = screen;

		Dimension screenSize = new Dimension(600, 70);
		this.setPreferredSize(screenSize);
		this.setMaximumSize(screenSize);
		this.setMinimumSize(screenSize);
		this.setOpaque(false);
	}

	/**
	 * Prepares the turn order for a new battle. Calculates the relative speed of all participants and places
	 * them in order
	 */
	public void initialize() {
		for (int i = 0; i < PREDICTIVE_SIZE; i++) {
			turnIcons.add(new ImageIcon());
		}
		for (int i = 0; i < data.getParty().length; i++) {
			if (data.getParty()[i] != null) {
				combatants.add(data.getParty()[i]);
				if (data.getParty()[i].getSpeed().getActual() > baseTurnMaximum) {
					baseTurnMaximum = data.getParty()[i].getSpeed().getActual();
				}
			}
		}

		for (int i = 0; i < battleScreen.getEnemies().toArrayList().size(); i++) {
			combatants.add(battleScreen.getEnemies().toArrayList().get(i));
			if (battleScreen.getEnemies().toArrayList().get(i).getSpeed().getActual() > baseTurnMaximum) {
				baseTurnMaximum = battleScreen.getEnemies().toArrayList().get(i).getSpeed().getActual();
			}
		}

		for (int i = 0; i < combatants.size(); i++) {
			combatants.get(i).setTurnMaximum(2 * baseTurnMaximum - combatants.get(i).getSpeed().getActual());
			if (battleScreen.getStartingCondition() == COMBAT_START.AMBUSH) {
				if (combatants.get(i).getClass() == PartyMember.class) {
					combatants.get(i).setTurnPriority(combatants.get(i).getTurnMaximum());
					combatants.get(i).setTurnPriorityPrediction(combatants.get(i).getTurnMaximum());
				} else if (combatants.get(i).getClass() == Enemy.class) {
					combatants.get(i).setTurnPriority(combatants.get(i).getTurnMaximum() / 5);
					combatants.get(i).setTurnPriorityPrediction(combatants.get(i).getTurnMaximum() / 5);
				}
			} else if (battleScreen.getStartingCondition() == COMBAT_START.NORMAL) {
				combatants.get(i).setTurnPriority(combatants.get(i).getTurnMaximum() / 2);
				combatants.get(i).setTurnPriorityPrediction(combatants.get(i).getTurnMaximum() / 2);
			} else if (battleScreen.getStartingCondition() == COMBAT_START.PREEMPTIVE) {
				if (combatants.get(i).getClass() == PartyMember.class) {
					combatants.get(i).setTurnPriority(combatants.get(i).getTurnMaximum() / 5);
					combatants.get(i).setTurnPriorityPrediction(combatants.get(i).getTurnMaximum() / 2);
				} else if (combatants.get(i).getClass() == Enemy.class) {
					combatants.get(i).setTurnPriority(combatants.get(i).getTurnMaximum());
					combatants.get(i).setTurnPriorityPrediction(combatants.get(i).getTurnMaximum());
				}
			}
		}
		calculateTurnOrder();
		dropTurnPriority();
	}

	/**
	 * Resets the information to zero after a battle has concluded
	 */
	public void clear() {
		int size = combatants.size();
		for (int i = 0; i < size; i++) {
			combatants.remove(0);
		}
		baseTurnMaximum = 0;
		for (int i = 0; i < PREDICTIVE_SIZE; i++) {
			turnIcons.remove(0);
		}
	}

	/**
	 * Updates the displayed images to reflect the calculated turn order
	 */
	private void updateImages() {
		for (int i = 0; i < turnIcons.size(); i++) {
			turnIcons.set(i, turns.get(i).getBattlePicture());
		}
	}

	/**
	 * Calculates the turn order given that the current character takes a particular
	 * action. For example, a character attempting to run from battle, or a character using a "quick hit"
	 * type ability should not use up their full turn. This method should be used to show the correct
	 * result in the turn bar.\n
	 * <p>
	 * A parameter of 1 predicts turn order as if the character uses a normal move, meaning that the
	 * calculateTurnOrder() method should be used instead.
	 *
	 * @param prediction 0 for a quick action, 1 for a normal action, and 2 for a long action
	 */
	public void predictTurnOrder(int prediction) {
		for (int i = 0; i < combatants.size(); i++) {
			combatants.get(i).setTurnMaximumPrediction(1);
			combatants.get(i).setTurnPriorityPrediction(-1);
		}
		turns.get(0).setTurnPriorityPrediction(prediction);

		int size = turns.size();
		for (int i = 0; i < size - 1; i++) {
			turns.remove(1);
		}

		for (int i = 0; i < PREDICTIVE_SIZE - 1; i++) {
			Combatant fastest = null;
			for (int j = 0; j < combatants.size(); j++) {
				if (combatants.get(j).aliveEh()) {
					Combatant compare = combatants.get(j);
					if (fastest == null || compare.getPredictiveSpeedMod() < fastest.getPredictiveSpeedMod()) {
						fastest = compare;
					}
				}
			}
			fastest.incrementTurnPrediction();
			turns.add(fastest);
		}
		for (int i = 0; i < combatants.size(); i++) {
			combatants.get(i).clearTurnPrediction();
		}

		updateImages();
	}

	/**
	 * Calculates the turn order of all combatants as if they use normal priority moves and updates the screen
	 * to reflect the new order.
	 */
	public void calculateTurnOrder() {
		int size = turns.size();
		for (int i = 0; i < size; i++) {
			turns.remove(0);
		}

		for (int i = 0; i < PREDICTIVE_SIZE; i++) {
			Combatant fastest = null;
			for (int j = 0; j < combatants.size(); j++) {
				if (combatants.get(j).aliveEh()) {
					Combatant compare = combatants.get(j);
					if (fastest == null || compare.getPredictiveSpeedTrue() < fastest.getPredictiveSpeedTrue()) {
						fastest = compare;
					}
				}
			}
			fastest.incrementTurnPrediction();
			turns.add(fastest);
		}
		for (int i = 0; i < combatants.size(); i++) {
			combatants.get(i).clearTurnPrediction();
		}
		updateImages();
	}

	/**
	 * Returns the first combatant in the turn queue
	 *
	 * @return The combatant who is taking their turn
	 */
	public Combatant getActiveCombatant() {
		return turns.get(0);
	}

	/**
	 * To be used whenever a combatant completes its turn. This method then updates the target's turn priority and moves
	 * the turn on to the next combatant in the queue
	 *
	 * @param priority The speed of the move the combatant used. 0 for a fast move, 1 for a normal move, and 2 for a slow move
	 */
	public void endCombatantTurn(int priority) {
		//TODO probably rewrite this to be more specific
		if (priority == 0) {
			turns.get(0).setTurnPriority(turns.get(0).getTurnMaximum() / 4);
		} else if (priority == 1) {
			turns.get(0).setTurnPriority(turns.get(0).getTurnMaximum() / 2);
		} else if (priority == 2) {
			turns.get(0).setTurnPriority(turns.get(0).getTurnMaximum());
		}

		calculateTurnOrder();
		dropTurnPriority();

		//This will automatically predict the turn order for a Run command if the last command was a Run
		if (battleScreen.getBattleMenu().getCursorPosition() == 3) {
			predictTurnOrder(0);
		}
	}

	/**
	 * This method will take the turn priority of the person who is going first, and drop
	 * the turn priority of all combatants by that amount (putting the person going first
	 * at 0 turn priority)
	 */
	private void dropTurnPriority() {
		int fastestSpeed = turns.get(0).getTurnPriority();
		for (int i = 0; i < data.getParty().length; i++) {
			if (data.getParty()[i] != null) {
				data.getParty()[i].setTurnPriority(data.getParty()[i].getTurnPriority() - fastestSpeed);
			}
		}
		ArrayList<Enemy> enemies = battleScreen.getEnemies().toArrayList();
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).setTurnPriority(enemies.get(i).getTurnPriority() - fastestSpeed);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (visible) {
			for (int i = 0; i < turnIcons.size(); i++) {
				if (turns.get(i).getClass() == PartyMember.class) {
					g.drawImage(friendlyBackground.getImage(), i * 50, 18, null);
					g.drawImage(turnIcons.get(i).getImage(), 10 + (i * 50), 21, null);
				} else {
					g.drawImage(enemyBackground.getImage(), i * 50, 18, null);
					g.drawImage(turnIcons.get(i).getImage(), 1 + i * 50, 21, null);
				}
			}
		}
	}

	/**
	 * @return Whether or not this component is visible
	 */
	public boolean visibleEh() {
		return visible;
	}

	@Override
	public void setVisible(boolean b) {
		visible = b;
	}
}
