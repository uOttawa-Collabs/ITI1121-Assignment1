<center>
  <h1>ITI 1521. Introduction à l’informatique II</h1>
  <h3>Devoir 4</h3>
  <h3>Échéance: 28 jui 2020, 23 h 00</h3>
</center>

## Objectifs d’apprentissage

* Employer l’héritage, la composition et les classes abstraites
* Expérimenter avec l’apprentissage machine

## Introduction

Nous avons créé la base de notre implémentation de MENACE, un système automatisé qui apprend à jouer au Tic-Tac-Toe. Dans ce devoir, nous utiliserons notre solution du devoir 3, ainsi, vous devez donc d’abord la compléter. Ensuite nous l’utiliserons pour créer notre propre implémentation de la machine proposée dans l’article de Donald Michie de 1961.

## Menace

Nous sommes maintenant prêts à implémenter MENACE. Si vous ne l’avez pas encore fait, vous devriez vraiment lire l’article publié par Donald Michie en 1961 dans Science Survey, intitulé Trial and error.

Cet article a été réimprimé dans le livre On Machine Intelligence et se trouve à la page 11 sur ce site :

* https://www.gwern.net/docs/ai/1986-michie-onmachineintelligence.pdf

Vous pouvez également regarder

* https://www.youtube.com/watch?v=R9c-_neaxeU

**Dans ce qui suit, nous ne traiterons que des jeux 3 × 3, car c’est ce qui a été défini dans le document.** Vous n’avez pas à vous soucier des autres tailles de jeux, seulement 3 × 3.

Nous vous avons donné tout ce qui est nécessaire pour cette partie, vous n’avez donc qu’à vous concentrer sur la réalisation de `ComputerMenacePlayer` et `MenaceGame`. Vous devez cependant utiliser une solution fonctionnelle du devoir 3.

Dans notre solution, nous avons notre joueur MENACE, qui joue contre une série de joueurs possibles : un humain, un joueur aléatoire, un joueur parfait ou un autre joueur MENACE.

Le joueur humain et le joueur aléatoire ont déjà été implémentés dans les devoirs précédents. Nous fournissons une implémentation d’un joueur parfait. Vous n’avez pas à comprendre comment il fonctionne précisément (mais bien sûr, vous le pouvez !) mais vous devez absolument jeter un coup d’œil au code car cela vous aidera beaucoup pour l’implémentation de MENACE.

Nous avons apporté quelques modifications à la conception :

Tout d’abord, nous souhaitons que nos joueurs partagent des méthodes supplémentaires. Auparavant, il suffisait qu’un joueur donne une implémentation concrète de la méthode play.

Maintenant, nous voulons pouvoir informer le joueur qu’un nouveau jeu commence et qu’un jeu est terminé. Nous voulons que le joueur conserve quelques statistiques sur ses performances : combien de fois il a gagné et perdu au total, ainsi qu’au cours des 50 dernières parties (pour suivre ses progrès). La mise en œuvre de certaines de ces méthodes est commune à tous les joueurs, c’est pourquoi nous souhaitons ajouter le code directement dans Player.

Cependant, `Player` est une interface nous empêchant de le faire. Nous l’avons donc transformé en une classe à part entière. Nous ne pouvons toujours pas fournir une implémentation par défaut pour la méthode `play`, de sorte que cette méthode est toujours abstract, donc Player est maintenant une classe abstraite. Nous avons fourni l’implémentation pour toutes les autres méthodes de la classe Player.


Second, some of our players will need specialized version of TicTacToe. For example, our perfect player needs to enhance TicTacToe instances to record which of the possible moves are winning and which ones are losing. So we created the inner class PerfectGame, which has a TicTacToe game, but also tracks the outcome of each move
(win/lose/draw) and how many moves until that outcome.

Deuxièmement, certains de nos joueurs auront besoin d’une version spécialisée de TicTacToe. Par exemple, notre joueur idéal doit enregistrer les coups possibles qui sont gagnants et ceux qui sont perdants. Nous avons donc créé la classe PerfectGame, que ComputerPerfectPlayer utilise pour enregistrer les informations supplémentaires.

Le fonctionnement de la classe est le suivant : lorsqu’une instance de `ComputerPerfectPlayer` est créée, elle crée d’abord la liste de tous les jeux possibles, comme nous l’avons fait pour le devoir 3. Le joueur compose ensuite toutes les jeux dans les `PerfectGames`. Une fois la liste créée, l’instance `ComputerPerfectPlayer` précalcule tous les coups de toutes les parties possibles pour déterminer ceux qui doivent être joués et ceux qui doivent être évités.


