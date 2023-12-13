public class HumanPlayer implements Player
{
    private static String[] show(TicTacToe game)
    {
        // Cannot use Utils.NEW_LINE: Test failed due to CR/LF, but test file is LF
        String[] ret = {game.toString(), "\n\n", game.nextPlayer().toString() + " to play: "};
        return ret;
    }

    private static void printToScreen(String[] messages)
    {
        for (String s : messages)
        {
            System.out.print(s);
        }
    }

    private static void printToScreen(String message)
    {
        if (message != null)
        {
            System.out.println(message);
        }
    }

    public boolean play(TicTacToe game)
    {
        if (game.emptyPositions().length == 0)
        {
            return false;
        }

        printToScreen(show(game));
        String input = Utils.readLine();

        if ("debug".equals(input))
        {
            printToScreen(game.toDebug());
            return false;
        }
        else if ("exit".equals(input))
        {
            printToScreen("bye!");
            System.exit(0);
            return false;
        }
        else
        {
            String msg;
            try
            {
                msg = game.play(Integer.parseInt(input));
            }
            catch (Exception e)
            {
                return false;
            }

            if (msg != null)
            {
                printToScreen(msg);
                return false;
            }
            else
            {
                return true;
            }
        }
    }
}
