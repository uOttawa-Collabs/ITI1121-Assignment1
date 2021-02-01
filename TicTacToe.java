/**
 * The class <b>TicTacToe</b> is the
 * class that implements the Tic Tac Toe Game.
 * It contains the grid and tracks its progress.
 * It automatically maintain the current state of
 * the game as players are making moves.
 * <p>
 * Originally written by Guy-Vincent Jourdan, University of Ottawa
 */
public class TicTacToe
{
    /**
     * The internal representation of the board
     * as a one dimensional array, but visualized
     * as a 2d board based on the number of rows
     * and number of columns.
     * <p>
     * For example, below is a board of 3 rows
     * and 4 columns.  The board would be an array
     * of size 12 shown below.
     * <p>
     * 1  |  2  | 3  | 4
     * --------------------
     * 5  |  6  | 7  | 8
     * --------------------
     * 9  | 10  | 11 | 12
     */
    CellValue[] board;

    /**
     * The number of rows in your grid.
     */
    int numRows;

    /**
     * The number of columns in your grid.
     */
    int numColumns;

    /**
     * How many rounds have the players played so far.
     */
    int numRounds;

    /**
     * What is the current state of the game
     */
    GameState gameState;

    /**
     * How many cells of the same type must be
     * aligned (vertically, horizontally, or diagonally)
     * to determine a winner of the game
     */
    int sizeToWin;

    /**
     * Who is the current player?
     */
    CellValue currentPlayer;

    /**
     * The default empty constructor.  The default game
     * should be a 3x3 grid with 3 cells in a row to win.
     */
    public TicTacToe()
    {
        this(3, 3, 3);
    }

    /**
     * A constructor where you can specify the dimensions
     * of your game as rows x coluns grid, and a sizeToWin
     *
     * @param aNumRows    the number of lines in the game
     * @param aNumColumns the number of columns in the game
     * @param aSizeToWin  the number of cells that must be aligned to win.
     */
    public TicTacToe(int aNumRows, int aNumColumns, int aSizeToWin)
    {
        numRows    = aNumRows;
        numColumns = aNumColumns;
        sizeToWin  = aSizeToWin;

        board = new CellValue[numRows * numColumns];
        for (int i = 0; i < board.length; ++i)
        {
            board[i] = CellValue.EMPTY;
        }

        numRounds     = 1;
        gameState     = GameState.PLAYING;
        currentPlayer = CellValue.EMPTY;
    }

    /**
     * Who should play next (X or O).
     * <p>
     * This method does not modify the state of the game.
     * Instead it tells you who should play next.
     *
     * @return The player that should play next.
     */
    public CellValue nextPlayer()
    {
        if (currentPlayer == CellValue.X)
        {
            return CellValue.O;
        }
        else
        {
            return CellValue.X;
        }
    }

    /**
     * What is the value at the provided cell based on the
     * grid of numRows x numColumns as illustrated below.
     * <p>
     * 1  |  2  | 3  | 4
     * --------------------
     * 5  |  6  | 7  | 8
     * --------------------
     * 9  | 10  | 11 | 12
     * <p>
     * Note that the input is 1-based (HINT: arrays are 0-based)
     * <p>
     * If the position is invalid, return CellValue.INVALID.
     *
     * @param position The position on the board to look up its current value
     *
     * @return The CellValue at that position
     */
    public CellValue valueAt(int position)
    {
        if (position > numRows * numColumns || position < 1)
        {
            return CellValue.INVALID;
        }
        else
        {
            return board[position - 1];
        }
    }

    /**
     * What is the value at the provided row and column number.
     * <p>
     * [1,1]  | [1,2]  | [1,3]  | [1,4]
     * ----------------------------------
     * [2,1]  | [2,2]  | [2,3]  | [2,4]
     * ----------------------------------
     * [3,1]  | [3,2]  | [3,3] | [2,4]
     * <p>
     * Note that the input is 1-based (HINT: arrays are 0-based)
     * <p>
     * If the row/column is invalid, return CellValue.INVALID.
     *
     * @param position The position on the board to look up its current value
     *
     * @return The CellValue at that row/column
     */
    public CellValue valueAt(int row, int column)
    {
        if (row > numRows || row < 1 || column > numColumns || column < 1)
        {
            return CellValue.INVALID;
        }
        else
        {
            return board[(row - 1) * numColumns + column - 1];
        }
    }

