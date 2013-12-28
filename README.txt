An old school role playing game that borrows heavily from games like Pokemon and Final Fantasy.

Completion Status-
Game is continually being developed and is still missing many key systems. However many systems are fully (or near fully) developed such as player / NPC movement, area transitions, dialogue, signs, treasure chests, in-game clock, inventory and party screens.

Note on running the game-
Run the game from the Frame class. If an error is thrown, first run the MapWriter class to regenerate the map and then run again from the Frame class.

Note on playing the game-
Current keybinds are hard coded. 
Z will interact with the world and advance dialogue
X will backtrack menus
ESC or ENTER will bring up the side menu
I and P will directly access inventory and party panels. Currently buggy 

Changelog-

v0.26 - Level Algorithm
-Party members are now have a piecewise XP growth algorithm in place for when they level up. Algorithm may grow too slowly but will be re-evaluated later.
-Testing level ups is possible at any time by pressing the 'G' key.
-Talking to the teal haired NPC in the first map will now result in her joining the party.
-Corrected oversight on not having party portrait update to the currently selected party member.
-Moved dialogue advancement method out of the input manager and into the dialogue box class because it had no place being there to begin with.
-Moved most of the logic for looting items and reading signs into the dialogue box class. Now it all happens automatically after something is added to the inventory or a sign is interacted with.
-Many dialogue box methods can now been made private as a result of the previous changes.
-"Fixed" a bug that would cause the game to freeze when clicking off of the game and back on. The fix merely treats the symptoms of the problem but is 100% functional. Further documentation available in bug report section.
-Triggers now correctly contain the methods needed to add additional events and actions.
-Two new trigger action available- remove NPC from the map and add PartyMember to party.
-It is now possible to directly access the party and inventory panels without using the small side menu by using the 'p' and 'i' keys. This mostly works but has introduced bugs. See bug report section.
-Input Manager has been partially broken into methods for improved readability


v0.25 - Party Panel
-Initial party screen submit.

Older version changelogs not well documented.