Elle est alors prête à jouer les parties. Lorsque sa méthode play est appelée, elle reçoit une instance de `TicTacToe` comme paramètre, qui est l’état actuel de la partie. Il consulte sa liste de toutes les parties précalculées pour trouver celle qui correspond à l’état actuel de la partie (jusqu’à la symétrie), puis sélectionne à partir de là l’un des meilleurs coups possibles, tel que précalculé lors de l’initialisation. `ComputerMenacePlayer` fonctionnera de la même manière.

L’essentiel de MENACE est qu’il pré-calcule toutes les parties possibles (tenant compte des symétries), et pour chaque partie, il fournit initialement un certain nombre de billes pour chaque coup possible. Lorsqu’il joue une partie, MENACE trouve la partie correspondant à l’état actuel et choisit au hasard une des billes qu’il possède pour cette partie. Plus un coup donné possède de billes à ce stade, plus il a de chances d’être sélectionné. Une fois la partie terminée, MENACE met à jour le nombre de billes pour chacun des coups utilisés au cours de la partie, en fonction du résultat : si la partie a été perdue, les billes qui ont été sélectionnées seront retirées, ce qui rendra moins probable la sélection de coups similaires dans le futur.

Cette approche pose un problème : il est possible de retirer la dernière bille d’une partie de cette manière, auquel cas la menace sera bloquée si la partie est à nouveau jouée dans le futur, sans qu’aucun coup n’ait la moindre chance d’être sélectionné. Pour éviter cela, nous n’enlevons une bille que si elle n’est pas la dernière de la partie.

En résumé :

* Si le jeu est nul, la bille est remise en place et une autre bille similaire est ajoutée à chaque fois, augmentant un peu plus la chance de sélectionner ce coup dans le futur.

* Si la partie est gagnée, la bille est remise en place et trois autres billes similaires sont ajoutées.

* Si la partie est perdue, la bille n'est pas remise

* Ne retirez pas la dernière bille d'une partie.

On vous demande de fournir une implémentation de `ComputerMenacePlayer` qui se comporte comme prévu. Vous devez également implémenter `MenaceGame` que les instances de `ComputerMenacePlayer` utilisent pour pré-calculer toutes les parties possibles et enregistrer le nombre actuel de billes pour chaque coup possible de chaque partie possible.

Dans le document, on suppose que MENACE joue toujours le premier coup (et nos expériences suggèrent en fait que MENACE apprend beaucoup mieux à être un premier joueur qu’un deuxième joueur). L’instance principale de MENACE dans notre système est également réglée pour toujours jouer en premier (bien que vous puissiez facilement changer cela dans le code), mais puisque nous pouvons jouer MENACE contre MENACE, nous devons avoir une implémentation qui peut jouer à la fois X et O. Le document précise le nombre initial de billes pour X seulement. Nous utiliserons des nombres légèrement différents pour X (8,4,2,1 au lieu de 4,3,2,1) et nous utiliserons des nombres similaires pour O. En d’autres termes, le nombre initial de billes est

* Première position (X): 8 billes
* Deuxième position (O): 8 billes
* Troisième position (X): 4 billes
* Quatrième position (O): 4 billes
* Cinquième position (X): 2 billes
* Sixième position (O): 2 billes
* Septième position (X): 1 bille
* Huitième position (O): 1 bille
* Neuvième position (X): 1 bille

Le fonctionnement du système que nous fournissons est le suivant :

1. Une nouvelle instance de MENACE est créée.

2. Cette instance n’a pas été entraînée et est donc très faible. Vous pouvez choisir l’adversaire, qui est soit un ordinateur (aléatoire, parfait ou MENACE), soit un humain (vous).

3. Si vous choisissez un adversaire contrôlé par l’ordinateur, le système lancera automatiquement 500 parties entre MENACE et cet adversaire, au cours desquelles MENACE devrait améliorer son jeu (surtout la première fois) et devrait ensuite être beaucoup plus difficile à battre.

4. Nous avons également inclus une option pour réinitialiser MENACE, afin que vous puissiez facilement recommencer une étape d’apprentissage à partir de zéro.


## Entraînée votre joueur MENANCE

Voici un exemple de fonctionnement du système.

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