    /**
     * Display the state of the board
     * And ask the next player to play.
     * Return the messages as an array of
     * Strings so that the caller can decide
     * how to display them (and it makes things
     * easier to test)
     *
     * @return An array of messages to display.
     */
    public String[] show()
    {
        String[] ret = {toString(), nextPlayer().toString() + " to play: "};
        return ret;
    }

    /**
     * The next player has decided their move to the
     * provided position.
     * <p>
     * <p>
     * 1  |  2  | 3  | 4
     * --------------------
     * 5  |  6  | 7  | 8
     * --------------------
     * 9  | 10  | 11 | 12
     * <p>
     * A position is invalid if:
     * a) It's off the board (e.g. based on the above < 1 or > 12)
     * b) That cell is not empty (i.e. it's no longer available)
     * <p>
     * If the position is invalid, an error should be printed out.
     * <p>
     * If the position is valid, then
     * a) Update the board
     * b) Update the state of the game
     * c) Allow the next player to play.
     * <p>
     * A game can continue even after a winner is declared.
     * If that is the case, a message should be printed out
     * (that the game is infact over), but the move should
     * still be recorded.
     * <p>
     * The winner of the game is the player who won first.
     *
     * @param position The position that has been selected by the next player.
     *
     * @return A message about the current play (see tests for details)
     */
    public String play(int position)
    {
        StringBuilder ret = new StringBuilder();

        CellValue cellValue = valueAt(position);

        if (cellValue == CellValue.EMPTY)
        {
            board[position - 1] = nextPlayer();
            GameState myGameState = checkForWinner(position);

            if (gameState == GameState.PLAYING && myGameState != GameState.PLAYING)
            {
                gameState = myGameState;
                ret.append("Result: ").append(gameState.toString());
            }

            currentPlayer = nextPlayer();
            ++numRounds;
        }
        else if (cellValue == CellValue.INVALID)
        {
            ret.append("The value should be between 1 and ").append(numRows * numColumns);
        }
        else
        {
            ret.append("Cell " + position + " has already been played with ").append(cellValue.toString());
        }

        if (ret.toString().equals(""))
        {
            return null;
        }
        else
        {
            return ret.toString();
        }
    }

