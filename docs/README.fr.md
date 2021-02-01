<center>
  <h1>ITI 1521. Introduction à l’informatique II</h1>
  <h3>Devoir 1</h3>
  <h3>Échéance: 21 mai 2020, 23 h 00</h3>
</center>

## Objectifs d’apprentissage

* Éditer, compiler et exécuter des programmes Java
* Utiliser des tableaux pour conserver les informations
* Appliquer les concepts de base de la programmation orientée objet
* Comprendre les politiques de l’université en matière d’intégrité académique

## Introduction

Nous allons implémenter le jeu Tic-Tac-Toe. Le jeu en lui-même est assez simple et bien connu. Vous pouvez rafraîchir vos connaissances , par exemple ici [sur le Tic-Tac-Toe](https://fr.wikipedia.org/wiki/Tic-tac-toe).

Notre but ultime est de programmer un algorithme d’apprentissage machine qui apprendra automatiquement à devenir un bon joueur de Tic-Tac-Toe. Nous baserons notre approche sur un article publié par Donald Michie en 1961 dans Science Survey, intitulé Trial and error. Cet article a été réimprimé dans le livre On Machine Intelligence et se trouve à la page 11 à l’URL suivante :

* [Machine Intelligence 1986](https://www.gwern.net/docs/ai/1986-michie-onmachineintelligence.pdf)

Pour une interprétation plus moderne de la même idée, vous pouvez aussi regarder

* [The pile of matchboxes which can learn](https://www.youtube.com/watch?v=R9c-_neaxeU)

Mais pour ce devoir, notre objectif est plus modeste : nous voulons simplement mettre en place un jeu de Tic-Tac-Toe, où les joueurs indiquent leur prochain coup depuis la ligne de commande. Dans sa configuration par défaut, cela ressemblera à ceci : le programme affiche d’abord une grille vide et demande au premier joueur (X) une entrée.

```
$ java Main

   |   |
-----------
   |   |
-----------
   |   |

X to play:
```

Le premier joueur a joué la cellule 5. Le programme affiche la partie en cours, avec la cellule numéro cinq occupée par X, et demande au deuxième joueur (O) de faire une entrée. Le jeu continuera à suivre ce modèle.

```
X to play: 5

   |   |
-----------
   | X |
-----------
   |   |

O to play:
```

L'utilisateur tape "2" dans le terminal.


```
O to play: 2

   | O |
-----------
   | X |
-----------
   |   |

X to play:
```

L'utilisateur tape "1" dans le terminal.

```
X to play: 1

 X | O |
-----------
   | X |
-----------
   |   |

O to play:
```

L'utilisateur tape "9" dans le terminal.

```
O to play: 9
 X | O |
-----------
   | X |
-----------
   |   | O

X to play:
```

L'utilisateur tape "4" dans le terminal.

```
X to play: 4

 X | O |
-----------
 X | X |
-----------
   |   | O

O to play:
```

L'utilisateur tape "6" dans le terminal.

```
O to play: 6

 X | O |
-----------
 X | X | O
-----------
   |   | O

X to play:
```

L'utilisateur tape "7" dans le terminal.

```
X to play: 7

 X | O |
-----------
 X | X | O
-----------
 X |   | O

Result: XWIN
```

Comme on peut le voir, à chaque tour le programme imprime l’état actuel de la partie et demande ensuite au prochain utilisateur (X ou O) de lui fournir son prochain coup. Nous supposons simplement que les cellules sont numérotées ligne par ligne, du haut à gauche au bas à droite, comme suit :

```
 1 | 2 | 3
-----------
 4 | 5 | 6
-----------
 7 | 8 | 9
```

Donc dans la partie ci-dessus, le premier coup du joueur ( qui joue avec X) est de sélectionner la cellule 5, qui est au milieu de la grille. Le coup initial du second joueur ( qui joue avec O) est de sélectionner la cellule 2, qui est au milieu de la première ligne (et une erreur fatale). Après quelques coups supplémentaires, le premier joueur gagne.

Notre propre implémentation sera un peu plus générale que le jeu habituel sur une grille 3 × 3. Par défaut (comme montré ci-dessus), notre jeu sera en effet joué sur une grille 3 × 3, en essayant d’aligner 3 cellules similaires horizontalement, verticalement ou en diagonale. Mais notre implémentation plus générale acceptera 3 paramètres en entrée n, m et k pour jouer un jeu sur une grille n × m en essayant d’aligner k des cellules similaires horizontalement, verticalement ou en diagonale. Voici un exemple de jeu sur une grille 3 × 4, essayant d’aligner 3 cellules similaires.

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

L'utilisateur tape "2" dans le terminal.


```
X to play: 2

   | X |   |
---------------
   |   |   |
---------------
   |   |   |

O to play:
```

L'utilisateur tape "6" dans le terminal.


```
O to play: 6

   | X |   |
---------------
   | O |   |
---------------
   |   |   |

X to play:
```

L'utilisateur tape "7" dans le terminal.

```
X to play: 7

   | X |   |
---------------
   | O | X |
---------------
   |   |   |

O to play:
```

L'utilisateur tape "4" dans le terminal.

```
O to play: 4

   | X |   | O
---------------
   | O | X |
---------------
   |   |   |

X to play:
```

L'utilisateur tape "12" dans le terminal.

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

Dans cette application, nous devons enregistrer l’«état» d’une partie : elle peut être encore en cours, ou l’un ou l’autre des joueurs a gagné, ou encore elle peut être nulle. De même, nous devons enregistrer l’état d’une cellule sur le plateau : une cellule peut être vide, ou elle peut contenir un X ou un O.

Il y a plusieurs façons d’y parvenir, mais dans ce devoir, nous allons utiliser le type Enum de Java.

Certains programmeurs utilisent des valeurs de type int pour représenter des constantes symboliques dans leurs programmes. Par exemple, pour représenter le jour de la semaine ou le mois de l’année.

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

L’utilisation de constantes, telles que `MONDAY` et `JANUARY`, améliore la lisibilité du code source. Comparez “`if (jour == LUNDI) { ...}`” à “`if (jour == 1) { ...}`”. C’est un pas dans la bonne direction.

Cependant, comme toutes les constantes sont des valeurs entières, il y a plusieurs types d’erreurs que le compilateur ne peut pas détecter. Par exemple, si le programmeur utilise le même nombre pour deux constantes, le compilateur ne pourra pas l’aider, 7 est une valeur valide pour `SATURDAY` et `SUNDAY` :

```java
public static final int SATURDAY = 7;
public static final int SUNDAY = 7;
```

Mais aussi, assigner une valeur représentant un **mois** à une variable représentant un **jour** de la semaine ne serait pas détecté par le compilateur, les deux sont de type `int` :

```java
int day = JANUARY;
```

Les types énumérés ont les mêmes avantages que les constantes symboliques ci-dessus, ce qui rend le code plus lisible, mais d’une manière sûre du point de vue des types.

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

Dans le programme ci-dessus, chaque constante a une valeur unique. De plus, l’énoncé ci-dessous provoque une erreur de compilation, comme il se doit :

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

• https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html

## Notre implémentation

Nous sommes maintenant prêts à programmer notre solution. Nous n’aurons besoin que de quatre classes pour cela. Pour le devoir, vous devez suivre les modèles que nous vous fournissons. Vous ne pouvez modifier aucune des signatures des méthodes (c’est-à-dire que vous ne pouvez pas modifier les méthodes du tout). Vous ne pouvez pas ajouter de nouvelles méthodes ou variables de visibilité public. Vous pouvez, cependant, ajouter de nouvelles méthodes dont la visibilité est private pour améliorer la lisibilité ou l’organisation de votre code.


### GameState

GameState est un type enuméré qui est utilisé pour décrire l’état actuel du jeu. Il a quatre valeurs possibles :

* PLAYING: ce jeu est en cours,
* DRAW: ce jeu est un match nul,
* XWIN: ce jeu a été gagné par le premier joueur,
* OWIN: ce jeu a été gagné par le premier joueur,

### CellValue

CellValue est un type énuméré qui est utilisé pour décrire l’état d’une cellule. Il a quatre valeurs possibles :

* INVALID: la cellule n'est pas dans notre jeu et n'est pas valide
* EMPTY: la cellule est vide,
* X: il y a un X dans la cellule,
* O: il y a un O dans la cellule.

Indice: C'est possible de changer le `toString()` d'un enum.

Par exemple

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

Les instances de la classe TicTacToe représentent un jeu en cours. Chaque objet contient le plateau actuel, qui est sauvegardé dans un tableau à une dimension. Il y a une méthode d’instance qui peut être utilisée pour jouer le prochain coup. L’objet détermine le tour du joueur, de sorte que l’information n’est pas spécifiée : on spécifie simplement la position à jouer et l’objet sait comment jouer soit un X soit un O. L’objet enregistre aussi automatiquement l’état de la partie.

La spécification de notre classe TicTacToe est donnée votre répo git. Vous devez remplir toutes les parties manquantes, en lisant attentivement tous les commentaires avant de le faire. Vous ne pouvez pas modifier les méthodes ou les variables qui sont fournies. Vous pouvez, cependant, ajouter de nouvelles méthodes de visibilité private si nécessaire.

Le modèle que vous utilisez contient les éléments suivants :

* Une variable d’instance qui pointe vers un tableau d’objets de type CellValue afin de représenter l’état de la grille.
* Quelques variables d’instance pour enregistrer le nombre de colonnes et de lignes du jeu, le nombre de cellules à aligner, le nombre de tours joués ("rounds") et l’état actuel.
* Deux constructeurs : celui par défaut crée le jeu habituel (la grille 3x3, avec un vainqueur si 3 cellules similaires sont alignées), un second est utilisé pour spécifier le nombre de lignes, le nombre de colonnes et le nombre de cellules à aligner. Comme d’habitude, toutes les variables d’instance doivent être initialisées lors de la construction de l’objet.
* Une méthode pour interroger l’objet sur le prochain joueur (c’est-à-dire, est-ce le tour de X ou de O de jouer ?).
* Une méthode show() pour montrer l'état publique de le jeux.
* Une méthode play(int position) pour jouer à un endroit particulier dans le jeu. Ceci met à jour l’état du jeu et la grille.
* Nous avons aussi quelque méthode auxiliaire,
  * `checkForWinner(int position)`, qui est utilisée pour calculer l’état du jeu une fois qu’un coup particulier est joué dans la méthode play.
  * `valueOf(...)` (deux méthodes) pour montrer la valeur d'une cellule dans la grille.
* Nous avons une méthode toString(), qui retourne une représentation sous forme de chaîne de caractères de l’état actuel du jeu, comme montré dans l’exemple précédent.
* On n'utilise pas des méthodes d’accès («getters»), mais il y a un "toDebug()" pour montrer l'état de votre jeux.

Le comportement specifique est decris dans les test automatiser dans junit.zip

Un exemple d’une chaîne de caractères retournée par `toString()` serait, lors de l’impression :

```
   | X |   | O
---------------
   | O | X |
---------------
   |   |   | X
```

Il y a quelques situations qui méritent notre attention. Par exemple, l’index sélectionné par le joueur peut être
invalide ou illégal. Nous n’avons pas encore de très bons mécanismes pour gérer ces situations, donc pour le moment
nous allons simplement écrire un message d’erreur. Le comportement ultérieur de la méthode n’est pas spécifié, donc il suffit d’implémenter quelque chose qui semble avoir un sens <sup>1</sup>. Une autre situation serait que les joueurs continuent le jeu après que l’un d’eux ait gagné. Pour les besoins des tests, nous voulons que cela soit possible, mais l’état du jeu doit alors refléter le premier gagnant de la partie. Ainsi, si les joueurs continuent après une victoire, un message est imprimé mais le jeu continue tant que les coups sont légaux. Le «premier» gagnant subsiste.

<sup>1</sup> La raison pour laquelle nous ne spécifions aucun comportement ici est qu’une fois que nous aurons les outils nécessaires pour traiter ces situations exceptionnelles, nous verrons que nous n’aurons pas du tout à trouver un comportement alternatif.

Notez que la méthode toString() retourne une référence vers une chaîne de caractères, elle n’imprime rien en réalité. Ainsi, une instance de String, lorsqu’elle est imprimée, devrait produire la sortie attendue (en d’autres termes, cette instance de String, lorsqu’elle est imprimée, occupera plusieurs lignes).

Si la cellule fournie par le joueur est invalide ou illégale, un message est affiché à l’utilisateur, qui est invité à jouer à nouveau. Voici quelques exemples de cette situation :

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

Notez que vous pouvez supposer que les joueurs ne fournissent que des valeurs entières comme entrées. Vous n’avez pas à gérer le cas où d’autres types d’entrée sont fournis, comme par exemple un caractère.

### Main

Cette classe implémente le jeu. On vous fournit une implementation, qui crée l’instance de la classe TicTacToe en fonction des paramètres soumis par l’utilisateur. Il s’agit en fait de boucler sur chaque étape du jeu jusqu’à ce que le jeu soit terminé. A chaque étape, il affiche le jeu en cours et demande au joueur suivant, X ou a O, de jouer une cellule.

## JUnit

Nous fournissons un ensemble de tests junit pour la classe TicTacToe. Ces tests devraient bien sûr permettre de s’assurer que votre implémentation est correcte. Ils peuvent aussi aider à clarifier le comportement attendu de cette classe.

Veuillez lire les [instructions junit](JUNIT.md) pour obtenir de l'aide avec l'exécution des tests.

## Soumission

Veuillez lire attentivement les [Directives de soumission](SUBMISSION.fr.md).

Les erreurs de soumission affecteront vos notes.

Soumettez les fichiers suivante.

* STUDENT.md
* Main.java
* CellValue.java
* GameState.java
* TicTacToe.java

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
