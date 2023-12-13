<center>
  <h1>ITI 1521. Introduction à l’informatique II</h1>
  <h3>Devoir 2</h3>
  <h3>Échéance: 11 jun 2020, 23 h 00</h3>
</center>

## Objectifs d’apprentissage

* Utiliser des interfaces
* Appliquer le polymorphisme dans la conception d’une application
* Explorer le concept de copie profonde
* Expérimenter les listes et les énumérations

## Introduction

Dans le cadre de ce devoir, nous poursuivons notre travail sur le jeu du Tic-Tac-Toe. Lors du dernier devoir, nous avons mis au point une implémentation de base du jeu, qui peut être joué par deux humains. Cette fois, nous allons d’abord créer un «joueur informatique», qui n’est pas très intelligent mais qui peut au moins jouer selon les règles du jeu. Nous pourrons ainsi jouer humain contre ordinateur. Nous mettrons ensuite cela de côté et nous nous efforcerons d’énumérer tous les jeux possibles. Cette énumération sera utilisée plus tard lorsque nous créerons un joueur informatique qui pourra bien jouer.

## Humain contre machine (sans intelligence)

Une façon très simple de faire jouer un programme au Tic-Tac-Toe est de lui faire choisir au hasard une case vide à jouer à chaque tour. Bien sûr, une telle implémentation devrait être facile à battre, mais au moins on peut jouer contre elle.

Afin de concevoir cette solution, nous voulons introduire le concept de joueur (**Player**). Pour l’instant, nous aurons deux catégories de joueurs : le joueur humain, et le joueur informatique sans intelligence. Plus tard, nous pourrons introduire d’autres types de joueurs, par exemple un joueur informatique intelligent, un joueur parfait, etc. Ce sont tous des joueurs (de type **Player**).

![Player Interface in UML](uml_player.png)

**Figure 1: Figure 1 – L’interface Player et les deux classes qui la mettent en oeuvre.**

Ce que nous obtenons de cette abstraction (**Players**), c’est qu’il est possible d’organiser un match entre deux joueurs, et de faire jouer ces deux joueurs une série de parties, en comptant les points pour le match, etc. Nous pou- vons avoir des joueurs humains contre humains, humains contre ordinateurs sans intelligence, intelligents contre ordinateurs sans intelligence, ou n’importe quelle combinaison de joueurs, cela n’a pas d’impact sur la façon dont le jeu est joué : nous avons deux joueurs, et ils alternent en jouant un coup sur le jeu jusqu’à ce que la partie soit terminée. La condition pour pouvoir faire cela est que tous les joueurs possèdente une méthode **play()**, qui peut être appelée lorsque c’est le tour de ce joueur de jouer.

Nous pouvons choisir qui joue qui, par exemple un humain contre un ordinateur. Le joueur qui joue en premier est choisi au hasard. Dans les parties suivantes, les joueurs alternent comme premier joueur. Comme d’habitude, le premier joueur joue X et le deuxième joueur joue O, de sorte que chaque joueur alterne entre jouer X et jouer O.

L’impression suivante montre un jeu typique.


```
$ java GameMain
Player 2's turn.
Player 1's turn.

 X |   |
-----------
   |   |
-----------
   |   |

O to play:
```

Ici, le joueur 2 (l’ordinateur) a été sélectionné pour commencer la première partie.

Comme on peut le voir, le joueur de l’ordinateur n’imprime rien lorsqu’il joue, il fait juste son coup en silence. Ensuite, c’est au tour du joueur 1 (l’humain). Suivant ce que nous avons fait dans le devoir 1, l’objet HumanPlayer imprime d’abord la partie (ici, on peut voir que l’ordinateur a joué la cellule 1) et demande ensuite à l’humain (nous, l’utilisateur) de jouer. Ci-dessous, nous voyons que l’humain a sélectionné la cellule 2. L’ordinateur jouera alors (en silence) et l’humain sera à nouveau invité à jouer. Cela continue jusqu’à la fin de la partie :

```
O to play: 2
Player 2's turn.
Player 1's turn.

 X | O | X
-----------
   |   |
-----------
   |   |

O to play:
```

Et alors.

```
O to play: 6
Player 2's turn.
Player 1's turn.

 X | O | X
-----------
 X |   | O
-----------
   |   |

O to play:
```

Et alors.

