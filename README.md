Voyage to Venus
====

Student Name: Greg Lyons

netID: gml14

Project: Game

Game Name: Voyage to Venus

Backstory: For the first time, NASA is sending a manned mission to Venus. As pilot of the spacecraft, it is your job to safely navigate the ship to Venus (Level 1).  Venus is 162 million miles away – the counter in the top left of the screen keeps track of distance traveled (in millions of miles).  At 162, you will reach Venus.  If you crash along the way, you must restart from the beginning.  NASA has predicted a possible meteor shower during your journey to Venus – not a problem, right?

After you reach Venus, you will begin the return journey home (Level 2).  If the ship crashes along the way, you restart from the Level 2 checkpoint rather than the start of the game.  Again, the return trip to earth will take 162 million miles.  Reach home safely, and you’ll be an international hero.


[CONTROLS]

[UP] and [DOWN]……Move spaceship

[SPACE]…………………Fire laser cannon

[R]…………………Reload laser cannon (5 shots per reload)

[ESC]………………………Pause

[ENTER]……………Resume from pause


Goal: To make two safe trips: from Earth to Venus (Level 1), and then from Venus back to Earth (Level 2).

Basic mechanics:  The background screen moves continuously to the left while the spaceship stays laterally suspended on the left side of the screen, giving the illusion of the ship's motion from left to right (for Level 1; reversed for Level 2).  The up and down arrow keys will be used to move the ship vertically to avoid meteors, alien spaceships, and alien projectiles.  The user's spaceship is also be equipped with a projectile weapon to fire at obstacles and destroy them.

The game is implemented with a series of states, progressing from the opening splash screen, to level 1, to a mid-game splash screen, to level 2, and then to a final splash screen.  There is an additional pause state for ingame pausing.  Using states allows for methods to be shared and interpreted based on state rather than writing isolated, specific sections of code for each part of the game.

Levels: The first level (Earth to Venus) features a meteor shower - a heavy barrage of obstacles to avoid.  The second level (Venus to Earth) features fewer meteors but introduces alien spaceships that move up and down with the player and shoot projectiles straight ahead.

Cheat keys:
- Holding [SHIFT] triggers God Mode, which freezes all obstacles on the screen.  However it also freezes the mile counter.
- Pressing [\] (backslash) jumps immediately to gameplay for level 2


*NOTE: Many of the in-game values for things like speed, timing, size, etc. may seem arbitrary, but they have been carefully tested to ensure the best gameplay experience.  Small tweaks that improve gameplay are better than sticking to a rigid system of numbering.  Some of the methods use math based on the state, which helps with the directional switch between the first and second level.
