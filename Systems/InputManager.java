package Systems;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import GUI.GameData;
import GUI.Interactable;
import GUI.NPC;
import GUI.Sign;
import GUI.Tile;
import GUI.Enums.GAME_STATE;

public class InputManager extends JPanel {

	private static final long serialVersionUID = -1120571601725209112L;
	GameData data;
	private Inventory inventory = new Inventory();

	public InputManager(GameData data)	{
		this.data = data;
		this.addKeyListener(new AL());
		this.setFocusable(true);
		this.setOpaque(false);
	}

	/**
	 * @author mobius
	 * If the key pressed during the overworld screen (gamestate 0) is an arrow key, this will send the key event
	 * to the character object to queue up its corresponding next action to perform when update() is called. Releasing
	 * the key will clear the queued command
	 */
	private class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			if (data.getGameState() == GAME_STATE.WALK)	{
				if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || 
						e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)	{
					data.getPlayer().move(e);
				}
				else if (e.getKeyCode() == KeyEvent.VK_Z)	{
					activate();
				}
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_ENTER)	{
					data.setGameState(GAME_STATE.MENU);
					data.getMenu().setVisible(true);
				}
				else if (e.getKeyCode() == KeyEvent.VK_G)	{
					data.getCurrentMap().getNPCs().get(0).walkToPoint(new Point(4, data.getCurrentMap().getNPCs().get(0).getCoordY()));
				}
			}
			
			
			else if (data.getGameState() == GAME_STATE.TALK)	{
				if (e.getKeyCode() == KeyEvent.VK_Z)	{
					advanceDialogue();
				}
			}
			
			
			else if (data.getGameState() == GAME_STATE.MENU)	{
				if (e.getKeyCode() == KeyEvent.VK_UP)	{
					data.getMenu().raiseCursor();
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)	{
					data.getMenu().dropCursor();
				}
				else if (e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_ENTER)	{
					if (data.getMenu().getCursorPosition() == 1)	{
						data.setGameState(GAME_STATE.INVENTORY_OUTER);
						data.getMenu().setVisible(false);
						data.getMenu().shrink();
						data.getGameBoard().add(inventory);	
						data.getTextBox().setVisible(true);
					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_X)	{
					data.setGameState(GAME_STATE.WALK);
					data.getMenu().setVisible(false);
				}
			}
			
			else if (data.getGameState() == GAME_STATE.INVENTORY_OUTER)	{
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_X)	{
					data.getGameBoard().remove(inventory);
					data.getMenu().restore();
					data.setGameState(GAME_STATE.MENU);
					data.getMenu().setVisible(true);
					data.getTextBox().setVisible(false);
				}
				else if (e.getKeyCode() == KeyEvent.VK_UP)	{
					inventory.raiseLeftCursor();
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)	{
					inventory.dropLeftCursor();
				}
			}
		}
		/* 
		 * Cancels the character's queued action causing it to stand still after its current action completes
		 */
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || 
					e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)	{
				data.getPlayer().cancelMove(e);
			}
		}
	}
	
	@SuppressWarnings("incomplete-switch")
	public void activate()	{
		if (data.getGameState() == GAME_STATE.WALK)	{
			Tile facedTile = null;
			int facedX = data.getPlayer().getCoordX();
			int facedY = data.getPlayer().getCoordY();
			switch (data.getPlayer().getFacing())	{
			case LEFT:
				facedTile = data.getCurrentMap().getArray()[data.getPlayer().getCoordX() - 1][data.getPlayer().getCoordY()];
				facedX--; break;
			case UP:
				facedTile = data.getCurrentMap().getArray()[data.getPlayer().getCoordX()][data.getPlayer().getCoordY() - 1]; 
				facedY--; break;
			case RIGHT:
				facedTile = data.getCurrentMap().getArray()[data.getPlayer().getCoordX() + 1][data.getPlayer().getCoordY()]; 
				facedX++; break;
			case DOWN:
				facedTile = data.getCurrentMap().getArray()[data.getPlayer().getCoordX()][data.getPlayer().getCoordY() + 1]; 
				facedY++; break;
			}
			if (facedTile.getDoodad() != null)	{
				if(facedTile.getDoodad().getClass() == Interactable.class)	{
					((Interactable) facedTile.getDoodad()).interact();
				}
				else if (facedTile.getDoodad().getClass() == Sign.class)	{
					((Sign) facedTile.getDoodad()).getDialogue();
					data.setGameState(GAME_STATE.TALK);
					data.getTextBox().setVisible(true);
					data.getTextBox().setDialogue(((Sign) facedTile.getDoodad()).getDialogue(), true);
					advanceDialogue();
				}
			}
			NPC interactedNPC;
			for (int i = 0; i < data.getCurrentMap().getNPCs().size(); i++)	{
				interactedNPC = data.getCurrentMap().getNPCs().get(i);
				if (interactedNPC.getCoordX() == facedX && interactedNPC.getCoordY() == facedY && !interactedNPC.movingEh())	{
					data.getTextBox().setDialogue(data.getCurrentMap().getNPCs().get(i).getDialogue(), false);
					interactedNPC.invertFacing(data.getPlayer().getFacing());
					interactedNPC.setTalking(true);
					data.getPlayer().setInteractingNPC(interactedNPC);
					data.setGameState(GAME_STATE.TALK);
					data.getTextBox().setVisible(true);
					advanceDialogue();
				}
			}
		}
	}
	
	public void advanceDialogue()	{
		if (data.getTextBox().writingEh() && !data.getTextBox().writeFasterEh())	{
			data.getTextBox().writeFaster();
		}
		else if (!data.getTextBox().writingEh())	{
			if (data.getTextBox().hasNextLine())	{
				data.getTextBox().read();
			}
			else	{
				data.setGameState(GAME_STATE.WALK);
				if (data.getPlayer().getInteractingNPC() != null)	{
					data.getPlayer().getInteractingNPC().setTalking(false);
					data.getPlayer().setInteractingNPC(null);
				}
				data.getTextBox().setVisible(false);
			}
		}
	}

}