```
O to play: 7
Player 2's turn.
Player 1's turn.

 X | O | X
-----------
 X | X | O
-----------
 O |   |

O to play:
```

Et alors.

```
O to play: 9
Player 2's turn.
Game over

 X | O | X
-----------
 X | X | O
-----------
 O | X | O

Result: DRAW
Play again (y)?:
```

Ce jeu se termine par un match nul (DRAW). La phrase «Game over» est imprimée après le dernier coup (effectué par l’ordinateur dans ce cas), puis le tableau final est imprimé, et le résultat de la partie («Result : DRAW»).

On demande alors à l’utilisateur s’il veut jouer à nouveau.

Ici, nous voulons jouer un autre jeu. Cette fois, c’est l’humain qui fera le premier coup. Ci-dessous, vous pouvez voir l’ensemble de la partie, qui est une victoire pour l’humain. Ensuite, une troisième partie est jouée, également une victoire pour l’humain, et nous arrêtons de jouer après cela.

```
Play again (y)?: y
Player 1's turn.

   |   |
-----------
   |   |
-----------
   |   |

X to play: 1

Player 2's turn.
Player 1's turn.

 X | O |
-----------
   |   |
-----------
   |   |

X to play: 4

Player 2's turn.
Player 1's turn.

 X | O | O
-----------
 X |   |
-----------
   |   |

X to play: 7
Game over

 X | O | O
-----------
 X |   |
-----------
 X |   |

Result: XWIN
Play again (y)?: y

Player 2's turn.
Player 1's turn.

 X |   |
-----------
   |   |
-----------
   |   |

O to play: 3

Player 2's turn.
Player 1's turn.

 X | X | O
-----------
   |   |
-----------
   |   |

O to play: 6

Player 2's turn.
Player 1's turn.

 X | X | O
-----------
 X |   | O
-----------
   |   |

O to play: 9
Game over

 X | X | O
-----------
 X |   | O
-----------
   |   | O

Result: OWIN
Play again (y)?: n
```

Nous sommes maintenant prêts à programmer notre solution. Nous allons réutiliser l’implémentation de la classe TicTacToeGame du devoir 1 avec quelques petits changements. Une classe Utils a été fournie pour avoir un accès à quelques constantes et variables globales.


### Player

_Player_ est une interface. Elle ne définit qu’une seule méthode, la méthode play. Le type de sa valeur de retour est
**boolean** (le joueur a-t-il réussi à jouer) et elle a un paramètre d’entrée, une référence à un objet de la classe TicTacToeGame.

### HumanPlayer

