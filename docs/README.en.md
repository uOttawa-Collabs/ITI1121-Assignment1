<center>
  <h1>ITI 1121. Introduction to Computing II</h1>
  <h3>Assignment 4</h3>
  <h3>Deadline: Jul 28, 2020 at 11pm</h3>
</center>

## Learning objectives

* Inheritance, Composition and Abstract Classes
* Introduction to Machine Learning

## Introduction

We have created the basis for our implementation of MENACE, an automated system learning how to play Tic-Tac- Toe. In this assignment, we will use the solution of assignment 3, you must thus complete it first. We will use our previous work to create our own implementation of Donald Michie’s 1961 paper.

## Menace

We are now ready to implement Menace. If you have not already done so, you should really read the paper published by Donald Michie in 1961 in Science Survey, titled Trial and error.

That paper has been reprinted in the book On Machine Intelligence and can be found on page 11 at the following:

* https://www.gwern.net/docs/ai/1986-michie-onmachineintelligence.pdf

You can also watch

* https://www.youtube.com/watch?v=R9c-_neaxeU

**In the following, we are only going to deal with 3x3 games, because that is what was defined in the paper.** You do not have to worry about other board sizes, only 3x3.

We have given you everything that is required for this part, so you only need to focus on the implementation of `ComputerMenacePlayer` and `MenaceGame`. However, you need to use a working solution of assignment 3.

In our solution, we have our MENACE player, playing against a range of possible players: a human, a random player, a prefect player, or another MENACE player.

The human player and the random player were already implemented in the previous assignments. We do provide an implementation of a perfect player. You do not need to understand how it works precisely (but of course, you can!) but you should definitely have a look at the code since this will help you a lot for the implementation of MENACE.

We have done a couple of changes in the design:

First, we now would like our Players to share some additional methods. It used to be that all a Player had to do was to give a concrete implementation of the method play.

Now, we want to be able to inform the Player that a new game is starting and that a game is finished. We want the player to keep some stats about its performance: how often it won and lost overall, as well as over the last 50 games (to track progress). The implementation of some of these methods are common to all Players, so we would like to add the code directly in Player.

However, `Player` was an interface which prevented us to do this. So we transformed it in a full fledged class. We still cannot provide a default implementation for the method `play`, so that method is still abstract, therefore Player is now an abstract class. We have provided the implementation for all the other methods of the class Player.

Second, some of our players will need to augment a TicTacToe with additional details. For example, our perfect player needs to record which of the possible moves are winning and which ones are losing.  We created a new class `PerfectGame` that will be composed of a `TicTacToe` game, but also tracks the outcome of each move (win/lose/draw) and how many moves until that outcome.  The `PerfectGame` is used by `ComputerPerfectPlayer` to be the perfect player.

The way the class works is that when an instance of `ComputerPerfectPlayer` is created, it first creates the list of all possible games, as we did in Assignment 3.  The player then unravels all wins, all losses and all draws into PerfectGames so that it can determine which ones should be played, and which ones should be avoided.

It is then ready to play games. When its method play is called, it receives an instance of `TicTacToe` and looks through the precomputed games to find which one corresponds to the current state of the game (up to symmetry) and then selects from there one of the best possible moves, as precomputed during initialization. `ComputerMenacePlayer` will work very much in the same way.

The gist of MENACE is that it precomputes all possible games (up to symmetry), and for each game, it initially provides a certain number of beads for each possible move. When playing a game, MENACE finds the game corresponding to the current state and randomly selects one of the beads it has for that game. The more a given move has beads at that stage, the more likely it is to be selected. Once the game is finished, MENACE will update
the number of beads for each of the moves used during that game, based on the outcome: if the game was lost, then the beads that were selected will be removed, making it less likely that similar moves will be selected in the future.

This approach has as problem: it is possible to remove the last bead of a game this way, in which case Menace will be stuck if that game is seen again in the future, with no move having any chance of being selected. To avoid that, we only remove a bead if that is not the last one in that game.

In summary:

* If the game is a draw, then the beads are put back, and another similar bead is added each time increasing a little bit the chance of selecting that move in the future.

* If the game is a win, then the bead is put back and 3 similar beads are added.

* If the game is lost, the bead is not put back

* Do not remove the last bead of a game.

You are asked to provide an implementation of `ComputerMenacePlayer` and `MenaceGame` that behaves as expected. `MenanceGame` tracks all games (like PerfectGame), and also records the current number of beads of each possible move of each possible game.

You are asked to provide an implementation of `ComputerMenacePlayer` that behaves as expected. You have to also implement `MenaceGame` which `ComputerMenacePlayer` instances use to precompute all possibly games and record the current number of beads for each possible move of each possible game.

