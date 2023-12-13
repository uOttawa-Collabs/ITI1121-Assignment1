import java.util.LinkedList;

public class TicTacToeEnumerations
{
    // YOUR CODE HERE
    int numRows, numColumns, sizeToWin;
    /**
     * The list of lists of all generated games
     */
    LinkedList<LinkedList<TicTacToe>> allGames;

    /**
     * A constructor where you can specify the dimensions
     * of your game as rows x coluns grid, and a sizeToWin
     * to analyze.
     *
     * @param aNumRows    the number of lines in the game
     * @param aNumColumns the number of columns in the game
     * @param aSizeToWin  the number of cells that must be aligned to win.
     */
    public TicTacToeEnumerations(int aNumRows, int aNumColumns, int aSizeToWin)
    {
        numRows    = aNumRows;
        numColumns = aNumColumns;
        sizeToWin  = aSizeToWin;
    }

    /**
     * Generate a list of lists of all games, storing the
     * result in the member variables `allGames`.
     */
    public LinkedList<LinkedList<TicTacToe>> generateAllGames()
    {
        allGames = new LinkedList<LinkedList<TicTacToe>>();
        allGames.add(new LinkedList<TicTacToe>());
        allGames.getFirst().add(new TicTacToe(numRows, numColumns, sizeToWin)); // For level 0, always has an empty board.

        LinkedList<TicTacToe> currentLevel = allGames.getFirst();
        for (int i = 0; i < numRows * numColumns; i++)
        {
            LinkedList<TicTacToe> previousLevel = currentLevel;
            currentLevel = new LinkedList<TicTacToe>();
            allGames.add(currentLevel);

            boolean isLevelEmptyFlag = true;
            //for (int s = 0; s < previousLevel.size(); s++)
            for (TicTacToe previousLevelGame : previousLevel)
            {
                // s represents the index of game elements in the previous level.
                if (previousLevelGame.gameState == GameState.PLAYING)
                {
                    int[] empties = previousLevelGame.emptyPositions();

                    for (int nextStep : empties)
                    {
                        TicTacToe newGame = previousLevelGame.cloneNextPlay(nextStep);

                        boolean isNewFlag = true;
                        for (TicTacToe existingGames: currentLevel)
                        {
                            if (newGame.equals(existingGames))
                            {
                                isNewFlag = false;
                                break;
                            }
                        }

                        if (isNewFlag)
                        {
                            currentLevel.add(newGame);
                            isLevelEmptyFlag = false;
                        }
                    }
                }
            }

            if (isLevelEmptyFlag)
            {
                allGames.removeLast();
                break;
            }
        }

        return allGames;
    }

    public String toString()
    {
        if (allGames == null)
        {
            return "No games generated.";
        }

        StringBuilder s = new StringBuilder();

        int numGames = 0;
        int numXWin  = 0;
        int numOWin  = 0;
        int numDraw  = 0;
        for (int i = 0; i < allGames.size(); i++)
        {
            LinkedList<TicTacToe> games           = allGames.get(i);
            int                   numStillPlaying = 0;
            for (TicTacToe g : games)
            {
                numGames += 1;
                switch (g.gameState)
                {
                    case PLAYING:
                        numStillPlaying += 1;
                        break;
                    case XWIN:
                        numXWin += 1;
                        break;
                    case OWIN:
                        numOWin += 1;
                        break;
                    case DRAW:
                        numDraw += 1;
                        break;
                }
            }
            s.append("======= level " + i + " =======: ");
            s.append(games.size() + " element(s) (");
            s.append(numStillPlaying + " still playing)\n");
        }

        s.append("that's " + numGames + " games\n");
        s.append(numXWin + " won by X\n");
        s.append(numOWin + " won by O\n");
        s.append(numDraw + " draw");
        return s.toString();
    }
}