_HumanPlayer_ est une classe qui réalise l’interface **Player**. Dans son implémentation de la méthode **play**, elle vérifie d’abord que le jeu est effectivement jouable (et renvoie faux si ce n'est pas), puis demande à l’utilisateur une entrée valide (similaire à celui du devoir 1). Une fois qu’une telle entrée a été fournie, il joue le jeu et donne le contrôle à l’autre joueur et renvoie vrai.

### ComputerInOrderPlayer

_ComputerInOrderPlayer_ est une classe qui implémente également l'interface _Player_. Dans son implémentation de la méthode play, il vérifie d'abord que le jeu est jouable (et retourne faux s'il ne l'est pas), puis choisit la première cellule disponible.

## ComputerRandomPlayer

Faisons un joueur d'ordinateur légèrement plus intelligent.


**ComputerRandomPlayer** est une classe qui réalise également l’interface Player. Dans son implémentation de la méthode play, elle vérifie d’abord que la partie est effectivement jouable (et renvoie faux si ce n'est pas), puis choisit au hasard le cellule suivant, le joue et passe la main à l’autre joueur. Tous les cellules suivants possibles ont une chance égale d’être joués.

### GameMain

Cette classe implémente le jeu. La partie initiale est très similaire à celle du devoir 1. L’ensemble du jeu est joué dans la méthode principale. Une variable locale **players**, une référence à un tableau de deux joueurs, est utilisée pour stocker le joueur humain et le joueur informatique. Vous **devez** utiliser ce tableau pour stocker vos références **Player**.

Vous devez terminer l’implémentation de la méthode principale afin d’obtenir le comportement spécifié. Vous devez vous assurer que le premier joueur est initialement choisi au hasard et que le premier coup alterne entre les deux joueurs lors des parties suivantes.

Voici un autre exemple de jeu, cette fois sur une grille de 4×4 et 2 cellules alignées pour une victoire. Les joueurs humains font une série d’erreurs de saisie en cours de rout

Nous avons deux arguments supplémentaires par rapport à devor 1, "player1" et "player2" qui peuvent être:

* "h" pour le joueur humain
* "ic" pour le joueur d'ordinateur dans l'ordre
* "rc" pour le joueur d'ordinateur aléatoire

```
$ java GameMain h ic 4 4 2
Player 1's turn.

   |   |   |
---------------
   |   |   |
---------------
   |   |   |
---------------
   |   |   |

X to play: 2
Player 2's turn.
Player 1's turn.

 O | X |   |
---------------
   |   |   |
---------------
   |   |   |
---------------
   |   |   |

X to play: 99
The value should be between 1 and 16

X to play: 2
Cell 2 has already been played with X

X to play: 6
Game over

 O | X |   |
---------------
   | X |   |
---------------
   |   |   |
---------------
   |   |   |

Result: XWIN
Play again (Y)?:n
```


## Énumérations de jeux TicTacToe

Nous nous intéressons maintenant à autre chose : les énumérations (dénombrements) de jeux. Nous souhaitons générer tous les jeux possibles pour une taille de grille et nombre de cellules alignées pour une victoire donnés.

Par exemple, si nous prenons la grille par défaut, 3 × 3, il y a 1 grille au niveau 0, à savoir :


```
   |   |
-----------
   |   |
-----------
   |   |
```

Il y a ensuite 9 grilles au niveau 1, à savoir :

```
 X |   |
-----------
   |   |
-----------
   |   |
```

```
   | X |
-----------
   |   |
-----------
   |   |
```

```
   |   | X
-----------
   |   |
-----------
   |   |
```

```
   |   |
-----------
 X |   |
-----------
   |   |
```

```
   |   |
-----------
   | X |
-----------
   |   |
```

```
   |   |
-----------
   |   | X
-----------
   |   |
```

```
   |   |
-----------
   |   |
-----------
 X |   |
```

```
   |   |
-----------
   |   |
-----------
   | X |
```

```
   |   |
-----------
   |   |
-----------
   |   | X
```

Il y a alors 72 grilles au niveau 2, trop nombreuses pour être imprimées ici. En annexe sectionA, nous fournissons la liste complète des jeux pour une grille 2 × 2, avec une taille de victoire de 2. Notez qu’aucun jeu de niveau 4 n’apparaît sur cette liste : il est simplement impossible d’atteindre le niveau 3 et de ne pas gagner sur une grille 2 × 2 et un nombre de cellules à alignées de 2 pour une victoire. Dans notre énumération, nous n’énumérons pas deux fois le même jeu, et nous ne continuons pas après qu’une partie ait été gagnée.


### Notre implémentation

Pour cette implémentation, nous allons ajouter quelques nouvelles méthodes à notre classe **TicTacToe** et nous allons créer une nouvelle classe, **TicTacToeEnumerations**, pour générer nos jeux. Nous allons stocker nos jeux dans une liste de listes. Nous aurons très bientôt notre propre implémentation du type de données abstrait List, mais nous ne l’avons pas encore. Par conséquent, exceptionnellement pour ITI1X21, nous allons utiliser une solution clé en main. Dans ce cas, nous utiliserons java.util.linkedList. La documentation est disponible à l’adresse https://docs.oracle.com/javase/9/docs/api/java/util/LinkedList.html.

Le but est de créer une liste de listes : chaque liste contiendra tous les différents jeux pour un niveau donné. Reprenons la grille par défaut, 3 × 3. Notre liste comportera 10 éléments.

* Le premier élément est la liste des grilles 3 × 3 au niveau 0. Il y a 1 telle grille, donc cette liste a 1 élément.
* Ledeuxièmeélémentestlalistedesgrilles3×3auniveau1.Ilya9grillesdecetype,donccettelistecomporte
9 éléments.
* Le troisième élément est la liste des grilles 3 × 3 au niveau 2. Il existe 72 grilles de ce type, donc cette liste comporte 72 éléments.
* Le quatrième élément est la liste des grilles 3 × 3 au niveau 3. Il existe 252 grilles de ce type, donc cette liste comporte 252 éléments.
* Le cinquième élément est la liste des grilles 3 × 3 au niveau 4. Il y a 756 grilles de ce type, donc cette liste comporte 756 éléments.

...

* Le neuvième élément est la liste des grilles 3 × 3 au niveau 8. Il existe 390 grilles de ce type, donc cette liste comporte 390 éléments.
* Le dixième élément est la liste des grilles 3 × 3 au niveau 9. Il existe 78 grilles de ce type, donc cette liste comporte 78 éléments.


La classe **EnumerationsMain.java** vous est fournie. Elle appelle la génération de la liste et imprime quelques informations à son sujet. Voici quelques exécutions typiques :

```
$ java EnumerationsMain
======= level 0 =======: 1 element(s) (1 still playing)
======= level 1 =======: 9 element(s) (9 still playing)
======= level 2 =======: 72 element(s) (72 still playing)
======= level 3 =======: 252 element(s) (252 still playing)
======= level 4 =======: 756 element(s) (756 still playing)
======= level 5 =======: 1260 element(s) (1140 still playing)
======= level 6 =======: 1520 element(s) (1372 still playing)
======= level 7 =======: 1140 element(s) (696 still playing)
======= level 8 =======: 390 element(s) (222 still playing)
======= level 9 =======: 78 element(s) (0 still playing)
that's 5478 games
564 won by X
316 won by O
78 draw
```

Nous pouvons spécifier la taille de la grille et le nombre d'affilées à gagner

```
$ java EnumerationsMain 3 3 2
======= level 0 =======: 1 element(s) (1 still playing)
======= level 1 =======: 9 element(s) (9 still playing)
======= level 2 =======: 72 element(s) (72 still playing)
======= level 3 =======: 252 element(s) (112 still playing)
======= level 4 =======: 336 element(s) (136 still playing)
======= level 5 =======: 436 element(s) (40 still playing)
======= level 6 =======: 116 element(s) (4 still playing)
======= level 7 =======: 12 element(s) (0 still playing)
that's 1234 games
548 won by X
312 won by O
0 draw
```

Voici une petite grille 2x2.

```
$ java EnumerationsMain 2 2 2
======= level 0 =======: 1 element(s) (1 still playing)
======= level 1 =======: 4 element(s) (4 still playing)
======= level 2 =======: 12 element(s) (12 still playing)
======= level 3 =======: 12 element(s) (0 still playing)
that's 29 games
12 won by X
0 won by O
0 draw
```

Voici une grille _impossible pour gagner_ 2x2.

```
$ java EnumerationsMain 2 2 3
======= level 0 =======: 1 element(s) (1 still playing)
======= level 1 =======: 4 element(s) (4 still playing)
======= level 2 =======: 12 element(s) (12 still playing)
======= level 3 =======: 12 element(s) (12 still playing)
======= level 4 =======: 6 element(s) (0 still playing)
that's 35 games
0 won by X
0 won by O
6 draw
```

Here is a larger 5x2 board.

```
$ java EnumerationsMain 5 2 3
======= level 0 =======: 1 element(s) (1 still playing)
======= level 1 =======: 10 element(s) (10 still playing)
======= level 2 =======: 90 element(s) (90 still playing)
======= level 3 =======: 360 element(s) (360 still playing)
======= level 4 =======: 1260 element(s) (1260 still playing)
======= level 5 =======: 2520 element(s) (2394 still playing)
======= level 6 =======: 3990 element(s) (3798 still playing)
======= level 7 =======: 3990 element(s) (3290 still playing)
======= level 8 =======: 2580 element(s) (2162 still playing)
======= level 9 =======: 1032 element(s) (646 still playing)
======= level 10 =======: 150 element(s) (0 still playing)
that's 15983 games
1212 won by X
610 won by O
150 draw
```

### TicTacToe Changes

Nous devons ajouter trois nouvelles méthodes publiques à la classe TicTacToeGame :

#### cloneNextPlay

```java
public TicTacToe cloneNextPlay(int nextMove)
```

La méthode `cloneNextPlay` est utilisé pour créer une nouvelle instance de la classe `TicTacToe` basé sur l'instance actuelle. La nouvelle instance sera un jeu dont l’état est le même que celui référencé, mais dans lequel la position suivante (`nextMove`) a été jouée. Par exemple, imaginez un jeu suivant :

```
 O |   | X
-----------
 X |   |
-----------
   |   |
```

L’appel suivant :

```java
game.cloneNextPlay(7);
```

renvoie une référence au jeu suivant :

```
 O |   | X
-----------
 X |   |
-----------
 O |   |
```

Une considération importante dans la mise en œuvre de cette méthode est que le jeu référencé ne doit pas être modifié par l’appel. Consultez l’annexe B pour mieux comprendre ce qui est nécessaire pour y parvenir.


#### equals

```java
public boolean equals(Object obj)
```

La méthode compare le jeu actuel avec le jeu référencé par other. Cette méthode renvoie `true` si et seulement si les deux jeux sont considérés comme identiques : ils ont les mêmes caractéristiques, et leur grille est dans le même état.

#### emptyPositions

The `emptyPositions` returns an array of positions that are empty and available to be played on. The results are 1-based (not zero).

Par exemple, imaginez un jeu suivant :

```
 O |   | X
-----------
 X |   |
-----------
   |   |
```

L’appel suivant :

```java
game.emptyPositions();
```

renvoie :

```java
[2, 5, 6, 7, 8, 9]
```

### TicTacToeEnumerations

Cette nouvelle classe calcule un constructeur identique à `TicTacToe`

```java
public TicTacToeEnumerations(int aNumRows, int aNumColumns, int aSizeToWin)
```

Et puis implémentez la méthode `generateAllGames` pour générer la liste des listes des jeux.

```java
public LinkedList<LinkedList<TicTacToe>> generateAllGames()
```


Cette méthode retourne la liste (chaînée) des listes (chaînées) de références TicTacToe que nous cherchons, pour les jeux sur une grille `numRows` x `numColumns` avec un nombre de cellules alignées pour une victoire de `sizeToWin`. Comme précisé, chacune des listes (secondaires) contient les listes de références au jeu du même niveau. Il y a trois facteurs importants à prendre en compte lors de l’élaboration de la liste :

* Nous ne construisons des jeux que jusqu’à leur point de victoire (ou jusqu’à ce qu’ils atteignent le point d’égalité). Nous ne prolongeons jamais une partie déjà gagnée.
* Nous ne dupliquons pas les jeux. Il y a plusieurs façons d’atteindre le même état, alors assurez-vous qu’un même jeu n’est pas listé plusieurs fois.
* Nous n’incluons pas de listes vides. Comme on peut le voir dans appendice A, nous arrêtons notre énumération une fois que tous les jeux sont terminés. Dans le cas 2 × 2 avec un nombre de cellules alignées pour une victoire de 2, puisque toutes les parties sont terminées après 3 coups, la liste des listes ne comporte que 4 éléments : les parties après 0 coup, les parties après 1 coup, les parties après 2 coups et les parties après 3 coups.


## JUnit

Nous fournissons un ensemble de tests junit pour la classe TicTacToe. Ces tests devraient bien sûr permettre de s’assurer que votre implémentation est correcte. Ils peuvent aussi aider à clarifier le comportement attendu de cette classe.

Veuillez lire les [instructions junit](JUNIT.fr.md) pour obtenir de l'aide avec l'exécution des tests.

## Soumission

Veuillez lire attentivement les [Directives de soumission](SUBMISSION.fr.md).

Les erreurs de soumission affecteront vos notes.

Soumettez les fichiers suivante.

* STUDENT.md
* ComputerInOrderPlayer.java
* ComputerRandomPlayer.java
* GameMain.java
* HumanPlayer.java
* TicTacToe.java
* TicTacToeEnumerations.java

Soumettez les fichiers suivants, mais ils ne doivent pas être modifiés.

* CellValue.java
* EnumerationsMain.java
* GameState.java
* Player.java
* Utils.java

Cette soumission peut se faire en groupe de 2 +/- 1 personne. Assurez-vous que `STUDENT.md` inclut les noms de tous les participants; ne soumettez qu'une seule solution par groupe.

## Intégrité académique

Cette partie du devoir vise à sensibiliser les étudiants au plagiat et à l’intégrité académique. Veuillez lire les documents suivants.


* https://www.uottawa.ca/administration-et-gouvernance/reglement-scolaire-14-autres-informations-import
* https://www.uottawa.ca/vice-recteur-etudes/integrite-etudes

Les cas de plagiat seront traités conformément au règlement de l’université. En soumettant ce travail, vous reconnaissez :

1. J’ai lu le règlement académique sur la fraude académique.
2. Je comprends les conséquences du plagiat.
3. À l’exception du code source fourni par les instructeurs pour ce cours, tout le code source est le mien.
4. Je n’ai collaboré avec aucune autre personne, à l’exception de mon partenaire dans le cas d’un travail d’équipe.

* Si vous avez collaboré avec d’autres personnes ou obtenu le code source sur le Web, veuillez alors indiquer le nom de vos collaborateurs ou la source de l’information, ainsi que la nature de la collaboration. Mettez ces informations dans le fichier STUDENT.md. Des points seront déduits proportionnellement au niveau de l’aide fournie (de 0 à 100%).

## Appendix A: Énumération de tous les jeux d’une grille 2 × 2

```
======= level 0 =======: 1 element(s)
   |
-------
   |
```

```
======= level 1 =======: 4 element(s)
 X |
-------
   |

   | X
-------
   |

   |
-------
 X |

   |
-------
   | X
```

```
======= level 2 =======: 12 element(s)

 X | O
-------
   |

 X |
-------
 O |

 X |
-------
   | O

 O | X
-------
   |

   | X
-------
 O |

   | X
-------
   | O

 O |
-------
 X |

   | O
-------
 X |

   |
-------
 X | O

 O |
-------
   | X

   | O
-------
   | X

   |
-------
 O | X
```

```
======= level 3 =======: 12 element(s)

 X | O
-------
 X |

 X | O
-------
   | X

 X | X
-------
 O |

 X |
-------
 O | X

 X | X
-------
   | O

 X |
-------
 X | O

 O | X
-------
 X |

 O | X
-------
   | X

   | X
-------
 O | X

   | X
-------
 X | O

 O |
-------
 X | X

   | O
-------
 X | X
```

## Appendix B: Shallow copy versus Deep copy

Comme vous le savez, les objets ont des variables qui ont soit un type primitif, soit un type référence. Les va- riables d’un type primitif contiennent une valeur d’un type primitif du langage, tandis que les variables référence contiennent une référence (l’adresse) d’un autre objet (y compris les tableaux, qui sont des objets en Java).

Si vous copiez l’état actuel d’un objet, afin d’obtenir un objet doublon, vous créerez une copie de chacune des variables. Ce faisant, la valeur de chaque variable primitive d’instance sera dupliquée (ainsi, la modification de l’une de ces valeurs dans l’une des copies ne modifiera pas la valeur de l’autre copie). Toutefois, dans le cas des variables référence, ce qui sera copié est la référence elle-même, c’est-à-dire l’adresse de l’objet vers lequel cette variable pointe. Par conséquent, les variables référence de l’objet original et de l’objet dupliqué pointeront vers la même adresse, et les variables référence feront référence aux mêmes objets. C’est ce que l’on appelle une copie de surface (shallow copy) : vous avez en effet deux objets, mais ils partagent tous les objets pointés par leurs variables référence d’instance. La figure 2 fournit un exemple : l’objet référencé par la variable b est une copie de surface de l’objet référencé par la variable a : il possède ses propres copies des variables d’instance, mais les variables référence title et time font référence aux mêmes objets.

Souvent, une copie superficielle n’est pas suffisante : ce qu’il faut, c’est une copie dite profonde. Une copie profonde diffère d’une copie de surface en ce que les objets référencés par une variable référence doivent également être dupliqués de manière récursive, de telle sorte que lorsque l’objet initial est copié (en profondeur), la copie ne partage aucune référence avec l’objet initial. La figure 3 fournit un exemple : cette fois, l’objet référencé par la variable b est une copie profonde de l’objet référencé par la variable a : maintenant, les variables référence title et time référencent des objets différents. Notez que, à leur tour, les objets référencés par la variable time ont également été copiés en profondeur. L’ensemble des objets accessibles à partir de a ont été dupliqués.

![Shallow Copy](shallow_copy.png)

**Figure 2: A example of a shallow copy of objects.**

![Deep Copy](deep_copy.png)

**Figure 3: A example of a deep copy of objects.**

Vous pouvez en lire plus sur la différence entre copie de surface et copie profonde sur Wikipedia :

https://fr.wikipedia.org/wiki/Copie_d'un_objet