    /**
     * A help method to determine if the game has been won
     * to be called after a player has played
     * <p>
     * This method is called after the board has been updated
     * and provides the last position that was played
     * (to help you analyze the board).
     *
     * @param position The middle position to start our check
     *
     * @return GameState to show if XWIN or OWIN.  If the result was a DRAW, or if
     * the game is still being played.
     */
    private GameState checkForWinner(int position)
    {
        final int SEARCH_RANGE = sizeToWin - 1;
        final int FOCUS_ROW = (position - 1) / numColumns + 1;
        final int FOCUS_COLUMN = (position - 1) % numColumns + 1;

        int countSuccessiveX = 0;
        int countSuccessiveO = 0;
        int maxCountSuccessiveX = 0;
        int maxCountSuccessiveO = 0;
        int finalMaxCountSuccessiveX = 0;
        int finalMaxCountSuccessiveO = 0;

        // Up / Down
        for (int i = FOCUS_ROW - SEARCH_RANGE; i <= FOCUS_ROW + SEARCH_RANGE; ++i)
        {
            switch (valueAt(i, FOCUS_COLUMN))
            {
                case INVALID:
                case EMPTY:
                    maxCountSuccessiveX = countSuccessiveX > maxCountSuccessiveX ? countSuccessiveX : maxCountSuccessiveX;
                    maxCountSuccessiveO = countSuccessiveO > maxCountSuccessiveO ? countSuccessiveO : maxCountSuccessiveO;
                    countSuccessiveX = 0;
                    countSuccessiveO = 0;
                    break;
                case X:
                    maxCountSuccessiveO = countSuccessiveO > maxCountSuccessiveO ? countSuccessiveO : maxCountSuccessiveO;
                    countSuccessiveO = 0;
                    ++countSuccessiveX;
                    break;
                case O:
                    maxCountSuccessiveX = countSuccessiveX > maxCountSuccessiveX ? countSuccessiveX : maxCountSuccessiveX;
                    countSuccessiveX = 0;
                    ++countSuccessiveO;
                    break;
            }
        }
        finalMaxCountSuccessiveX = maxCountSuccessiveX > finalMaxCountSuccessiveX ? maxCountSuccessiveX : finalMaxCountSuccessiveX;
        finalMaxCountSuccessiveO = maxCountSuccessiveO > finalMaxCountSuccessiveO ? maxCountSuccessiveO : finalMaxCountSuccessiveO;

        // Left / Right
        for (int i = FOCUS_COLUMN - SEARCH_RANGE; i <= FOCUS_COLUMN + SEARCH_RANGE; ++i)
        {
            switch (valueAt(FOCUS_ROW, i))
            {
                case INVALID:
                case EMPTY:
                    maxCountSuccessiveX = countSuccessiveX > maxCountSuccessiveX ? countSuccessiveX : maxCountSuccessiveX;
                    maxCountSuccessiveO = countSuccessiveO > maxCountSuccessiveO ? countSuccessiveO : maxCountSuccessiveO;
                    countSuccessiveX = 0;
                    countSuccessiveO = 0;
                    break;
                case X:
                    maxCountSuccessiveO = countSuccessiveO > maxCountSuccessiveO ? countSuccessiveO : maxCountSuccessiveO;
                    countSuccessiveO = 0;
                    ++countSuccessiveX;
                    break;
                case O:
                    maxCountSuccessiveX = countSuccessiveX > maxCountSuccessiveX ? countSuccessiveX : maxCountSuccessiveX;
                    countSuccessiveX = 0;
                    ++countSuccessiveO;
                    break;
            }
        }
        finalMaxCountSuccessiveX = maxCountSuccessiveX > finalMaxCountSuccessiveX ? maxCountSuccessiveX : finalMaxCountSuccessiveX;
        finalMaxCountSuccessiveO = maxCountSuccessiveO > finalMaxCountSuccessiveO ? maxCountSuccessiveO : finalMaxCountSuccessiveO;

        // Main Diagonal
        for (int i = -SEARCH_RANGE; i <= SEARCH_RANGE; ++i)
        {
            switch (valueAt(FOCUS_ROW + i, FOCUS_COLUMN + i))
            {
                case INVALID:
                case EMPTY:
                    maxCountSuccessiveX = countSuccessiveX > maxCountSuccessiveX ? countSuccessiveX : maxCountSuccessiveX;
                    maxCountSuccessiveO = countSuccessiveO > maxCountSuccessiveO ? countSuccessiveO : maxCountSuccessiveO;
                    countSuccessiveX = 0;
                    countSuccessiveO = 0;
                    break;
                case X:
                    maxCountSuccessiveO = countSuccessiveO > maxCountSuccessiveO ? countSuccessiveO : maxCountSuccessiveO;
                    countSuccessiveO = 0;
                    ++countSuccessiveX;
                    break;
                case O:
                    maxCountSuccessiveX = countSuccessiveX > maxCountSuccessiveX ? countSuccessiveX : maxCountSuccessiveX;
                    countSuccessiveX = 0;
                    ++countSuccessiveO;
                    break;
            }
        }
        finalMaxCountSuccessiveX = maxCountSuccessiveX > finalMaxCountSuccessiveX ? maxCountSuccessiveX : finalMaxCountSuccessiveX;
        finalMaxCountSuccessiveO = maxCountSuccessiveO > finalMaxCountSuccessiveO ? maxCountSuccessiveO : finalMaxCountSuccessiveO;

        // Paradiagonal
        for (int i = -SEARCH_RANGE; i <= SEARCH_RANGE; ++i)
        {
            switch (valueAt(FOCUS_ROW + i, FOCUS_COLUMN - i))
            {
                case INVALID:
                case EMPTY:
                    maxCountSuccessiveX = countSuccessiveX > maxCountSuccessiveX ? countSuccessiveX : maxCountSuccessiveX;
                    maxCountSuccessiveO = countSuccessiveO > maxCountSuccessiveO ? countSuccessiveO : maxCountSuccessiveO;
                    countSuccessiveX = 0;
                    countSuccessiveO = 0;
                    break;
                case X:
                    maxCountSuccessiveO = countSuccessiveO > maxCountSuccessiveO ? countSuccessiveO : maxCountSuccessiveO;
                    countSuccessiveO = 0;
                    ++countSuccessiveX;
                    break;
                case O:
                    maxCountSuccessiveX = countSuccessiveX > maxCountSuccessiveX ? countSuccessiveX : maxCountSuccessiveX;
                    countSuccessiveX = 0;
                    ++countSuccessiveO;
                    break;
            }
        }

        finalMaxCountSuccessiveX = maxCountSuccessiveX > finalMaxCountSuccessiveX ? maxCountSuccessiveX : finalMaxCountSuccessiveX;
        finalMaxCountSuccessiveO = maxCountSuccessiveO > finalMaxCountSuccessiveO ? maxCountSuccessiveO : finalMaxCountSuccessiveO;

        if (finalMaxCountSuccessiveX >= sizeToWin)
            return GameState.XWIN;
        else if (finalMaxCountSuccessiveO >= sizeToWin)
            return GameState.OWIN;
        else
        {
            if (numRounds >= numColumns * numRows)
                return GameState.DRAW;
            else
                return GameState.PLAYING;
        }
    }