In the paper, it is assumed that MENACE always plays the first move (and our experiments actually suggest that MENACE learns much better how to be a first player than to be a second player). The main instance of MENACE in our system is also set to always play first (though you can easily change that in the code), but since we can play MENACE against MENACE, we need to have an implementation that can play both X and O. The paper specifies the initial number of beads for X only. We will use slightly different numbers for X (8,4,2,1 instead of 4,3,2,1) and we will use similar numbers for O. In other words, the initial number of beads are

* First position (X): 8 beads
* Second position (O): 8 beads
* Third position (X): 4 beads
* Fourth position (O): 4 beads
* Fifth position (X): 2 beads
* Sixth position (O): 2 beads
* Seventh position (X): 1 bead
* Eighth position (O): 1 bead
* Ninth position (X): 1 bead

The implementation of the system that we provide works as follow:

1. Initially, a new instance of MENACE is created.

2. That instance has not been trained and therefore is very weak. You can chose the opponent, which is either computer driven (random, perfect or MENACE), or a human (you).

3. If you choose a computer driven opponent, then the system will automatically run 500 games between MENACE and that opponent, during which MENACE should improve its play (especially the first time). and should be then much tougher to beat.

4. We also have included an option to reinitialize MENACE, so that you can easily restart a learning stage from scratch.


## Training Your MENANCE Player

Here is a sample run of the system.

```
java GameMain
(1) Menace against a human player
(2) Train Menace against perfect player
(3) Train Menace against random player
(4) Train Menace against another menace
(5) Delete (both) Menace training sets
(6) Human to play perfect player
(7) Perfect player to play human
(8) Human against a menace player
(Q)uit
```

And we will play Menace against a human ("1"):

```
1
```

And here is the result.

```
   | X |
-----------
   |   |
-----------
   |   |

O to play: 1
 O | X |
-----------
 X |   |
-----------
   |   |

O to play: 5
 O | X |
-----------
 X | O |
-----------
   |   | X

O to play: 3
 O | X | O
-----------
 X | O |
-----------
   | X | X

O to play: 7

Result: OWIN
 O | X | O
-----------
 X | O |
-----------
 O | X | X

Player 1 has won 0 games, lost 1 games, and 0 were draws.

Player 2 has won 1 games, lost 0 games, and 0 were draws.
```

Let's play one more game.

```
1
```

And the output.

```
   |   |
-----------
   |   | X
-----------
   |   |

O to play: 1
 O |   | X
-----------
   |   | X
-----------
   |   |

O to play: 9
 O |   | X
-----------
   | X | X
-----------
   |   | O

O to play: 4
 O | X | X
-----------
 O | X | X
-----------
   |   | O

O to play: 7

Result: OWIN
 O | X | X
-----------
 O | X | X
-----------
 O |   | O

Player 1 has won 0 games, lost 2 games, and 0 were draws.

Player 2 has won 2 games, lost 0 games, and 0 were draws.
```


As can be seen, so far MENACE is not really good and lost twice in a
row despite being first player. Let’s now train it against a perfect player.


```
(1) Menace against a human player
(2) Train Menace against perfect player
(3) Train Menace against random player
(4) Train Menace against another menace
(5) Delete (both) Menace training sets
(6) Human to play perfect player
(7) Perfect player to play human
(8) Human against a menace player
(Q)uit
2
```

The output is

```
About to train with 500 games.
Player 1 has won 0 games, lost 182 games, and 318 were draws.
Over the last 50 games, this player has won 0, lost 4, and tied 46.

Player 2 has won 182 games, lost 0 games, and 318 were draws.
Over the last 50 games, this player has won 4, lost 0, and tied 46.
```

MENACE (which is Player 1) has lost 182 games against the perfect player
in the 500 that were played, and only 4 of the last 50 games.

Let us try again as a human player against a trained MENACE.

```
   |   |
-----------
 X |   |
-----------
   |   |

O to play: 5
   |   |
-----------
 X | O |
-----------
 X |   |

O to play: 1
 O |   |
-----------
 X | O |
-----------
 X |   | X

O to play: 8
 O | X |
-----------
 X | O |
-----------
 X | O | X

O to play: 3

Result: DRAW
 O | X | O
-----------
 X | O | X
-----------
 X | O | X
```

Now, MENACE is a much better player, and plays indeed very well and got a draw. Note however that it is not perfect and can still be beaten.

Look at this partial game:

```
   |   | X
-----------
   |   |
-----------
   |   |

O to play: 1
 O |   | X
-----------
   | X |
-----------
   |   |
```

Here we (Player 2) will make a terrible move (on purpose, I swear) and see how the MENACE player reacts.

```
O to play: 4
 O | X | X
-----------
 O | X |
-----------
   |   |
```