Nous allons d’abord effectuer deux tours contre un humain ("1") :

```
1
```

Et le sorti.

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

Jouons un deuxième jeux.

```
1
```

Et le sorti.

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

Comme on peut le voir, jusqu’à présent, MENACE ne joue pas très bien et a perdu deux fois de suite bien qu’il ait été le premier joueur. Entraînons-le maintenant contre un joueur parfait :

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

Et le sorti.

```
About to train with 500 games.
Player 1 has won 0 games, lost 182 games, and 318 were draws.
Over the last 50 games, this player has won 0, lost 4, and tied 46.

Player 2 has won 182 games, lost 0 games, and 318 were draws.
Over the last 50 games, this player has won 4, lost 0, and tied 46.
```

MENACE (qui est ici le joueur 1) a perdu 182 matchs contre le joueur parfait
sur les 500 qui ont été joués, et seulemnt 4 au cours des 50 derniers matchs.

Essayons encore une fois contre un joueur humain :

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

Maintenant, MENACE est un bien meilleur joueur, et joue en effet très bien et a obtenu un match nul. Notez cependant qu’il n’est pas parfait et qu’il peut encore être battu.

Regardez la prochaine partie :

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

Ici, nous (joueur 2) allons faire un jeu terrible (exprès, je jure) et voir comment le joueur MENACE réagit.

```
O to play: 4
 O | X | X
-----------
 O | X |
-----------
   |   |
```

On voit que MENACE n'a pas fait le meilleur choix pour gagner le match. Le joueur MENACE a
pas du tout formé pour gérer ces scénarios comme notre joueur parfait
aurait joué. Cela illustre certains des défis de l’apprentissage machine, mais c’est une toute autre discussion !


## Implementation Details and Hints

Vous êtes responsable de la mise en œuvre

* ComputerMenacePlayer.java
* MenaceGame.java

Voici quelques conseils pour vous aider à déboguer en cours de route.

### GameOutput

Du point de vue d'un joueur, un jeu a un résultat de WIN, DRAW,
PERDRE ou INCONNU.

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

Ceci est utile lors de la mise à jour du nombre de billes dans notre MenaceGame.


### Petits ensembles d'entraînement

Le `GameMain` a deux (4ieme et 5ieme) paramètres supplémentaires  pour vous aider à déboguer. Le quatrième paramètre est le nombre de matchs d'entraînement entre les joueurs de l'ordinateur (par défaut c'est 500).

Par exemple:

```
java GameMain 3 3 3 1
```

Créera notre 3x3 grille (3 pour gagner), mais on va entrainer pour 1 match seulement (pas 500).

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

### indicateur de débogage

Le cinquièmes paramètre de `GameMain` définit un indicateur `isDebug` que vous pouvez utiliser pour sortir des informations supplémentaires.

Voici comment cet indicateur est utilisé dans `ComputerPerfectPlayer`.

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

Nous voyons maintenant

```
Perfect player choosing based on:
OVERALL: DRAW(5)
 D 5 | D 5 | D 5
-----------
 D 5 | D 5 | D 5
-----------
 D 5 | D 5 | D 5
```

Qui montre les scénarios `W`in, `L`ose ou `D`raw avant de choisir.

```
 X |   |
-----------
   |   |
-----------
   |   |

O to play:
```

Voici l'extrait de code qui permet cela.

```java
if (isDebug) {
  System.out.println("Perfect player choosing based on: ");
  System.out.println(perfectGame);
  System.out.println("");
}
```

Vous pouvez faire quelque chose de similaire dans `ComputerMenancePlayer`, comme montré ci-dessous

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

Le joueur Menace montre alors le nombre aléatoire qu'il a obtenu et le tableau TicTacToe avec le nombre de billes dans chaque position ouverte.

```
Menace rolled 9 on board:
POSITION: 2 (Odds 31)
 8 | 8 | 4
-----------
 4 | 2 | 2
-----------
 1 | 1 | 1
```

Une mise en œuvre fonctionnelle de Menace jouerait donc en position 2.


```
   | X |
-----------
   |   |
-----------
   |   |
```


## Soumission

Veuillez lire attentivement les [Directives de soumission](SUBMISSION.fr.md).

Les erreurs de soumission affecteront vos notes.

Soumettez les fichiers suivante.

* STUDENT.md
* ComputerMenacePlayer.java
* MenaceGame.java

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