    /**
     * A text based representation of the 2D grid, and
     * should include all played Xs and Os.  For example
     * On a 3x3 board.  (HINT: \n for newlines)
     * <p>
     * | X |
     * -----------
     * O |   |
     * -----------
     * |   |
     *
     * @return String representation of the game
     */
    public String toString()
    {
        StringBuilder ret = new StringBuilder();

        for (int i = 0; i < numRows; ++i)
        {
            for (int j = 0; j < numColumns; ++j)
            {
                CellValue cellValue = valueAt(i + 1, j + 1);
                if (cellValue == CellValue.EMPTY || cellValue == CellValue.INVALID)
                {
                    ret.append("   ");
                }
                else
                {
                    ret.append(" ").append(cellValue.toString()).append(" ");
                }

                if (j < numColumns - 1)
                {
                    ret.append("|");
                }
            }
            if (i < numRows - 1)
            {
                ret.append("\n");
                for (int k = 0; k < numColumns * 4 - 1; ++k)
                {
                    ret.append("-");
                }
                ret.append("\n");
            }
        }
        return ret.toString();
    }

    /**
     * Expose all internal data for debugging purposes.
     *
     * @return String representation of the game
     */
    public String toDebug()
    {
        StringBuilder b = new StringBuilder();
        b.append("Grid (rows x columns): " + numRows + " x " + numColumns);
        b.append("\n");
        b.append("Size To Win: " + sizeToWin);
        b.append("\n");
        b.append("Num Rounds: " + numRounds);
        b.append("\n");
        b.append("Game State: " + gameState);
        b.append("\n");
        b.append("Current Player: " + currentPlayer);
        b.append("\n");
        b.append("Next Player: " + nextPlayer());
        b.append("\n");

        b.append("Board (array): [");
        for (int i = 0; i < board.length; i++)
        {
            if (i > 0)
            {
                b.append(",");
            }
            b.append(board[i]);
        }
        b.append("]\n");

        return b.toString();
    }
}
