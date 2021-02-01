<center>
  <h1>ITI 1121. Introduction to Computing II</h1>
  <h3>Assignment 1</h3>
  <h3>Deadline: May 21, 2020 at 11pm</h3>
</center>

## Learning objectives

* Edit, compile and run Java programs
* Utilize arrays to store information
* Apply basic object-oriented programming concepts
* Understand the university policies for academic integrity

## Introduction

This year, we are going to implement the game Tic-Tac-Toe. The game itself is fairly simple and well-known game. You can brush up [your Tic-Tac-Toe skills](https://en.wikipedia.org/wiki/Tic-tac-toe).

Our ultimate goal is to program a machine-learning algorithm that will learn how to be a good Tic-Tac-Toe player automatically. We will base our approach on a paper published by Donald Michie in 1961 in Science Survey, titled Trial and error. That paper has been reprinted in the book On Machine Intelligence and can be found on page 11 at the following URL:

* [Machine Intelligence 1986](https://www.gwern.net/docs/ai/1986-michie-onmachineintelligence.pdf)

For a more modern take of the same idea, you can also watch

* [The pile of matchboxes which can learn](https://www.youtube.com/watch?v=R9c-_neaxeU)

But for this assignment, our goal is more modest: we simply want to implement a game of Tic-Tac-Toe, where players are indicating their next move from the command line. In its default conguration, it will look like this: the program rst displayed an empty grid and is prompting the rst player (X) for an input.

```
$ java Main

   |   |
-----------
   |   |
-----------
   |   |

X to play:
```

The first player played the cell 5. The program displays the current game, with cell number ve taken by X, and is prompting the second player (O) for an input. The game will keep going following that pattern.

```
X to play: 5

   |   |
-----------
   | X |
-----------
   |   |

O to play:
```

The user types "2" in the terminal.

```
O to play: 2

   | O |
-----------
   | X |
-----------
   |   |

X to play:
```

The user types "1" in the terminal.

```
X to play: 1

 X | O |
-----------
   | X |
-----------
   |   |

O to play:
```

The user types "9" in the terminal.

```
O to play: 9
 X | O |
-----------
   | X |
-----------
   |   | O

X to play:
```

The user types "4" in the terminal.

```
X to play: 4

 X | O |
-----------
 X | X |
-----------
   |   | O

O to play:
```

The user types "6" in the terminal.

```
O to play: 6

 X | O |
-----------
 X | X | O
-----------
   |   | O

X to play:
```

The user types "7" in the terminal.

```
X to play: 7

 X | O |
-----------
 X | X | O
-----------
 X |   | O

Result: XWIN
```

As can be seen, at each turn the program prints out the current state of the game and then queries the next user (X or O) to provide its next move. We simply assume that the cells are numbered line by line, from top left to bottom right, as follows:

```
 1 | 2 | 3
-----------
 4 | 5 | 6
-----------
 7 | 8 | 9
```

So in the game above, the first player’s (playing with X) initial move is to select the cell 5, which is in the middle of the game. The second player’s (playing with O) initial move is to select the cell 2, which is in the middle of the first line (and a fatal mistake). After a few more moves, the first player wins.

Our own implementation will be a little bit more general than the usual 3x3 grid game. By default (as shown above), our game will be indeed played on a 3x3 grid, trying to align 3 similar cells horizontally, vertically or diagonally. But our more general implementation will accept 3 parameters n, m and k to play a game on an nxm grid trying to align k similar cells horizontally, vertically or diagonally. Here is an example of a game on a 3x4 grid, trying to align 3 similar cells.

```
$ java Main 3 4 3
```

```
   |   |   |
---------------
   |   |   |
---------------
   |   |   |

X to play:
```

```
X to play: 2

   | X |   |
---------------
   |   |   |
---------------
   |   |   |

O to play:
```

```
O to play: 6

   | X |   |
---------------
   | O |   |
---------------
   |   |   |

X to play:
```

```
X to play: 7

   | X |   |
---------------
   | O | X |
---------------
   |   |   |

O to play:
```

```
O to play: 4

   | X |   | O
---------------
   | O | X |
---------------
   |   |   |

X to play:
```

```
X to play: 12

   | X |   | O
---------------
   | O | X |
---------------
   |   |   | X

Result: XWIN
```

## Enum

In this application, we have a need to record the “state” of a game: it could be still in play, or one or the other of the players have won, or it could be a draw. Similarly, we need to record the state of a cell on the board: a cell can be empty, or it can contain a X or a O.

There are several ways to achieve this, but in this assignment we are going to use Java’s Enum type.

Some programmers use values of type int to represent symbolic constants in their programs. For example, to represent the day of the week or the month of the year.

```java
public class E1 {

  public static final int MONDAY = 1;
  public static final int TUESDAY = 2;
  public static final int SUNDAY = 7;

  public static final int JANUARY = 1;
  public static final int FEBRUARY = 2;
  public static final int DECEMBER = 12;

  public static void main(String[] args) {
    int day = SUNDAY;

    switch (day) {
      case MONDAY:
        System.out.println("sleep");
        break;

      case SUNDAY:
        System.out.println("midterm test");
        break;

      default:
        System.out.println("study");
    }
  }
}
```

Using constants, such as MONDAY and JANUARY, improves the readability of the source code. Compare “if (day == MONDAY) { ...}” to “if (day == 1) { ...}”. It is one step in the right direction.

However, since all the constants are integer values, there are several kinds of errors that the compiler cannot detect. For example, if the programmer uses the same number for two constants, the compiler would not be able to help, 7 is valid value for both SATURDAY and SUNDAY:

```java
public static final int SATURDAY = 7;
public static final int SUNDAY = 7;
```

But also, assigning a value representing a month to variable representing a day of the week would not be detected by the compiler, both are of type int:

```java
int day = JANUARY;
```

Enumerated types have the same benefits as the symbolic constants above, making the code more readable, but in a typesafe way.

```java
public class E2 {
  public enum Day {
    MONDAY, TUESDAY, SUNDAY
  }

  public enum Month {
    JANUARY, FEBRUARY, DECEMBER
  }

  public static void main(String[] args) {

    Day day = Day.MONDAY;

    switch (day) {
      case MONDAY:
        System.out.println("sleep");
        break;

      case SUNDAY:
        System.out.println("midterm test");
        break;

      default:
        System.out.println("study");
    }
  }
}
```

In the above program, each constant has a unique value. Furthermore, the statement below produces a compile time error, as it should:

```java
Day day = Month.JANUARY;
```

```bash
Enum.java:36: incompatible types
found : E2.Month
required: E2.Day
      Day day = Month.JANUARY;
                     ^
1 error
```

• https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html.

## Our Implementation

We are now ready to program our solution. We will only need four classes for this. For the assignment, you need to follow the patterns that we provide. You cannot change any of the signatures of the methods (that is you cannot modify the methods at all). You cannot add new public methods or variables. You can, however, add new private methods to improve the readability or the organization of your code.


### GameState

GameState is an enum type which is used to capture the current state of the game. It has four possible values:

* PLAYING: this game is ongoing,
* DRAW: this game is a draw,
* XWIN: this game as been won by the first player,
* OWIN: this game as been won by the second player.

### CellValue

CellValue is an enum type which is used to capture the state of a cell. It has four possible values:

* INVALID: the cell is not valid,
* EMPTY: the cell is empty,
* X: there is a X in the cell,
* O: there is an O in the cell.

Hint: It's possible to change the `toString()` of an enum.

Par exemple,

```java
public enum DayOfWeek {
  INVALID("?"),
  WEEKDAY("Mon-Fri"),
  WEEKEND("Sat/Sun");

  private String display;

  private DayOfWeek(String aDisplay) {
    display = aDisplay;
  }

  @Override
  public String toString() {
    return display;
  }
}
```

### TicTacToe

Instances of the class TicTacToe represent a game being played. Each object stores the actual board, which is saved in a single dimension array. There is an instance method that can be used to play the next move. The object figures out the player’s turn, so that information is not specified: we simply specify the index to play and the object knows to play either a X or a O. The object also tracks the state of the game automatically.

The specification for our class TicTacToe is given in our zip file. You need to fill out all the missing parts, reading carefully all the comments before doing so. You cannot modify the methods or the variables that are provided. You can, however, add new private methods as required.

La spécification de notre classe TicTacToe est donnée dans notre fichier zip. Vous devez remplir toutes les parties manquantes, en lisant attentivement tous les commentaires avant de le faire. Vous ne pouvez pas modifier les méthodes ou les variables qui sont fournies. Vous pouvez, cependant, ajouter de nouvelles méthodes de visibilité private si nécessaire.

The template that you are working with contains the following:

* An instance variable which is a reference to an array of CellValue to record the state of the board.
* Some instance variables to record the game’s number of columns and lines, the number of cells to align, the number of turns played (“level”) and current state.
* Two constructors: the default one creates the usual game (the 3x3 grid, with a winner if 3 similar cells are aligned), a second one is used to specify the number of rows, the number of columns and the number of cells to align. As usual, all instance variables must be initialized when building the object.
* A method for querying the object on the next player (that is, is it X's or O's turn to play?).
* A `show()` method to show the public state of the game.
* A `play(int position)` method for playing at a particular place in the game. This updates the game state and the grid.
* We also have some auxiliary method,
   * `checkForWinner(int position)`, which is used to calculate the state of the game after a particular move is made in the play method.
   * `valueOf(...)` (two methods) to show the value of a cell in the grid.
* There is a method `toString()`, which returns a string representation of the current state of the board.
* We don't use access methods ("getters"), but there is a `toDebug ()` to show the state of your games.

The specific behavior is described in the test automate in junit.zip

An example of a character string returned by `toString()` would be, when printed:

```
   | X |   | O
---------------
   | O | X |
---------------
   |   |   | X
```

There are a few situations that need our attention. For example, the index selected by the player may be invalid
or illegal. We do not have a very good way to handle these situations yet, so for the time being we will simply write
an error message. The subsequent behaviour of the method is unspecified, so simply implement something that seems to make sense <sup>1</sup>. One other situation would be that players continue the game after one of them wins. For testing purpose, we actually want that to be possible, however, then game state should reflect the first winner of the game. So if the players keep going after a win, a message is printed out but the game continues as long as the moves are legal. The “first” winner remains.

<sup>1</sup>The reason we are not specifying any behaviour here is because once we will have the tools required to deal with these exceptional situations, we will see that we actually will not have to come up with an alternative behaviour at all.

Note that the method toString() returns a reference to a String, it is not actually printing anything. So that one String instance, when printed, should produce the expected output (in other words, that one string instance, when printed, will span several lines).

If the cell provided by the player is invalid or illegal, and a message is displayed to the user, who is asked to play again. Here are a couple of examples of this situation:

```
$ java Main

   |   |
-----------
   |   |
-----------
   |   |

X to play:
```

```
X to play: 2

   | X |
-----------
   |   |
-----------
   |   |

0 to play:
```

```
0 to play: 10
The value should be between 1 and 9

   | X |
-----------
   |   |
-----------
   |   |

0 to play:
```

```
0 to play: 2
Cell 2 has already been played with X

   | X |
-----------
   |   |
-----------
   |   |

0 to play:
```

```
0 to play: 3

   | X | O
-----------
   |   |
-----------
   |   |

X to play:
```

Note that you can assume that the players are only providing integer values as inputs. You do not have to handle the case of other input types such as a character.

### Main

This class implements the game. You are provided with an implementation, which creates the instance of the TicTacToe class according to the parameters submitted by the user. It's all about looping through each stage of the game until the game is over. At each step, it displays the current game and asks the next player, X or a O, to play a cell.


## JUnit

We provide a set of JUnit tests for the TicTacToe class. These tests should help ensure that your implementation is correct. They can also help clarify the expected behavior of this class.

Please read the [junit instructions](JUNIT.md) for help with running the tests.


## Submission

Please read the [Submission Guidelines](SUBMISSION.en.md) carefully.
Submission errors will affect your grades.

Submit the following files.

* STUDENT.md
* Main.java
* CellValue.java
* GameState.java
* TicTacToe.java

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
