package Systems;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import GUI.GameData;
import GUI.Chest;
import GUI.NPC;
import GUI.Sign;
import GUI.Tile;
import GUI.Enums.GAME_STATE;

public class InputManager extends JPanel {

	private static final long serialVersionUID = -1120571601725209112L;
	private GameData data;
	
	private boolean directAccess;

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
			
			/*
			 * Main game screen
			 */
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
				else if (e.getKeyCode() == KeyEvent.VK_P)	{
					directAccess = true;
					openParty();
				}
				else if (e.getKeyCode() == KeyEvent.VK_I)	{
					directAccess = true;
					openInventory();
				}
				else if (e.getKeyCode() == KeyEvent.VK_G)	{
					for (int i = 0; i < data.getParty().length; i++)	{
						if (data.getParty()[i] != null)	{
							data.getParty()[i].levelUp();
						}
					}
				}
			}
			
			/*
			 * With a text box active
			 */
			else if (data.getGameState() == GAME_STATE.TALK)	{
				if (e.getKeyCode() == KeyEvent.VK_Z)	{
					data.getDialogueBox().advanceDialogue();
				}
			}
			
			
			/*
			 * With the small in-game menu open
			 */
			else if (data.getGameState() == GAME_STATE.MENU)	{
				if (e.getKeyCode() == KeyEvent.VK_UP)	{
					data.getMenu().raiseCursor();
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)	{
					data.getMenu().dropCursor();
				}
				else if (e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_ENTER)	{
					directAccess = false;
					if (data.getMenu().getCursorPosition() == 0)	{
						openParty();
					}
					else if (data.getMenu().getCursorPosition() == 1)	{
						openInventory();
					}
					
				}
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_X)	{
					data.setGameState(GAME_STATE.WALK);
					data.getMenu().setVisible(false);
					data.getMenu().resetCursor();
				}
			}
			
			
			/*
			 * Party screen open
			 */
			else if (data.getGameState() == GAME_STATE.PARTY_PANEL)	{
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_X)	{
					closeParty();
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT)	{
					data.getPartyPanel().moveCursorLeft();
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)	{
					data.getPartyPanel().moveCursorRight();
				}
			}
			
			
			/*
			 * Inventory screen active
			 */
			else if (data.getGameState() == GAME_STATE.INVENTORY_OUTER)	{
				if (e.getKeyCode() == KeyEvent.VK_UP)	{
					data.getInventoryPanel().raiseCategoryCursor();
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)	{
					data.getInventoryPanel().dropCategoryCursor();
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT)	{
					data.getInventoryPanel().moveHeaderCursorLeft();
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)	{
					data.getInventoryPanel().moveHeaderCursorRight();
				}
				else if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_Z)	{
					if (data.getInventory().getCategory(data.getInventoryPanel().getCategoryCursorPosition()).size() > 0)	{
						data.setGameState(GAME_STATE.INVENTORY_INNER);
						data.getInventoryPanel().setInnerActive(true);
					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_X)	{
					closeInventory();
				}
			}
			
			else if (data.getGameState() == GAME_STATE.INVENTORY_INNER)	{
				if (e.getKeyCode() == KeyEvent.VK_UP)	{
					data.getInventoryPanel().raiseItemCursor();
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)	{
					data.getInventoryPanel().dropItemCursor();
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT)	{
					data.getInventoryPanel().moveHeaderCursorLeft();
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)	{
					data.getInventoryPanel().moveHeaderCursorRight();
				}
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_X)	{
					data.setGameState(GAME_STATE.INVENTORY_OUTER);
					data.getInventoryPanel().setInnerActive(false);
					data.getInventoryPanel().resetItemCursor();
					data.getInventoryPanel().clearText();
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
	
	
	
	/**
	 * Performs an action on the tile in front of the player
	 */
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
				if(facedTile.getDoodad().getClass() == Chest.class)	{
					((Chest) facedTile.getDoodad()).interact();
					data.getInventory().addItem(((Chest) facedTile.getDoodad()).lootChest());
				}
				else if (facedTile.getDoodad().getClass() == Sign.class)	{
					data.getDialogueBox().setDialogue(((Sign) facedTile.getDoodad()).getDialogue(), true);
				}
			}
			
			NPC interactedNPC;
			for (int i = 0; i < data.getCurrentMap().getNPCs().size(); i++)	{
				interactedNPC = data.getCurrentMap().getNPCs().get(i);
				if (interactedNPC.getCoordX() == facedX && interactedNPC.getCoordY() == facedY && !interactedNPC.movingEh())	{
					data.getDialogueBox().setDialogue(data.getCurrentMap().getNPCs().get(i).getDialogue(), false);
					interactedNPC.invertFacing(data.getPlayer().getFacing());
					interactedNPC.setTalking(true);
					data.getPlayer().setInteractingNPC(interactedNPC);
				}
			}
		}
	}
	
	private void openParty()	{
		data.setGameState(GAME_STATE.PARTY_PANEL);
		data.getMenu().setVisible(false);
		data.getMenu().shrink();
		data.getDialogueBox().shrink();
		data.getPartyPanel().update();
		data.getGameBoard().add(data.getPartyPanel());
		data.setPaused(true);
	}
	
	private void closeParty()	{
		
		data.getGameBoard().remove(data.getPartyPanel());
		data.getMenu().restore();
		data.getDialogueBox().restore();
		data.setPaused(false);
		
		if (directAccess)	{
			data.setGameState(GAME_STATE.WALK);
			data.getMenu().setVisible(false);
		}
		else	{
			data.setGameState(GAME_STATE.MENU);
			data.getMenu().setVisible(true);
		}
		
		data.getDialogueBox().setVisible(false);
	}
	
	private void openInventory()	{
		data.setGameState(GAME_STATE.INVENTORY_OUTER);
		data.getMenu().setVisible(false);
		data.getMenu().shrink();
		data.getDialogueBox().shrink();
		data.getGameBoard().add(data.getInventoryPanel());
		data.getInventoryPanel().initializeList();
		data.setPaused(true);
	}
	
	private void closeInventory()	{
		
		data.getGameBoard().remove(data.getInventoryPanel());
		data.getMenu().restore();
		data.getDialogueBox().restore();
		
		data.getDialogueBox().setVisible(false);
		data.getInventoryPanel().resetCategoryCursor();
		data.getInventoryPanel().resetHeaderCursor();
		data.setPaused(false);
		
		if (directAccess)	{
			data.setGameState(GAME_STATE.WALK);
		}
		else	{
			data.setGameState(GAME_STATE.MENU);
			data.getMenu().setVisible(true);
			
		}
		
	}

}