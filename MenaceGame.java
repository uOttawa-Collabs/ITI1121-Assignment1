import java.util.Random;

public class MenaceGame
{
    TicTacToe game;
    int[]     boardOdds;
    int       totalOdds;
    int       currentPosition;
    final int[] originalBoardOdds = new int[] { 8, 8, 4, 4, 2, 2, 1, 1, 1 };

    /**
     * A menace game keeps an instance of a TicTacToe game
     * instead of extending it. We can chat about why
     * during our Monday meetups.
     * <p>
     * Hint: Take a look at the implementation of a PerfectGame
     * for ideas on how to structure a MenaceGame.
     */
    public MenaceGame(TicTacToe aGame)
    {
        game      = aGame;
        totalOdds = 0;
        resetOdds();

        for (int i = 0; i < game.board.length; ++i)
        {
            if (game.board[i] == CellValue.EMPTY)
            {
                totalOdds += boardOdds[i];
            }
            else
            {
                boardOdds[i] = 0;
            }
        }
    }

    /**
     * The game is over.
     * If you won, then add three beads to the current position's odds.
     * If you tied, only add 1 bead
     * If you lost, then remove a bead.
     * <p>
     * Note: You can never have 0 beads in a game
     * and do not forget to correctly update your totalOdds
     *
     * @param outcome The outcome of the game.
     */
    public void setOutcome(GameOutcome outcome)
    {
        switch (outcome)
        {
            case WIN:
                boardOdds[currentPosition - 1] += 3;
                totalOdds += 3;
                break;
            case DRAW:
                boardOdds[currentPosition - 1] += 1;
                totalOdds += 1;
                break;
            case LOSE:
                if (boardOdds[currentPosition - 1] > 1)
                {
                    boardOdds[currentPosition - 1] -= 1;
                    totalOdds -= 1;
                }
                break;
        }
    }

    /**
     * Roll the dice, and set the current position
     * If no positions are available, then return 0
     * (which is an invalid position)
     *
     * @return The current positionThe random number rolled.
     */
    public int pickCurrentPosition()
    {
        for (CellValue cv: game.board)
        {
            if (cv == CellValue.EMPTY)
            {
                currentPosition = calculatePosition(rollDice());
                return currentPosition;
            }
        }
        return 0;
    }

    /**
     * Generate a random number.
     * <p>
     * Consider the following 3x3 board.
     * <p>
     *    | X |
     * -----------
     *  O |   |
     * -----------
     *    |   |
     * <p>
     * If we had the following beads (I left the Xs and Os off)
     * <p>
     *  5 |  | 6
     * -----------
     *   | 1 | 1
     * -----------
     * 3 | 4 | 8
     * <p>
     * Then our total odds are 28 (5+6+1+1+3+4+8) and we
     * want our random number generator to generate numbers
     * between 1 and 28.
     *
     * @return The random number rolled.
     */
    public int rollDice()
    {
        if (game.gameState != GameState.PLAYING)
            return 0;
        else
            return new Random().nextInt(totalOdds) + 1;
    }

    /**
     * Based on the diceRoll, calculate which position
     * on the board should be played based on the current odds (beads)
     * in each available cell.
     * <p>
     * On a 3x3 board.
     * <p>
     *   | X |
     * -----------
     * O |   |
     * -----------
     *   |   |
     * <p>
     * If we had the following beads (I left the Xs and Os off)
     * <p>
     * 5 |   | 6
     * -----------
     *   | 1 | 1
     * -----------
     * 3 | 4 | 8
     * <p>
     * Here are some expected outputs based on sample diceRolls
     * <p>
     * diceRoll 3 => returns 1
     * diceRoll 11 => returns 3
     * diceRoll 12 => returns 5
     * diceRoll 14 => return 7
     *
     * @return int which position on the board should we choose
     */
    public int calculatePosition(int diceRoll)
    {
        if (game.gameState != GameState.PLAYING || diceRoll <= 0 || diceRoll > totalOdds)
        {
            return 0;
        }

        int i;
        for (i = 0; i < boardOdds.length && diceRoll > 0; ++i)
        {
            if (game.valueAt(i + 1) == CellValue.EMPTY)
                diceRoll -= boardOdds[i];
        }

        return i;
    }

    /**
     * Reset the odds back to an un-trained set based on
     * Menace algorithm.
     */
    public void resetOdds()
    {
        boardOdds = new int[game.board.length];

        for (int i = 0; i < game.board.length; ++i)
        {
            if (game.board[i] == CellValue.EMPTY)
            {
                boardOdds[i] = originalBoardOdds[i];
            }
            else
            {
                boardOdds[i] = 0;
            }
        }
    }


    public String toString()
    {
        StringBuilder b               = new StringBuilder();
        int           maxRowsIndex    = game.numRows - 1;
        int           maxColumnsIndex = game.numColumns - 1;

        String lineSeparator = Utils.repeat("---", game.numColumns) + Utils.repeat("-", game.numColumns - 1);

        b.append("POSITION: " + currentPosition + " (Odds " + totalOdds + ")\n");

        for (int i = 0; i < game.numRows; i++)
        {
            for (int j = 0; j < game.numColumns; j++)
            {
                int index = i * game.numColumns + j;

                b.append(" ");

                CellValue cv = game.valueAt(game.boardIndexes[index] + 1);

                if (cv == CellValue.EMPTY)
                    b.append(boardOdds[game.boardIndexes[index]]);
                else
                    b.append(game.board[game.boardIndexes[index]]);

                b.append(" ");

                if (j < maxColumnsIndex)
                {
                    b.append("|");
                }
            }

            // Line separator after each row, except the last
            if (i < maxRowsIndex)
            {
                b.append("\n");
                b.append(lineSeparator);
                b.append("\n");
            }
        }

        return b.toString();
    }
}
