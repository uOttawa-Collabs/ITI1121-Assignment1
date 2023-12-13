public class ComputerRandomPlayer implements Player
{
    public boolean play(TicTacToe game)
    {
        int[] empties = game.emptyPositions();

        if (empties.length != 0)
        {
            game.play(empties[Utils.generator.nextInt(empties.length)]);
            return true;
        }
        else
        {
            return false;
        }
    }
}