We see that MENACE did not make the best move to win the game.  The MENACE player has
not been trained at all to handle these "100% can win" scenarios like our perfect player
would have played.  This illustrates some of the challenges in machine learning,
but that is an entire different discussion!



## Implementation Details and Hints

You are responsible to implement

* ComputerMenacePlayer.java
* MenaceGame.java

Here are a few things to help you debug along the way.

### GameOutput

From a player's spective, a game has an outcome  of WIN, DRAW,
LOSE or UNKNOWN.

```
public enum GameOutcome {
  WIN,
  DRAW,
  LOSE,
  UNKNOWN;

  public boolean isBetter(GameOutcome other) {
    return this.compareTo(other) < 0;
  }

  public boolean asGoodOrBetter(GameOutcome other) {
    return this.compareTo(other) <= 0;
  }
}
```

This is useful when updating the number of beads in our MenaceGame.


### Smaller Training Sets

The `GameMain` has two extra (4th and 5th) parameters to help you debug.
The fourth parameter will set the number of training matches between computer players
(defaults to 500).

For example:

```
java GameMain 3 3 3 1
```

Will create our 3x3 (3 to win board), but training will now only be 1 game (not 500).

```
(1) Menace against a human player
(2) Train Menace against perfect player
(3) Train Menace against random player
(4) Train Menace against another menace
(5) Delete (both) Menace training sets
(6) Human to play perfect player
(7) Perfect player to play human
(8) Human against a menace player
(Q)uit
2

Result: OWIN
   | X | X
-----------
 O | O | O
-----------
   | X |

Player 1 has won 0 games, lost 1 games, and 0 were draws.

Player 2 has won 1 games, lost 0 games, and 0 were draws.
```

### Debug Flag

The `GameMain`s fifths parameter sets a `isDebug` flag that you can use
to conditionally output extra information.

Here is how that flag is used in `ComputerPerfectPlayer`.

```
java GameMain 3 3 3 1 true
```

Let's play against the perfect player

```
(1) Menace against a human player
(2) Train Menace against perfect player
(3) Train Menace against random player
(4) Train Menace against another menace
(5) Delete (both) Menace training sets
(6) Human to play perfect player
(7) Perfect player to play human
(8) Human against a menace player
(Q)uit
7
```

We now see

```
Perfect player choosing based on:
OVERALL: DRAW(5)
 D 5 | D 5 | D 5
-----------
 D 5 | D 5 | D 5
-----------
 D 5 | D 5 | D 5
```

Which highlights the `W`in, `L`ose or `D`raw scenarios before it chooses

```
 X |   |
-----------
   |   |
-----------
   |   |

O to play:
```

Here is the snippet of code that enables that.

```java
if (isDebug) {
  System.out.println("Perfect player choosing based on: ");
  System.out.println(perfectGame);
  System.out.println("");
}
```

Consider doing something similar in `ComputerMenancePlayer`, as shown below

```
(1) Menace against a human player
(2) Train Menace against perfect player
(3) Train Menace against random player
(4) Train Menace against another menace
(5) Delete (both) Menace training sets
(6) Human to play perfect player
(7) Perfect player to play human
(8) Human against a menace player
(Q)uit
1
```

The Menace player then shows the random number it rolled and the TicTacToe board showing
the number of beads in each open position.

```
Menace rolled 9 on board:
POSITION: 2 (Odds 31)
 8 | 8 | 4
-----------
 4 | 2 | 2
-----------
 1 | 1 | 1
```

So a working Menace implementation would play in position 2.

```
   | X |
-----------
   |   |
-----------
   |   |
```


## Submission

Please read the [Submission Guidelines](SUBMISSION.en.md) carefully.
Submission errors will affect your grades.

Submit the following files.

* STUDENT.md
* ComputerMenacePlayer.java
* MenaceGame.java

This assignment can be done in groups of 2 +/- 1 person.  Ensure that `STUDENT.md` includes the names of all participants; only submit 1 solution per group.

## Academic Integrity

This part of the assignment is meant to raise awareness concerning plagiarism and academic integrity. Please read the following documents.

* https://www.uottawa.ca/administration-and-governance/academic-regulation-14-other-important-informati
* https://www.uottawa.ca/vice-president-academic/academic-integrity

Cases of plagiarism will be dealt with according to the university regulations. By submitting this assignment, you acknowledge:

1. I have read the academic regulations regarding academic fraud.
2. I understand the consequences of plagiarism.
3. With the exception of the source code provided by the instructors for this course, all the source code is mine.
4. I did not collaborate with any other person, with the exception of my partner in the case of team work.

* If you did collaborate with others or obtained source code from the Web, then please list the names of your collaborators or the source of the information, as well as the nature of the collaboration. Put this information in the submitted README.txt file. Marks will be deducted proportional to the level of help provided (from 0 to 100%).
