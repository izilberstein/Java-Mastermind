

How to use:

To compile the program use javac *.java and to run the program use java Game.

The main program (Game.java) has a central menu that it will always return to unless the character q is pressed in which case it quits. All other commands trigger different actions.

There are two ways to play the game: as the guesser or the master. How to play each way is explained in the printRules method from the menu.


How it works:

The program uses code objects, which holds an int array of length 4 (Code.java). The values that this int array can hold is from 0 to a global variable that can be altered (MaxVal.java).

When playing as the guesser, you input a code object each turn. The computer checks these code objects with the randomly generated code object it created as the master code and returns the number of hits and taps, stored as a key object (Key.java). The way it does this is by indexing the number of each value in 2 arrays, one for the guessed code and one for the master code. It has a third array keeping track of the hits, where the values equal each other and are in the same index in the codes.
After it has all the values stored in the 3 arrays, it counts the number of hits and taps, and subtracts the hits from the taps to avoid overlap before returning the key object.
The program runs until either the number of turns has run out or you guess the code (4 hits). If you guess the code, it checks to see if your number of turns qualifies for the leaderboard, which I will mention later.

Playing as the master, the user generates the master code and the computer guesses it. The computer generates an initial guess of a random code and an array list of every possible code. After the user returns the hits and taps, the computer revises its array list to include only codes with the same number of hits and taps as its guess - the codes that could still possibly be the users. It then randomly picks one from this list to guess. It does this until it wins (which it always does), unless the user returns an incorrect number of hits or taps in which case there will be no possible codes and confronts you for cheating, or it runs out of turns (which will never happen unless the max value for the code is too large).

The leaderboard, which is stored in leaderboard.txt, holds the top 5 scores (number of guesses) when playing as the guesser. After you win as the guesser, it checks your number of turns with the top scores on the leaderboard. Only if you qualify (have less than or equal to a score on the leaderboard) will it ask for your name and insert you into the list. It manipulates the leaderboard by reading it as an array list of object Leader (Leader.java), an object that holds your position, your score, and your name. There is a reset file for the leaderboard that can be copied into it if need be. 
