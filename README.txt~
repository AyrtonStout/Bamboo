An old school role playing game that borrows heavily from games like Pokemon and Final Fantasy.

Completion Status-
Game is continually being developed and is still missing many key systems. However many systems are fully (or near fully) developed such as player / NPC movement, area transitions, dialogue, signs, treasure chests, in-game clock, inventory and party screens.
Current Focus: Combat screen

Note on running the game-
Run the game from the Frame class. If an error is thrown, first run the MapWriter class to regenerate the map and then run again from the Frame class.

Note on playing the game-
Current keybinds are hard coded. 
Z will interact with the world and advance dialogue
X will backtrack menus
ESC or ENTER will bring up the side menu
I and P will directly access inventory and party panels

Changelog-

v0.32
-Arrows now appear and disappear at more logical times
-Friendly and enemy targetting arrows
-Methods to call the dialogue box to be used in combat screen to display information
-Groups of enemies can now attack instead of just individual enemies

v0.31
-Combat screen now has a properly formatted menu
-Arrows show selected option
-Arrows respond to key commands
-Run option will escape combat 100% of the time

v0.30 - Combat Series
-Starter map now has a chance to spawn a random enemy of 3 types, though all 3 types currently use the same placeholder picture
-Combat screen is currently inescapable and functionless. This build is not recommended for experiencing other parts of the game

v0.29 - Stats Panel Resurrection
-Stats panel that used to be part of the Party panel has now been somewhat restored. Previous functionality has been retained but new stuff is non functional and a placeholder

v0.28 - Enemy Update
-Structure for enemy units is in place though the ability to spawn them is not
-Three enemies, Giant Rat, Razorclaw Crab, and DeathStalker Crow have been added; though all enemies currently use old placeholder art for the crab.
-Inventory screen now properly displays the current party instead of hard coded values
 (A note on this is that the way that the equip screen now works, showing unique party members in the item screen may soon be deleted altogether)

v0.27 - Party Overhaul
-Partial redesign of party panel to be more of an equipment panel. Statistics will be moved to their own section at a later date
-Party panel now supports 3 out of 4 options, equip, remove, and removeAll. Auto will be completed at a later date once it is better determined what metrics will be used.
-In continued effort to compact down the code in the InputManager class, all key presses related to the party panel are now handled by the party panel. This will instead cause the party panel to have the sprawling code maze instead. This will be looked at to make more readible in the next update.
-Fixed a flickering bug that was present when doing certain actions in the inventory screens. This bug seemed isolated to Windows systems so was not until recently discovered.
-Added methods to remove equipment instead of just setting them to null
-Equipping items causes them to disappear from the inventory. Removing the items will cause them to reappear.
-Created method for Inventory to add an item without calling up the dialogue box and changing the game state


v0.26 - Level Algorithm
-Party members are now have a piecewise XP growth algorithm in place for when they level up. Algorithm may grow too slowly but will be re-evaluated later.
-Testing level ups is possible at any time by pressing the 'G' key.
-Talking to the teal haired NPC in the first map will now result in her joining the party.
-Corrected oversight on not having party portrait update to the currently selected party member.
-Moved dialogue advancement method out of the input manager and into the dialogue box class because it had no place being there to begin with.
-Moved most of the logic for looting items and reading signs into the dialogue box class. Now it all happens automatically after something is added to the inventory or a sign is interacted with.
-Many dialogue box methods can now be made private as a result of the previous changes.
-"Fixed" a bug that would cause the game to freeze when clicking off of the game and back on. The fix merely treats the symptoms of the problem but is 100% functional. Further documentation available in bug report section.
-Triggers now correctly contain the methods needed to add additional events and actions.
-Two new trigger action available- remove NPC from the map and add PartyMember to party.
-It is now possible to directly access the party and inventory panels without using the small side menu by using the 'p' and 'i' keys. This mostly works but has introduced bugs. See bug report section.
-Input Manager has been partially broken into methods for improved readability


v0.25 - Party Panel
-Initial party screen submit.

Older version changelogs not well documented.
