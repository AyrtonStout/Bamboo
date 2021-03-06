#### An old school role playing game that borrows heavily from games like Pokemon and Final Fantasy.

Click the image for a demo video of most of the game's features
[![Project Demo Video](https://img.youtube.com/vi/vMYlcoZ04Vs/0.jpg)](https://www.youtube.com/watch?v=vMYlcoZ04Vs)

## Completion Status-
Game is WIP though progress is pretty much dead. Some systems remain incomplete, however many systems are fully (or near fully) developed such as player / NPC movement, area transitions, dialogue, signs, treasure chests, in-game clock, combat screen, inventory and party screens.

### Current Focus: 
 - Bug fixes and enhancements to older code

### Current Issues: 
  - Spells are still WIP. Using them during combat will sometimes create infinite loops
  - Game is developed in a Linux environment. Some things (like text or alignment) can behave oddly when run on Windows or MacOS

### Note on running the game
Run the game from the GUI/Frame class. If an error is thrown, first run the MapWriter class to regenerate the map and then run again from the Frame class.

### Note on playing the game-
  - Current keybinds are hard coded. 
  - Z will interact with the world and advance dialogue
  - X will backtrack menus
  - ESC or ENTER will bring up the side menu
  - I and P will directly access inventory and party panels
  - Level-ups may be easily tested with the G key

## Changelog

###### v0.50 - Spell Series
- Early spell implementation
- Combat class is now static

###### v0.41
- Before equipping an item, the item will now show what the stat changes will be
- Mana now changes depending on the character's intellect
- Equipping weapons will no longer fully restore a character's health
- Fragmented PartyPanel class into like 5 smaller classes

###### v0.40 - The fix and enhance random crap series
- Redid inventory class and its methods to make more sense at the expense of code length 
- Fixed long standing bug that would destroy the currently equipped item if equipping a  different item
- It is now possible to equip more than just weapons, though such gear does not yet exist
- Rewrote the inheritance hierarchy to make equippable items implement items, and then items like swords and armor inherit from equippable items instead. 
- All stats that an equippable item gives are now included in the party panel
- Fixed the missing Special stat from the party panel's display
- Weapon attack values are now included into the combat calculations


###### v0.39
- Potions are now only usable on valid targets (targets that would benefit/ are alive)
- Info panel now correctly displays during item targeting
- Info panel now serves as a smaller text box that will also display information
- Potion use is now animated for party members
- Enemies now attack a random target instead of the first target. Still a placeholder for a more robust system; just a better one
- This concludes the combat series until after the spells and abilities series.

###### v0.38
- Item option now brings up a new panel that will display consumables
- Items are displayed in two columns of 4 items each
- Cursor will no longer travel to items that dont exist
- Item screen will now give an indication if no usable items exist
- Floating combat text made into its own class and enumeration for easy expansion
- Started fixing the rather badly coded inventory class but chickened out after seeing how much work it would end up being. Will return to it later
- Health and mana potions now functional

###### v0.37
- Characters are now able to crit in combat
  (Crit chance has been increased to make this more easily seen)
- Floating text from crits now appear in yellow text
- Critical hits now have a shaking effect on the combat text
- Characters are now able to miss with a base miss chance of 15%
- Missed attacks are reflected in white text
- Characters that are dead now stay drawn dead at the start of a new battle
- Dead party members no longer receive XP
- Predictive turn ordering for fleeing now happens automatically after a failed attempt

###### v0.36
- Player can now target their own party members for attacks
- Party members now change appearance when they are dead
- Floating combat text is now drawn above other combat elements
- Fixed a bug causing dialogue to not play before an enemy's turn
- Running now causes only a partial use of the turn to be used
 + This is reflected in the turn order prediction

###### v0.35
- Turn prediction now hides itself after combat is over when xp is being awarded
- Floating combat text is now properly aligned to all actors
- Trimmed crab's transparent pixels and fixed its dimensions
- Turn prediction now has a color coded border around the actor's turn indicators
- Targetting arrows are now more intelligent at finding their target
- A placeholder background is now in use because I got sick of staring at grey
 + This makes seeing the floating damage text somewhat difficult for the time being
  (sacrifices had to be made)

###### v0.34
- Enemies now fight back, though are limited to attacking the first party member
- Party members and enemies now attack more frequently relative to their speed
- This turn order is visible, and predictive
- Battles support multiple party members

###### v0.33
- Fixed several bugs with monsters. They now are new monsters every time instead of spawning the same one
- Basic attack now works and a basic damage indicator is available
- Text box will properly share certain information such as the XP from an enemy's death
- Battle properly exits upon an enemy dying though XP is not yet properly awarded

###### v0.32
- Arrows now appear and disappear at more logical times
- Friendly and enemy targetting arrows
- Methods to call the dialogue box to be used in combat screen to display information
- Groups of enemies can now attack instead of just individual enemies

###### v0.31
- Combat screen now has a properly formatted menu
- Arrows show selected option
- Arrows respond to key commands
- Run option will escape combat 100% of the time

###### v0.30 - Combat Series
- Starter map now has a chance to spawn a random enemy of 3 types, though all 3 types currently use the same placeholder picture
- Combat screen is currently inescapable and functionless. This build is not recommended for experiencing other parts of the game

###### v0.29 - Stats Panel Resurrection
- Stats panel that used to be part of the Party panel has now been somewhat restored. Previous functionality has been retained but new stuff is non functional and a placeholder

###### v0.28 - Enemy Update
- Structure for enemy units is in place though the ability to spawn them is not
- Three enemies, Giant Rat, Razorclaw Crab, and DeathStalker Crow have been added; though all enemies currently use old placeholder art for the crab.
- Inventory screen now properly displays the current party instead of hard coded values
 (A note on this is that the way that the equip screen now works, showing unique party members in the item screen may soon be deleted altogether)

###### v0.27 - Party Overhaul
- Partial redesign of party panel to be more of an equipment panel. Statistics will be moved to their own section at a later date
- Party panel now supports 3 out of 4 options, equip, remove, and removeAll. Auto will be completed at a later date once it is better determined what metrics will be used.
- In continued effort to compact down the code in the InputManager class, all key presses related to the party panel are now handled by the party panel. This will instead cause the party panel to have the sprawling code maze instead. This will be looked at to make more readible in the next update.
- Fixed a flickering bug that was present when doing certain actions in the inventory screens. This bug seemed isolated to Windows systems so was not until recently discovered.
- Added methods to remove equipment instead of just setting them to null
- Equipping items causes them to disappear from the inventory. Removing the items will cause them to reappear.
- Created method for Inventory to add an item without calling up the dialogue box and changing the game state


###### v0.26 - Level Algorithm
- Party members are now have a piecewise XP growth algorithm in place for when they level up. Algorithm may grow too slowly but will be re-evaluated later.
- Testing level ups is possible at any time by pressing the 'G' key.
- Talking to the teal haired NPC in the first map will now result in her joining the party.
- Corrected oversight on not having party portrait update to the currently selected party member.
- Moved dialogue advancement method out of the input manager and into the dialogue box class because it had no place being there to begin with.
- Moved most of the logic for looting items and reading signs into the dialogue box class. Now it all happens automatically after something is added to the inventory or a sign is interacted with.
- Many dialogue box methods can now be made private as a result of the previous changes.
- "Fixed" a bug that would cause the game to freeze when clicking off of the game and back on. The fix merely treats the symptoms of the problem but is 100% functional. Further documentation available in bug report section.
- Triggers now correctly contain the methods needed to add additional events and actions.
- Two new trigger action available- remove NPC from the map and add PartyMember to party.
- It is now possible to directly access the party and inventory panels without using the small side menu by using the 'p' and 'i' keys. This mostly works but has introduced bugs. See bug report section.
- Input Manager has been partially broken into methods for improved readability


###### v0.25 - Party Panel
- Initial party screen submit.

Older version changelogs not well documented.
