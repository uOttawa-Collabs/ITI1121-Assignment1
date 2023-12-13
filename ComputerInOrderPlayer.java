public class ComputerInOrderPlayer implements Player
{
    public boolean play(TicTacToe game)
    {
        int[] empties = game.emptyPositions();

        if (empties.length != 0)
        {
            game.play(empties[0]);
            return true;
        }
        else
        {
            return false;
        }
    }
}
