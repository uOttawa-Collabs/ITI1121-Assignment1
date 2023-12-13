public class ComputerMenacePlayer extends Player
{
    private int[] lastBoardOdds;
    private int lastTotalOdds;
    private TicTacToe lastGame;
    private MenaceGame menaceGame;;

    /**
     * A menace player needs to know the size of the game before starting
     * Only optimized for a 3x3 board
     *
     * @param aNumRows    the number of lines in the game
     * @param aNumColumns the number of columns in the game
     * @param aSizeToWin  the number of cells that must be aligned to win.
     */
    public ComputerMenacePlayer(int aNumRows, int aNumColumns, int aSizeToWin)
    {
        if (aNumRows != 3 || aNumColumns != 3)
            throw new IllegalArgumentException("Number of rows and columns must be 3.");
    }

    @Override
    public GameOutcome endGame(GameState game)
    {
        GameOutcome outcome = super.endGame(game);

        menaceGame.setOutcome(outcome);

        return outcome;
    }

    public boolean play(TicTacToe game)
    {
        boolean flag = false;

        if (lastGame != game)
        {
            if (menaceGame != null)
            {
                lastBoardOdds = menaceGame.boardOdds;
                lastTotalOdds = menaceGame.totalOdds;
                flag = true;
            }
            menaceGame = new MenaceGame(game);
            lastGame = game;
            if (flag)
            {
                menaceGame.boardOdds = lastBoardOdds;
                menaceGame.totalOdds = lastTotalOdds;
            }
        }

        if (game.gameState != GameState.PLAYING)
            return false;
        else
        {
            int position = menaceGame.pickCurrentPosition();
            if (position != 0)
            {
                game.play(position);
                return true;
            }
            else
                return false;
        }
    }
}
